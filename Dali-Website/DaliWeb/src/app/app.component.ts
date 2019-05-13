import { AuthenticationService } from './_services/authentication.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.styl']
})
export class AppComponent {
  title = 'Dali';

  constructor(private authenticationService: AuthenticationService) {
    authenticationService.init();
  }
}
