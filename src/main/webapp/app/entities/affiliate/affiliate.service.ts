import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAffiliate } from 'app/shared/model/affiliate.model';

type EntityResponseType = HttpResponse<IAffiliate>;
type EntityArrayResponseType = HttpResponse<IAffiliate[]>;

@Injectable({ providedIn: 'root' })
export class AffiliateService {
    public resourceUrl = SERVER_API_URL + 'api/affiliates';

    constructor(protected http: HttpClient) {}

    create(affiliate: IAffiliate): Observable<EntityResponseType> {
        return this.http.post<IAffiliate>(this.resourceUrl, affiliate, { observe: 'response' });
    }

    update(affiliate: IAffiliate): Observable<EntityResponseType> {
        return this.http.put<IAffiliate>(this.resourceUrl, affiliate, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAffiliate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAffiliate[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
