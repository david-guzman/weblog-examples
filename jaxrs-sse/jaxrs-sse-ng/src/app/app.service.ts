import { Injectable } from '@angular/core';
import { Observable, Observer } from 'rxjs/Rx';

@Injectable()
export class AppService {

    private url = "http://localhost:8081/jaxrs-sse-web/webresources/sse";

    subscribeToJaxRs(): Observable<string> {
        console.log('subscribing to SSE from ' + this.url);

        const eventSource = new EventSource(this.url);
        return Observable.create((observer: Observer<string>) => {


            eventSource.onopen = (x: any) => {
                console.log('connection open ' + x.timeStamp + ' - readyState = ' + eventSource.readyState);
            };

            eventSource.onmessage = x => {
                observer.next(x.data);
            };

            eventSource.onerror = () => {
                console.log('ERROR - readyState = ' + eventSource.readyState);
            };

            eventSource.addEventListener('attendance-list', x => {
                observer.next(x.data);
            });

            eventSource.close = () => {
                observer.complete();
            };
        });
    }

}
