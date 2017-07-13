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
});
