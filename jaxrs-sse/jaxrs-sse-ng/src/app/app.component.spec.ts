import { ComponentFixture, TestBed, async, fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { Observable } from 'rxjs/Rx';

import { AppComponent } from './app.component';
import { AppService } from './app.service';

describe('AppComponent', () => {

  let fixture: ComponentFixture<AppComponent>;
  let comp: AppComponent;
  let debel: DebugElement[];
  let elem: HTMLElement;
  let serv: AppService;
  let spy: jasmine.Spy;

  const testMsg = [
    'NURSE : Test SSE',
    'REGISTRAR1 : Test SSE',
    'REGISTRAR2 : Test SSE',
    'CONSULTANT : Test SSE',
    'RADIOLOGIST : Test SSE',
    'PHLEBOTOMIST : Test SSE'
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppComponent],
      providers: [AppService]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    comp = fixture.componentInstance;

    serv = fixture.debugElement.injector.get(AppService);

    debel = fixture.debugElement.queryAll(By.css('td'));
  });

  it('Should create component', () => expect(comp).toBeDefined());

  it('Should display "Vacant" in all <td> fields', () => {
    fixture.detectChanges();
    debel.forEach(deb => {
      elem = deb.nativeElement;
      expect(elem.textContent).toBe('Vacant');
    });
  });

  it('Should call spy', () => {
    spy = spyOn(serv, 'subscribeToJaxRs')
      .and.returnValue(Observable.from(testMsg));
    fixture.detectChanges();
    expect(spy.calls.any()).toBe(true, 'subscribeToJaxRs called');
  });

  it('Should update td', fakeAsync(() => {
    spy = spyOn(serv, 'subscribeToJaxRs')
      .and.returnValue(Observable.from(testMsg));
    fixture.detectChanges();
    tick();
    fixture.detectChanges();
    debel.forEach(deb => {
      elem = deb.nativeElement;
      expect(elem.textContent).toBe('Test SSE');
    });
  }));

});