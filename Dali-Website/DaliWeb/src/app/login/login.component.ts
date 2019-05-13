import { Component, OnInit, NgZone } from '@angular/core';
import { AuthenticationService } from '../_services/authentication.service';
declare const gapi: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.styl']
})
export class LoginComponent implements OnInit {

  public  auth2: any;
  private message: string = "Sign up";
  private authenticationService: AuthenticationService;

  constructor(authenticationService: AuthenticationService) {
    this.authenticationService = authenticationService;
   }

  ngOnInit() {
  }
  
  ngAfterViewInit(){
    //this.authenticationService.attachGoogleSignInBtn(document.getElementById('googleBtn'));
  }

  GoogleSignIn() {
    this.authenticationService.GoogleSignIn();
    /*console.log(this.authenticationService.googleUser);
    console.log(this.authenticationService.googleUser.getAuthResponse().id_token);*/
  }

}
