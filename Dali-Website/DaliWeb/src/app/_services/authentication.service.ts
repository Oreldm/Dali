import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { User, UserType } from '../_models';

declare const gapi: any;
const SESSION_USER_KEY: string = 'currentUser';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {

  

  //GoogleAPI
  public  auth2: any;

  public googleUser: any;

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  public isLogin: boolean = false;

  constructor() {
    console.log("IN SERVICE CONSTRUCTOR!!!");
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(sessionStorage.getItem(SESSION_USER_KEY)));
    this.currentUser = this.currentUserSubject.asObservable();

    if(this.currentUserValue()) {
      this.isLogin = true;
    }
  }

   public init() {
    this.googleInit();
   }

   public googleInit() {
    gapi.load('auth2', () => {
      this.auth2 = gapi.auth2.init({
        client_id: '146644312889-omcthjokcqf9qts63mbijevfokgrbag2.apps.googleusercontent.com',
        cookiepolicy: 'single_host_origin',
        scope: 'profile email'
      });
      console.log("END: googleInit");
      //this.attachSignin(document.getElementById('googleBtn'));
    });
  }

  public GoogleSignIn() {
    this.auth2.signIn()
    .then(res => this.onGoogleSignIn(res))
    .catch(err => this.onGoogleSignInFailed(err));
  }

  private onGoogleSignIn(googleUser: any) {
    let user = new User(googleUser, UserType.GOOGLE);
    sessionStorage.setItem(SESSION_USER_KEY, JSON.stringify(user));
    this.currentUserSubject.next(user);

    this.isLogin = true;
  }

  private onGoogleSignInFailed(err: any) {
    console.error(err)
  }

  public signOut() {
    switch(this.currentUserSubject.value.type) {
      case UserType.GOOGLE:
        this.auth2.signOut();
        break;
    }

    sessionStorage.removeItem(SESSION_USER_KEY)
    this.currentUserSubject.next(null);

    this.isLogin = true;
  }

  public currentUserValue(): User {
    return this.currentUserSubject.value;
  }

}
