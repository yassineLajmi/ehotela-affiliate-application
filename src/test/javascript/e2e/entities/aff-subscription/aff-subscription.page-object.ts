import { element, by, ElementFinder } from 'protractor';

export class AffSubscriptionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-aff-subscription div table .btn-danger'));
    title = element.all(by.css('jhi-aff-subscription div h2#page-heading span')).first();

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

export class AffSubscriptionUpdatePage {
    pageTitle = element(by.id('jhi-aff-subscription-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    idAssuranceInput = element(by.id('field_idAssurance'));
    startDateInput = element(by.id('field_startDate'));
    endDateInput = element(by.id('field_endDate'));
    affiliateSelect = element(by.id('field_affiliate'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setIdAssuranceInput(idAssurance) {
        await this.idAssuranceInput.sendKeys(idAssurance);
    }

    async getIdAssuranceInput() {
        return this.idAssuranceInput.getAttribute('value');
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

export class AffSubscriptionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-affSubscription-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-affSubscription'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
