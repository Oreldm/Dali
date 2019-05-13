import { FileHandle } from './../_models/file-handle';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

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

  private placedMarker = null;
  private markerInfo;

  constructor(private formBuilder: FormBuilder) {
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

  private placeMarker(lat: number, lng: number) {
    this.placedMarker = {lat, lng};
  }

  private onNameChange(str: string) {
    this.markerInfo = str
  }

}
