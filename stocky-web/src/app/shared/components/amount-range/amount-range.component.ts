import {Component} from '@angular/core';

@Component({
    selector: 'app-amount-range',
    templateUrl: './amount-range.component.html',
    styles: [`
      ::ng-deep .switch > nz-switch > button {

      }
    `]
})
export class AmountRangeComponent {

    public isAmountRange = true;

}