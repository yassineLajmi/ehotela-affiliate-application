import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAffSubscription } from 'app/shared/model/aff-subscription.model';

type EntityResponseType = HttpResponse<IAffSubscription>;
type EntityArrayResponseType = HttpResponse<IAffSubscription[]>;

@Injectable({ providedIn: 'root' })
export class AffSubscriptionService {
    public resourceUrl = SERVER_API_URL + 'api/aff-subscriptions';

    constructor(protected http: HttpClient) {}

    create(affSubscription: IAffSubscription): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(affSubscription);
        return this.http
            .post<IAffSubscription>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(affSubscription: IAffSubscription): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(affSubscription);
        return this.http
            .put<IAffSubscription>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAffSubscription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAffSubscription[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(affSubscription: IAffSubscription): IAffSubscription {
        const copy: IAffSubscription = Object.assign({}, affSubscription, {
            startDate:
                affSubscription.startDate != null && affSubscription.startDate.isValid()
                    ? affSubscription.startDate.format(DATE_FORMAT)
                    : null,
            endDate:
                affSubscription.endDate != null && affSubscription.endDate.isValid() ? affSubscription.endDate.format(DATE_FORMAT) : null
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
            res.body.forEach((affSubscription: IAffSubscription) => {
                affSubscription.startDate = affSubscription.startDate != null ? moment(affSubscription.startDate) : null;
                affSubscription.endDate = affSubscription.endDate != null ? moment(affSubscription.endDate) : null;
            });
        }
        return res;
    }
}
