import { IRubric } from 'app/shared/model/rubric.model';

export interface IAffiliate {
    id?: number;
    numeroAffilie?: number;
    titre?: string;
    noms?: string;
    adresseLigne1?: string;
    adresseLigne2?: string;
    numeroPostal?: number;
    localite?: string;
    canton?: string;
    langue?: number;
    groupeCode?: number;
    groupeNom?: string;
    isCcnt?: number;
    timeType?: string;
    groupType?: string;
    weeklyWorkTime?: number;
    employees?: IRubric[];
}

export class Affiliate implements IAffiliate {
    constructor(
        public id?: number,
        public numeroAffilie?: number,
        public titre?: string,
        public noms?: string,
        public adresseLigne1?: string,
        public adresseLigne2?: string,
        public numeroPostal?: number,
        public localite?: string,
        public canton?: string,
        public langue?: number,
        public groupeCode?: number,
        public groupeNom?: string,
        public isCcnt?: number,
        public timeType?: string,
        public groupType?: string,
        public weeklyWorkTime?: number,
        public employees?: IRubric[]
    ) {}
}
