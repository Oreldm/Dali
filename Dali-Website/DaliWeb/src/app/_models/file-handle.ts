import { SafeUrl } from '@angular/platform-browser';

export class FileHandle {
    file: File
    url: SafeUrl

    constructor(file: File, url: SafeUrl) {
        this.file = file;
        this.url = url;
    }
}
