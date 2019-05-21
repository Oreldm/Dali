import { FileHandle } from './../_models/file-handle';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RestService } from '../_services/rest.service';

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

  constructor(private formBuilder: FormBuilder, private rest: RestService) {
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
  }

  public placeMarker(lat: number, lng: number) {
    this.placedMarker = {lat, lng};
  }

  public onNameChange(str: string) {
    this.markerInfo = str
  }
}
