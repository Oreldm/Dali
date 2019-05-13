import { Directive, Output, EventEmitter, HostBinding, HostListener } from '@angular/core';
import { SafeUrl, DomSanitizer } from '@angular/platform-browser';
import { FileHandle } from '../_models/file-handle';

@Directive({
  selector: '[appDragFile]'
})
export class DragFileDirective {

  @Output() files: EventEmitter<FileHandle[]> = new EventEmitter();
  
  @HostBinding("style.background") private background = "#eee"

  constructor(private sanitizer: DomSanitizer) { }

  @HostListener("dragover", ["$event"]) public onDragOver(evt: DragEvent) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = "#999";
  }

  @HostListener("dragleave", ["$event"]) public onDragLeave(evt: DragEvent) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = "#eee";
  }

  @HostListener('drop', ['$event']) public onDrop(evt: DragEvent) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = "#eee";

    let files: FileHandle[] = [];
    for(let i = 0; i< evt.dataTransfer.files.length; i++) {
      const file = evt.dataTransfer.files[i];
      const url = this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(file));
      files.push(new FileHandle(file, url));
    }
    if(files.length > 0) {
      this.files.emit(files);
    }
  }
}
