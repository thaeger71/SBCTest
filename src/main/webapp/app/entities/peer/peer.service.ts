import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPeer } from 'app/shared/model/peer.model';

type EntityResponseType = HttpResponse<IPeer>;
type EntityArrayResponseType = HttpResponse<IPeer[]>;

@Injectable({ providedIn: 'root' })
export class PeerService {
    public resourceUrl = SERVER_API_URL + 'api/peers';

    constructor(private http: HttpClient) {}

    create(peer: IPeer): Observable<EntityResponseType> {
        return this.http.post<IPeer>(this.resourceUrl, peer, { observe: 'response' });
    }

    update(peer: IPeer): Observable<EntityResponseType> {
        return this.http.put<IPeer>(this.resourceUrl, peer, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPeer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPeer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
