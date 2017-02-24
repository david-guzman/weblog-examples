import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Http } from '@angular/http';

import 'rxjs/add/operator/map';

@Injectable()
export class AppService {

    private url = "http://localhost:8081/jaxrs-sse-web/webresources/sse";
    private eventStrings : string[] = [];

  private zone:NgZone;

  constructor(private _http : Http) {
    this.zone = new NgZone({enableLongStackTrace: false});
  }
  
  subscribeToJaxRs() : Observable<string> {
      console.log('subscribing to SSE from ' + this.url);
      
      return Observable.create( (observer : any) => {
          let eventSource = new EventSource(this.url);
          
          eventSource.onopen = x => {
              console.log('connection open' + x);
          };
          
          eventSource.onmessage = x => {
              console.log('message' + x);
              observer.next(x.data);
          };
          
          eventSource.onerror = x => {
              console.log('ERROR' + x);
          };
          
          eventSource.addEventListener('update', x => {
              observer.next(x.data);
          }
          );
      });
  }
  
  getSses() : Observable<string> {
    console.log('get SSE from server' + this.url);
    return this._http.get(this.url).map(res => res.toString());
  }
  
  getEventStrings() : Observable<string> {
      return Observable.from(this.eventStrings);
  }
    
    getEventsAsText() {
      const observable = Observable.create( (observer : any) => {
        const eventSource = new EventSource(this.url); 
        eventSource.onmessage = x => observer.next(x.data);
        eventSource.onerror = x => observer.error(x);

        return () => {
            eventSource.close();
        };
    });

    observable.subscribe({
        next: (guid : string) => {
            this.zone.run(() => this.eventStrings.push(guid));
        },
        error: (err : string) => console.error('something wrong occurred: ' + err)
    });
    }
}