
import { browser, element, by } from 'protractor';

describe('Angular and JAX-RS E2E Tests', function () {

  let expectedMsg = 'Ahoy from Angular and JAX-RS';
  
  beforeEach(function () {
    browser.get('');
  });

  it('Should display ' + expectedMsg + ' in h1', function () {
    expect(element(by.css('h1')).getText()).toEqual(expectedMsg);
  });

  it('Should display a string that matches /Patient\s\d{8}/ in each td', function() {
    browser.sleep(10000);
    element.all(by.css('td')).each( td => {
      expect(td.getText()).toMatch(/Patient\s\d{8}/);
    });
  });

});