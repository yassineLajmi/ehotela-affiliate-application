import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRubric } from 'app/shared/model/rubric.model';

type EntityResponseType = HttpResponse<IRubric>;
type EntityArrayResponseType = HttpResponse<IRubric[]>;

@Injectable({ providedIn: 'root' })
export class RubricService {
    public resourceUrl = SERVER_API_URL + 'api/rubrics';

    constructor(protected http: HttpClient) {}

    create(rubric: IRubric): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rubric);
        return this.http
            .post<IRubric>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(rubric: IRubric): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rubric);
        return this.http
            .put<IRubric>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRubric>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRubric[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(rubric: IRubric): IRubric {
        const copy: IRubric = Object.assign({}, rubric, {
            startDate: rubric.startDate != null && rubric.startDate.isValid() ? rubric.startDate.format(DATE_FORMAT) : null,
            endDate: rubric.endDate != null && rubric.endDate.isValid() ? rubric.endDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((rubric: IRubric) => {
                rubric.startDate = rubric.startDate != null ? moment(rubric.startDate) : null;
                rubric.endDate = rubric.endDate != null ? moment(rubric.endDate) : null;
            });
        }
        return res;
    }
}
