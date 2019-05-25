import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AgmCoreModule } from '@agm/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { GoogleButtonComponent } from './google-button/google-button.component';
import { UploadPageComponent } from './upload-page/upload-page.component';
import { MaterialModule } from './material-module';
import { DragFileDirective } from './_directives/drag-file.directive';
import { TagsChipsAutocompleteComponent } from './tags-chips-autocomplete/tags-chips-autocomplete.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    LoginComponent,
    ProfileComponent,
    GoogleButtonComponent,
    UploadPageComponent,
    DragFileDirective,
    TagsChipsAutocompleteComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MaterialModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyA4xC73J6YmH-K-7QMLWPFVnPQZHUdcc2o'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
