import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router'; // Import Router
import axios from 'axios';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class HomeComponent {
  configForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) { // Inject Router
    this.configForm = this.fb.group({
      maxTicketCapacity: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      totalTicketCapacity: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      ticketReleaseRate: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      customerRetrievalRate: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      vendorCount: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      customerCount: ['', [Validators.required, Validators.pattern('^[0-9]*$')]]
    }, {
      validators: this.maxGreaterThanTotal('maxTicketCapacity', 'totalTicketCapacity')
    });
  }

  maxGreaterThanTotal(maxKey: string, totalKey: string) {
    return (formGroup: FormGroup) => {
      const maxControl = formGroup.controls[maxKey];
      const totalControl = formGroup.controls[totalKey];

      if (
        maxControl.value &&
        totalControl.value &&
        Number(maxControl.value) <= Number(totalControl.value)
      ) {
        maxControl.setErrors({ maxNotGreaterThanTotal: true });
      } else {
        if (maxControl.hasError('maxNotGreaterThanTotal')) {
          delete maxControl.errors!['maxNotGreaterThanTotal'];
          if (!Object.keys(maxControl.errors!).length) {
            maxControl.setErrors(null);
          }
        }
      }
    };
  }

  onSubmit() {
    if (this.configForm.valid) {
      const payload = {
        totalTickets: this.configForm.value.totalTicketCapacity,
        ticketReleaseRate: this.configForm.value.ticketReleaseRate,
        customerRetrievalRate: this.configForm.value.customerRetrievalRate,
        maxTicketCapacity: this.configForm.value.maxTicketCapacity,
        vendorCount: this.configForm.value.vendorCount,
        customerCount: this.configForm.value.customerCount,
      };

      axios
        .post('http://localhost:8080/api/configurations', payload)
        .then((response) => {
          console.log('Form submitted successfully', response);
        })
        .catch((error) => {
          console.error('Error submitting form', error);
        });
    } else {
      console.log('Form is invalid');
    }
  }

  onReset() {
    this.configForm.reset();
  }

  navigateToControlPanel() {
    this.router.navigate(['/control-panel']);
  }
}
