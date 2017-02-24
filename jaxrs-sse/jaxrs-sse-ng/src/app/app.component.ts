import { Component, OnInit } from '@angular/core';

import { AppService } from './app.service';

@Component({
  selector: 'my-app',
  template: `
    <h1>Ahoy from {{name}}</h1>
    <ul>
      <li *ngFor="let eventMsg of eventMsgs">
        {{eventMsg}}
      </li>
    </ul>
    `,
  providers: [ AppService ]
})
export class AppComponent implements OnInit{ 
  name = 'Angular and JAX-RS'; 
  
  eventMsgs : string[] = [];
  
  constructor( private appService : AppService ){ }
  
  ngOnInit() {
    this.getSses();
  }
  
  getSses() {
    const sses = this.appService.subscribeToJaxRs();
    sses.subscribe(msg => this.eventMsgs.push(msg));
  }
  
}
