import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServer } from 'app/shared/model/server.model';

type EntityResponseType = HttpResponse<IServer>;
type EntityArrayResponseType = HttpResponse<IServer[]>;

@Injectable({ providedIn: 'root' })
export class ServerService {
    public resourceUrl = SERVER_API_URL + 'api/servers';

    constructor(private http: HttpClient) {}

    create(server: IServer): Observable<EntityResponseType> {
        return this.http.post<IServer>(this.resourceUrl, server, { observe: 'response' });
    }

    update(server: IServer): Observable<EntityResponseType> {
        return this.http.put<IServer>(this.resourceUrl, server, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IServer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IServer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
