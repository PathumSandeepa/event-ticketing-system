import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogWebSocketService {
  private subject: Subject<string>;

  constructor() {
    this.subject = new Subject<string>();
    this.connect();
  }

  private connect() {
    const ws = new WebSocket('ws://localhost:8080/api/ws/logs');

    ws.onmessage = (event) => {
      this.subject.next(event.data);
    };

    ws.onerror = (event) => {
      console.error('WebSocket error:', event);
    };
  }

  getMessages(): Observable<string> {
    return this.subject.asObservable();
  }
}
