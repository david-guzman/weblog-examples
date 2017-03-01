import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AppComponent } from './app.component';

describe('AppComponent', function () {
  let de: DebugElement;
  let comp: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    comp = fixture.componentInstance;
    de = fixture.debugElement.query(By.css('h1'));
  });

  it('should create component', () => expect(comp).toBeDefined() );

  it('should have expected <h1> text', () => {
    fixture.detectChanges();
    const h1 = de.nativeElement;
    expect(h1.innerText).toMatch(/angular\sand\sjax-rs/i,
      '<h1> should say something about "Angular and JAX-RS"');
  });
});

describe('AppComponent', () => {

  let fixture: ComponentFixture<AppComponent>;
  let comp: AppComponent;
  let debel: DebugElement[];
  let elem: HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    comp = fixture.componentInstance;

    debel = fixture.debugElement.queryAll(By.css('td'));
  });

  it('All <td> should display Vacant on start', () => {
    fixture.detectChanges();
    debel.forEach( deb => {
      elem = deb.nativeElement;
      expect(elem.textContent).toBe('Vacant');
    });
  });

});