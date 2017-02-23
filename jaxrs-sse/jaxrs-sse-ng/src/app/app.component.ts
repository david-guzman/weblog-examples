import { Component } from '@angular/core';

@Component({
  selector: 'my-app',
  template: `<h1>Ahoy from {{name}}</h1>`,
})
export class AppComponent  { 
  name = 'Angular and JAX-RS'; 
  
}
