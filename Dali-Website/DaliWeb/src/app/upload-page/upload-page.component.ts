import { TagsChipsAutocompleteComponent } from './../tags-chips-autocomplete/tags-chips-autocomplete.component';
import { FileHandle } from './../_models/file-handle';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RestService } from '../_services/rest.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '../_services/authentication.service';

@Component({
  selector: 'app-upload-page',
  templateUrl: './upload-page.component.html',
  styleUrls: ['./upload-page.component.styl']
})
export class UploadPageComponent implements OnInit {

  uploadForm: FormGroup;
  submitted: boolean = false;
  success: boolean = false;

  files: FileHandle[] = [];

  public placedMarker = null;
  public markerInfo;

  @ViewChild(TagsChipsAutocompleteComponent) tagsComponent: TagsChipsAutocompleteComponent;

  constructor(private formBuilder: FormBuilder, private rest: RestService, private router: Router,
     private auth: AuthenticationService) {
    this.uploadForm = this.formBuilder.group({
      name: ['', Validators.required],
      info: ['', Validators.required],
      file: ['', Validators.required],
      tags: ['', Validators.required]
    });
   }

  ngOnInit() {
    
  }

  filesDropped(files: FileHandle[]) {
    this.files = files;
  }

  onSubmit() {
    this.submitted = true;
    var formData = new FormData();
    formData.append('file', this.files[0].file);
    formData.append('name', this.uploadForm.get('name').value);
    formData.append('artistId', '1');
    formData.append('tagId', '1');
    formData.append('info', this.uploadForm.get('info').value);
    formData.append('lat', this.placedMarker.lat);
    formData.append('lng', this.placedMarker.lng);

    console.log(this.auth.currentUserValue().id.toString());

    this.rest.uploadArtwork(formData).subscribe(response => {
      console.log("inResponse");
      if(response)
        this.router.navigate(['/profile']);
    });
  }

  public placeMarker(lat: number, lng: number) {
    this.placedMarker = {lat, lng};
  }

  public onNameChange(str: string) {
    this.markerInfo = str
  }
}
