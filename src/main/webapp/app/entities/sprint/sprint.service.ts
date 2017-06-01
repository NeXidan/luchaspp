import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Sprint } from './sprint.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SprintService {

    private resourceUrl = 'api/sprints';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(sprint: Sprint): Observable<Sprint> {
        const copy = this.convert(sprint);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(sprint: Sprint): Observable<Sprint> {
        const copy = this.convert(sprint);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Sprint> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.fromDate = this.dateUtils
            .convertLocalDateFromServer(entity.fromDate);
        entity.toDate = this.dateUtils
            .convertLocalDateFromServer(entity.toDate);
    }

    private convert(sprint: Sprint): Sprint {
        const copy: Sprint = Object.assign({}, sprint);
        copy.fromDate = this.dateUtils
            .convertLocalDateToServer(sprint.fromDate);
        copy.toDate = this.dateUtils
            .convertLocalDateToServer(sprint.toDate);
        return copy;
    }
}
