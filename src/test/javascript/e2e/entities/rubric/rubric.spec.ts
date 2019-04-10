/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RubricComponentsPage, RubricDeleteDialog, RubricUpdatePage } from './rubric.page-object';

const expect = chai.expect;

describe('Rubric e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let rubricUpdatePage: RubricUpdatePage;
    let rubricComponentsPage: RubricComponentsPage;
    let rubricDeleteDialog: RubricDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Rubrics', async () => {
        await navBarPage.goToEntity('rubric');
        rubricComponentsPage = new RubricComponentsPage();
        await browser.wait(ec.visibilityOf(rubricComponentsPage.title), 5000);
        expect(await rubricComponentsPage.getTitle()).to.eq('affiliateApp.rubric.home.title');
    });

    it('should load create Rubric page', async () => {
        await rubricComponentsPage.clickOnCreateButton();
        rubricUpdatePage = new RubricUpdatePage();
        expect(await rubricUpdatePage.getPageTitle()).to.eq('affiliateApp.rubric.home.createOrEditLabel');
        await rubricUpdatePage.cancel();
    });

    it('should create and save Rubrics', async () => {
        const nbButtonsBeforeCreate = await rubricComponentsPage.countDeleteButtons();

        await rubricComponentsPage.clickOnCreateButton();
        await promise.all([
            rubricUpdatePage.setDescriptionInput('description'),
            rubricUpdatePage.setStartDateInput('2000-12-31'),
            rubricUpdatePage.setEndDateInput('2000-12-31'),
            rubricUpdatePage.setRubricTypeInput('rubricType'),
            rubricUpdatePage.setTotalRateInput('5'),
            rubricUpdatePage.setSexInput('sex'),
            rubricUpdatePage.setStartAgeInput('5'),
            rubricUpdatePage.setEndAgeInput('5'),
            rubricUpdatePage.setEmployeeRateInput('5'),
            rubricUpdatePage.setDiRateInput('5'),
            rubricUpdatePage.setAilSalaryLimitInput('5'),
            rubricUpdatePage.affiliateSelectLastOption()
        ]);
        expect(await rubricUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await rubricUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await rubricUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        expect(await rubricUpdatePage.getRubricTypeInput()).to.eq('rubricType');
        expect(await rubricUpdatePage.getTotalRateInput()).to.eq('5');
        expect(await rubricUpdatePage.getSexInput()).to.eq('sex');
        expect(await rubricUpdatePage.getStartAgeInput()).to.eq('5');
        expect(await rubricUpdatePage.getEndAgeInput()).to.eq('5');
        expect(await rubricUpdatePage.getEmployeeRateInput()).to.eq('5');
        expect(await rubricUpdatePage.getDiRateInput()).to.eq('5');
        expect(await rubricUpdatePage.getAilSalaryLimitInput()).to.eq('5');
        await rubricUpdatePage.save();
        expect(await rubricUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await rubricComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Rubric', async () => {
        const nbButtonsBeforeDelete = await rubricComponentsPage.countDeleteButtons();
        await rubricComponentsPage.clickOnLastDeleteButton();

        rubricDeleteDialog = new RubricDeleteDialog();
        expect(await rubricDeleteDialog.getDialogTitle()).to.eq('affiliateApp.rubric.delete.question');
        await rubricDeleteDialog.clickOnConfirmButton();

        expect(await rubricComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
