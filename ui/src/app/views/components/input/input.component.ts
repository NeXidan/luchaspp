import {Component, Input} from '@angular/core';

@Component({
    selector: 'input',
    templateUrl: './input.component.html',
    styleUrls: ['./input.component.less'],
    host: {
        '[disabled]': 'disabled',
        '(input)': '_onInput'
    }
})
export class InputComponent {
    @Input()
    disabled: Boolean

    _onInput() {
        // no-op
    }
}
