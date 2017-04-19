import {Component, Input} from '@angular/core';

@Component({
    selector: 'button',
    templateUrl: './button.component.html',
    styleUrls: ['./button.component.less'],
    host: {
        '[disabled]': 'disabled'
    }
})
export class ButtonComponent {
    @Input()
    disabled: Boolean

    @Input()
    icon: string

    @Input()
    label: string
}
