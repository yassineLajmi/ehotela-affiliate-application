import { element, by, ElementFinder } from 'protractor';

export class AffiliateComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-affiliate div table .btn-danger'));
    title = element.all(by.css('jhi-affiliate div h2#page-heading span')).first();

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

export class AffiliateUpdatePage {
    pageTitle = element(by.id('jhi-affiliate-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    numeroAffilieInput = element(by.id('field_numeroAffilie'));
    titreInput = element(by.id('field_titre'));
    nomsInput = element(by.id('field_noms'));
    adresseLigne1Input = element(by.id('field_adresseLigne1'));
    adresseLigne2Input = element(by.id('field_adresseLigne2'));
    numeroPostalInput = element(by.id('field_numeroPostal'));
    localiteInput = element(by.id('field_localite'));
    cantonInput = element(by.id('field_canton'));
    langueInput = element(by.id('field_langue'));
    groupeCodeInput = element(by.id('field_groupeCode'));
    groupeNomInput = element(by.id('field_groupeNom'));
    isCcntInput = element(by.id('field_isCcnt'));
    timeTypeInput = element(by.id('field_timeType'));
    groupTypeInput = element(by.id('field_groupType'));
    weeklyWorkTimeInput = element(by.id('field_weeklyWorkTime'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNumeroAffilieInput(numeroAffilie) {
        await this.numeroAffilieInput.sendKeys(numeroAffilie);
    }

    async getNumeroAffilieInput() {
        return this.numeroAffilieInput.getAttribute('value');
    }

    async setTitreInput(titre) {
        await this.titreInput.sendKeys(titre);
    }

    async getTitreInput() {
        return this.titreInput.getAttribute('value');
    }

    async setNomsInput(noms) {
        await this.nomsInput.sendKeys(noms);
    }

    async getNomsInput() {
        return this.nomsInput.getAttribute('value');
    }

    async setAdresseLigne1Input(adresseLigne1) {
        await this.adresseLigne1Input.sendKeys(adresseLigne1);
    }

    async getAdresseLigne1Input() {
        return this.adresseLigne1Input.getAttribute('value');
    }

    async setAdresseLigne2Input(adresseLigne2) {
        await this.adresseLigne2Input.sendKeys(adresseLigne2);
    }

    async getAdresseLigne2Input() {
        return this.adresseLigne2Input.getAttribute('value');
    }

    async setNumeroPostalInput(numeroPostal) {
        await this.numeroPostalInput.sendKeys(numeroPostal);
    }

    async getNumeroPostalInput() {
        return this.numeroPostalInput.getAttribute('value');
    }

    async setLocaliteInput(localite) {
        await this.localiteInput.sendKeys(localite);
    }

    async getLocaliteInput() {
        return this.localiteInput.getAttribute('value');
    }

    async setCantonInput(canton) {
        await this.cantonInput.sendKeys(canton);
    }

    async getCantonInput() {
        return this.cantonInput.getAttribute('value');
    }

    async setLangueInput(langue) {
        await this.langueInput.sendKeys(langue);
    }

    async getLangueInput() {
        return this.langueInput.getAttribute('value');
    }

    async setGroupeCodeInput(groupeCode) {
        await this.groupeCodeInput.sendKeys(groupeCode);
    }

    async getGroupeCodeInput() {
        return this.groupeCodeInput.getAttribute('value');
    }

    async setGroupeNomInput(groupeNom) {
        await this.groupeNomInput.sendKeys(groupeNom);
    }

    async getGroupeNomInput() {
        return this.groupeNomInput.getAttribute('value');
    }

    async setIsCcntInput(isCcnt) {
        await this.isCcntInput.sendKeys(isCcnt);
    }

    async getIsCcntInput() {
        return this.isCcntInput.getAttribute('value');
    }

    async setTimeTypeInput(timeType) {
        await this.timeTypeInput.sendKeys(timeType);
    }

    async getTimeTypeInput() {
        return this.timeTypeInput.getAttribute('value');
    }

    async setGroupTypeInput(groupType) {
        await this.groupTypeInput.sendKeys(groupType);
    }

    async getGroupTypeInput() {
        return this.groupTypeInput.getAttribute('value');
    }

    async setWeeklyWorkTimeInput(weeklyWorkTime) {
        await this.weeklyWorkTimeInput.sendKeys(weeklyWorkTime);
    }

    async getWeeklyWorkTimeInput() {
        return this.weeklyWorkTimeInput.getAttribute('value');
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

export class AffiliateDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-affiliate-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-affiliate'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
