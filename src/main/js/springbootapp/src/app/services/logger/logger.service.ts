import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoggerService {

  log(msg: string) {
    if (!environment.production) {
      console.log(msg)
    }
  }
}
