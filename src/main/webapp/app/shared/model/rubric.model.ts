import { Moment } from 'moment';
import { IAffiliate } from 'app/shared/model/affiliate.model';

export interface IRubric {
    id?: number;
    description?: string;
    startDate?: Moment;
    endDate?: Moment;
    rubricType?: string;
    totalRate?: number;
    sex?: string;
    startAge?: number;
    endAge?: number;
    employeeRate?: number;
    diRate?: number;
    ailSalaryLimit?: number;
    affiliate?: IAffiliate;
}

export class Rubric implements IRubric {
    constructor(
        public id?: number,
        public description?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public rubricType?: string,
        public totalRate?: number,
        public sex?: string,
        public startAge?: number,
        public endAge?: number,
        public employeeRate?: number,
        public diRate?: number,
        public ailSalaryLimit?: number,
        public affiliate?: IAffiliate
    ) {}
}
