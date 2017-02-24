import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Http, Response } from '@angular/http';

import 'rxjs/add/operator/map';

@Injectable()
export class AppService {

    private url = "http://localhost:8081/jaxrs-sse-web/webresources/sse";
    private eventStrings: string[] = [];

    private zone: NgZone;

    constructor(private _http: Http) {
        this.zone = new NgZone({ enableLongStackTrace: false });
    }

    subscribeToJaxRs(): Observable<string> {
        console.log('subscribing to SSE from ' + this.url);

        return Observable.create((observer: any) => {
            let eventSource = new EventSource(this.url);

            eventSource.onopen = x => {
                console.log('connection open ' + x.timeStamp);
            };

            eventSource.onmessage = x => {
                console.log('message' + x);
                observer.next(x.data);
            };

            eventSource.onerror = x => {
// SHOULD BE         observer.error(x);
                console.log('ERROR');
            };

            eventSource.addEventListener('attendance-list', x => {
                observer.next(x.data);
            }, false
            );

        });
    }

    getEventsAsText() {
        const observable = Observable.create((observer: any) => {
            const eventSource = new EventSource(this.url);
            eventSource.onmessage = x => observer.next(x.data);
            eventSource.onerror = x => observer.error(x);

            return () => {
                eventSource.close();
            };
        });

        observable.subscribe({
            next: (guid: string) => {
                this.zone.run(() => this.eventStrings.push(guid));
            },
            error: (err: string) => console.error('something wrong occurred: ' + err)
        });
    }
}