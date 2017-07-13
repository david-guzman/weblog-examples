import { JaxrsSseNgPage } from './app.po';

describe('jaxrs-sse-ng App', () => {
  let page: JaxrsSseNgPage;

  let expectedMsg = 'Ahoy from Angular and JAX-RS!!';

  beforeEach(() => {
    page = new JaxrsSseNgPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual(expectedMsg);
  });

  it('Should display a string that matches /Patient\s\d{8}/ in each td', function() {
    page.sleep();
    page.getTableData().each( td => {
      expect(td.getText()).toMatch(/Patient\s\d{8}/);
    });
  });

});
