/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AffiliateComponentsPage, AffiliateDeleteDialog, AffiliateUpdatePage } from './affiliate.page-object';

const expect = chai.expect;

describe('Affiliate e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let affiliateUpdatePage: AffiliateUpdatePage;
    let affiliateComponentsPage: AffiliateComponentsPage;
    let affiliateDeleteDialog: AffiliateDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Affiliates', async () => {
        await navBarPage.goToEntity('affiliate');
        affiliateComponentsPage = new AffiliateComponentsPage();
        await browser.wait(ec.visibilityOf(affiliateComponentsPage.title), 5000);
        expect(await affiliateComponentsPage.getTitle()).to.eq('affiliateApp.affiliate.home.title');
    });

    it('should load create Affiliate page', async () => {
        await affiliateComponentsPage.clickOnCreateButton();
        affiliateUpdatePage = new AffiliateUpdatePage();
        expect(await affiliateUpdatePage.getPageTitle()).to.eq('affiliateApp.affiliate.home.createOrEditLabel');
        await affiliateUpdatePage.cancel();
    });

    it('should create and save Affiliates', async () => {
        const nbButtonsBeforeCreate = await affiliateComponentsPage.countDeleteButtons();

        await affiliateComponentsPage.clickOnCreateButton();
        await promise.all([
            affiliateUpdatePage.setNumeroAffilieInput('5'),
            affiliateUpdatePage.setTitreInput('titre'),
            affiliateUpdatePage.setNomsInput('noms'),
            affiliateUpdatePage.setAdresseLigne1Input('adresseLigne1'),
            affiliateUpdatePage.setAdresseLigne2Input('adresseLigne2'),
            affiliateUpdatePage.setNumeroPostalInput('5'),
            affiliateUpdatePage.setLocaliteInput('localite'),
            affiliateUpdatePage.setCantonInput('canton'),
            affiliateUpdatePage.setLangueInput('5'),
            affiliateUpdatePage.setGroupeCodeInput('5'),
            affiliateUpdatePage.setGroupeNomInput('groupeNom'),
            affiliateUpdatePage.setIsCcntInput('5'),
            affiliateUpdatePage.setTimeTypeInput('timeType'),
            affiliateUpdatePage.setGroupTypeInput('groupType'),
            affiliateUpdatePage.setWeeklyWorkTimeInput('5')
        ]);
        expect(await affiliateUpdatePage.getNumeroAffilieInput()).to.eq('5');
        expect(await affiliateUpdatePage.getTitreInput()).to.eq('titre');
        expect(await affiliateUpdatePage.getNomsInput()).to.eq('noms');
        expect(await affiliateUpdatePage.getAdresseLigne1Input()).to.eq('adresseLigne1');
        expect(await affiliateUpdatePage.getAdresseLigne2Input()).to.eq('adresseLigne2');
        expect(await affiliateUpdatePage.getNumeroPostalInput()).to.eq('5');
        expect(await affiliateUpdatePage.getLocaliteInput()).to.eq('localite');
        expect(await affiliateUpdatePage.getCantonInput()).to.eq('canton');
        expect(await affiliateUpdatePage.getLangueInput()).to.eq('5');
        expect(await affiliateUpdatePage.getGroupeCodeInput()).to.eq('5');
        expect(await affiliateUpdatePage.getGroupeNomInput()).to.eq('groupeNom');
        expect(await affiliateUpdatePage.getIsCcntInput()).to.eq('5');
        expect(await affiliateUpdatePage.getTimeTypeInput()).to.eq('timeType');
        expect(await affiliateUpdatePage.getGroupTypeInput()).to.eq('groupType');
        expect(await affiliateUpdatePage.getWeeklyWorkTimeInput()).to.eq('5');
        await affiliateUpdatePage.save();
        expect(await affiliateUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await affiliateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Affiliate', async () => {
        const nbButtonsBeforeDelete = await affiliateComponentsPage.countDeleteButtons();
        await affiliateComponentsPage.clickOnLastDeleteButton();

        affiliateDeleteDialog = new AffiliateDeleteDialog();
        expect(await affiliateDeleteDialog.getDialogTitle()).to.eq('affiliateApp.affiliate.delete.question');
        await affiliateDeleteDialog.clickOnConfirmButton();

        expect(await affiliateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
