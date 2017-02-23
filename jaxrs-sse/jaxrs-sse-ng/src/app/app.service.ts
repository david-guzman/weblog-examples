import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class AppService {

    private url = "http://localhost:8080/jaxrs-sse-web/webresources/sse";
    private eventStrings : string[] = [];

  private zone:NgZone;

  constructor() {
    this.zone = new NgZone({enableLongStackTrace: false});
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