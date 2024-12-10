import { Component } from '@angular/core';
import axios from 'axios';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-control-panel',
  templateUrl: './control-panel.component.html',
  styleUrls: ['./control-panel.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ControlPanelComponent {
  start() {
    axios.get('http://localhost:8080/api/configurations/yes')
      .then(response => {
        console.log('Start triggered', response);
      })
      .catch(error => {
        console.error('Error triggering start', error);
      });
  }

  stop() {
    axios.get('http://localhost:8080/api/configurations/no')
      .then(response => {
        console.log('Stop triggered', response);
      })
      .catch(error => {
        console.error('Error triggering stop', error);
      });
  }
}
