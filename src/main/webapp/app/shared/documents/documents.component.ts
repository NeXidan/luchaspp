import {Component, Input} from '@angular/core';
import {DocumentsService} from './documents.service';

@Component({
    selector: 'app-documents',
    templateUrl: './documents.component.html',
    providers: [DocumentsService]
})
export class DocumentsComponent {
    @Input()
    type: string;

    @Input()
    optional = '';

    constructor(
        private documentsService: DocumentsService
    ) {
    }

    load(format) {
        this.documentsService.request(this.type, format, this.optional);
    }
}
