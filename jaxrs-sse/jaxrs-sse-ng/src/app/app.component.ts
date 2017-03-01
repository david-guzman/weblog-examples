import { Component, OnInit } from '@angular/core';

import { AppService } from './app.service';
import { ClinicRooms } from './clinicrooms';

@Component({
  selector: 'my-app',
  moduleId: module.id,
  templateUrl: './app.component.html',
  providers: [AppService]
})
export class AppComponent implements OnInit {
  name = 'Ahoy from Angular and JAX-RS';

  clinicRooms: ClinicRooms = new ClinicRooms();

  constructor(private appService: AppService) { }

  ngOnInit() {
    this.getSses();
  }

  getSses() {
    const sses = this.appService.subscribeToJaxRs();

    sses.subscribe({
      next: (msg: string) => {
        this.mapToRooms(msg);
      }
    });
  }

  mapToRooms(m: string) {
    const entry = m.split(':')
      .map(e => e.trim());
    switch (entry[0]) {
      case 'NURSE':
        this.clinicRooms.nurse = entry[1];
        break;
      case 'REGISTRAR1':
        this.clinicRooms.registrar1 = entry[1];
        break;
      case 'REGISTRAR2':
        this.clinicRooms.registrar2 = entry[1];
        break;
      case 'CONSULTANT':
        this.clinicRooms.consultant = entry[1];
        break;
      case 'RADIOLOGIST':
        this.clinicRooms.radiologist = entry[1];
        break;
      case 'PHLEBOTOMIST':
        this.clinicRooms.phlebotomist = entry[1];
        break;
      default:
        break;
    }
  }

}