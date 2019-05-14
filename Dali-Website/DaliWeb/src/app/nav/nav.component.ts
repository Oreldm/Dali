import { AuthenticationService } from './../_services/authentication.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.styl']
})
export class NavComponent implements OnInit {

  appTitle: string = "Dali"
  public authenticationService: AuthenticationService;
  
  constructor(authenticationService: AuthenticationService) {
    this.authenticationService = authenticationService;
   }

  ngOnInit() {
  }

}
