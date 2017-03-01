
import { browser, element, by } from 'protractor';

describe('QuickStart E2E Tests', function () {

  let expectedMsg = 'Ahoy from Angular and JAX-RS';

  beforeEach(function () {
    browser.get('');
  });

  it('Should display: ' + expectedMsg, function () {
    expect(element(by.css('h1')).getText()).toEqual(expectedMsg);
  });

});