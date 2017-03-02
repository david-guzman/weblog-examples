
import { browser, element, by } from 'protractor';

describe('QuickStart E2E Tests', function () {

  let expectedMsg = 'Ahoy from Angular and JAX-RS';
  
  beforeEach(function () {
    browser.get('');
  });

  it('Should display: ' + expectedMsg, function () {
    expect(element(by.css('h1')).getText()).toEqual(expectedMsg);
  });

  it('As a loop', function() {
    browser.sleep(10000);
    element.all(by.css('td')).each( td => {
      expect(td.getText()).toMatch(/Patient\s\d{8}/);
    });

  });

});