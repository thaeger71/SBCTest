import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDialplanEntry } from 'app/shared/model/dialplan-entry.model';

type EntityResponseType = HttpResponse<IDialplanEntry>;
type EntityArrayResponseType = HttpResponse<IDialplanEntry[]>;

@Injectable({ providedIn: 'root' })
export class DialplanEntryService {
    public resourceUrl = SERVER_API_URL + 'api/dialplan-entries';

    constructor(private http: HttpClient) {}

    create(dialplanEntry: IDialplanEntry): Observable<EntityResponseType> {
        return this.http.post<IDialplanEntry>(this.resourceUrl, dialplanEntry, { observe: 'response' });
    }

    update(dialplanEntry: IDialplanEntry): Observable<EntityResponseType> {
        return this.http.put<IDialplanEntry>(this.resourceUrl, dialplanEntry, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDialplanEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDialplanEntry[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
