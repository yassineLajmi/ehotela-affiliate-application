import { element, by, ElementFinder } from 'protractor';

export class RubricComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-rubric div table .btn-danger'));
    title = element.all(by.css('jhi-rubric div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class RubricUpdatePage {
    pageTitle = element(by.id('jhi-rubric-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    startDateInput = element(by.id('field_startDate'));
    endDateInput = element(by.id('field_endDate'));
    rubricTypeInput = element(by.id('field_rubricType'));
    totalRateInput = element(by.id('field_totalRate'));
    sexInput = element(by.id('field_sex'));
    startAgeInput = element(by.id('field_startAge'));
    endAgeInput = element(by.id('field_endAge'));
    employeeRateInput = element(by.id('field_employeeRate'));
    diRateInput = element(by.id('field_diRate'));
    ailSalaryLimitInput = element(by.id('field_ailSalaryLimit'));
    affiliateSelect = element(by.id('field_affiliate'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setStartDateInput(startDate) {
        await this.startDateInput.sendKeys(startDate);
    }

    async getStartDateInput() {
        return this.startDateInput.getAttribute('value');
    }

    async setEndDateInput(endDate) {
        await this.endDateInput.sendKeys(endDate);
    }

    async getEndDateInput() {
        return this.endDateInput.getAttribute('value');
    }

    async setRubricTypeInput(rubricType) {
        await this.rubricTypeInput.sendKeys(rubricType);
    }

    async getRubricTypeInput() {
        return this.rubricTypeInput.getAttribute('value');
    }

    async setTotalRateInput(totalRate) {
        await this.totalRateInput.sendKeys(totalRate);
    }

    async getTotalRateInput() {
        return this.totalRateInput.getAttribute('value');
    }

    async setSexInput(sex) {
        await this.sexInput.sendKeys(sex);
    }

    async getSexInput() {
        return this.sexInput.getAttribute('value');
    }

    async setStartAgeInput(startAge) {
        await this.startAgeInput.sendKeys(startAge);
    }

    async getStartAgeInput() {
        return this.startAgeInput.getAttribute('value');
    }

    async setEndAgeInput(endAge) {
        await this.endAgeInput.sendKeys(endAge);
    }

    async getEndAgeInput() {
        return this.endAgeInput.getAttribute('value');
    }

    async setEmployeeRateInput(employeeRate) {
        await this.employeeRateInput.sendKeys(employeeRate);
    }

    async getEmployeeRateInput() {
        return this.employeeRateInput.getAttribute('value');
    }

    async setDiRateInput(diRate) {
        await this.diRateInput.sendKeys(diRate);
    }

    async getDiRateInput() {
        return this.diRateInput.getAttribute('value');
    }

    async setAilSalaryLimitInput(ailSalaryLimit) {
        await this.ailSalaryLimitInput.sendKeys(ailSalaryLimit);
    }

    async getAilSalaryLimitInput() {
        return this.ailSalaryLimitInput.getAttribute('value');
    }

    async affiliateSelectLastOption() {
        await this.affiliateSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async affiliateSelectOption(option) {
        await this.affiliateSelect.sendKeys(option);
    }

    getAffiliateSelect(): ElementFinder {
        return this.affiliateSelect;
    }

    async getAffiliateSelectedOption() {
        return this.affiliateSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class RubricDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-rubric-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-rubric'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
