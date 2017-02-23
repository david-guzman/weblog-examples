import { Component, OnInit } from '@angular/core';

import { AppService } from './app.service';

@Component({
  selector: 'my-app',
  template: `<h1>Ahoy from {{name}}</h1>`,
  providers: [ AppService ]
})
export class AppComponent implements OnInit { 
  name = 'Angular and JAX-RS'; 
  
  eventMsgs : string[] = [];
  test : string = "";
  
  constructor( private appService : AppService ){ }
  
  ngOnInit() {
    this.getSses();
  }
  
  getSses() {

  }
  
}
