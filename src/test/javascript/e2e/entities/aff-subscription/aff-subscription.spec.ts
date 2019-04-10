/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AffSubscriptionComponentsPage, AffSubscriptionDeleteDialog, AffSubscriptionUpdatePage } from './aff-subscription.page-object';

const expect = chai.expect;

describe('AffSubscription e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let affSubscriptionUpdatePage: AffSubscriptionUpdatePage;
    let affSubscriptionComponentsPage: AffSubscriptionComponentsPage;
    let affSubscriptionDeleteDialog: AffSubscriptionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AffSubscriptions', async () => {
        await navBarPage.goToEntity('aff-subscription');
        affSubscriptionComponentsPage = new AffSubscriptionComponentsPage();
        await browser.wait(ec.visibilityOf(affSubscriptionComponentsPage.title), 5000);
        expect(await affSubscriptionComponentsPage.getTitle()).to.eq('affiliateApp.affSubscription.home.title');
    });

    it('should load create AffSubscription page', async () => {
        await affSubscriptionComponentsPage.clickOnCreateButton();
        affSubscriptionUpdatePage = new AffSubscriptionUpdatePage();
        expect(await affSubscriptionUpdatePage.getPageTitle()).to.eq('affiliateApp.affSubscription.home.createOrEditLabel');
        await affSubscriptionUpdatePage.cancel();
    });

    it('should create and save AffSubscriptions', async () => {
        const nbButtonsBeforeCreate = await affSubscriptionComponentsPage.countDeleteButtons();

        await affSubscriptionComponentsPage.clickOnCreateButton();
        await promise.all([
            affSubscriptionUpdatePage.setIdAssuranceInput('idAssurance'),
            affSubscriptionUpdatePage.setStartDateInput('2000-12-31'),
            affSubscriptionUpdatePage.setEndDateInput('2000-12-31'),
            affSubscriptionUpdatePage.affiliateSelectLastOption()
        ]);
        expect(await affSubscriptionUpdatePage.getIdAssuranceInput()).to.eq('idAssurance');
        expect(await affSubscriptionUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await affSubscriptionUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        await affSubscriptionUpdatePage.save();
        expect(await affSubscriptionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await affSubscriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AffSubscription', async () => {
        const nbButtonsBeforeDelete = await affSubscriptionComponentsPage.countDeleteButtons();
        await affSubscriptionComponentsPage.clickOnLastDeleteButton();

        affSubscriptionDeleteDialog = new AffSubscriptionDeleteDialog();
        expect(await affSubscriptionDeleteDialog.getDialogTitle()).to.eq('affiliateApp.affSubscription.delete.question');
        await affSubscriptionDeleteDialog.clickOnConfirmButton();

        expect(await affSubscriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
