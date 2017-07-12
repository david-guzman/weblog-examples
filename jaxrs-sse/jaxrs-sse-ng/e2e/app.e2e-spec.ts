import { JaxrsSseNgPage } from './app.po';

describe('jaxrs-sse-ng App', () => {
  let page: JaxrsSseNgPage;

  beforeEach(() => {
    page = new JaxrsSseNgPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
