import { SppPage } from './app.po';

describe('spp App', () => {
    let page: SppPage;

    beforeEach(() => {
        page = new SppPage();
    });

    it('should display message saying app works', () => {
        page.navigateTo();
        expect(page.getParagraphText()).toEqual('app works!');
    });
});
