import {Injectable} from '@angular/core';
import {Http, Response, ResponseContentType} from '@angular/http';
import {saveAs} from 'file-saver';

@Injectable()
export class DocumentsService {
    private resourceUrl = 'documents/';

    constructor(private http: Http) { }

    request(url, format, optional = '') {
        this.http.get(`${this.resourceUrl}${url}/${format}${optional}`, {responseType: ResponseContentType.Blob})
            .map((res: Response) => res.blob())
            .subscribe((data) => {
                const blob = new Blob([data], {type: `application/${format}`});
                saveAs(blob, `${url}.${format}`);
            });
    }
}
