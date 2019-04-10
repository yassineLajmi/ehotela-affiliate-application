import { Moment } from 'moment';
import { IAffiliate } from 'app/shared/model/affiliate.model';

export interface IAffSubscription {
    id?: number;
    idAssurance?: string;
    startDate?: Moment;
    endDate?: Moment;
    affiliate?: IAffiliate;
}

export class AffSubscription implements IAffSubscription {
    constructor(
        public id?: number,
        public idAssurance?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public affiliate?: IAffiliate
    ) {}
}
