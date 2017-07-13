import { browser, by, element } from 'protractor';

export class JaxrsSseNgPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }

  getTableData() {
    return element.all(by.css('td'));
  }

  sleep() {
    browser.sleep(10000);
  }
}
