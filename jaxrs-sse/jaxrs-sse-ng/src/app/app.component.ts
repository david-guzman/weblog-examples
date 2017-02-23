import { Component } from '@angular/core';

import { AppService } from './app.service';

@Component({
  selector: 'my-app',
  template: `<h1>Ahoy from {{name}}</h1>`,
  providers: [ AppService ]
})
export class AppComponent  { 
  name = 'Angular and JAX-RS'; 
  
  constructor( private appService : AppService ){ }
  
}
