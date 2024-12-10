import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { CommonModule } from '@angular/common';
import { LogWebSocketService } from './log-websocket.service';

@Component({
  selector: 'app-control-panel',
  templateUrl: './control-panel.component.html',
  styleUrls: ['./control-panel.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ControlPanelComponent implements OnInit {
  logs: string[] = [];

  constructor(private logWebSocketService: LogWebSocketService) {}

  ngOnInit() {
    this.logWebSocketService.getMessages().subscribe((message) => {
      this.logs.push(message);
    });
  }

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
