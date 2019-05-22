import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const endpoint = "http://project-dali.com:5000"
const webPath = "/web"
const userPath = "/user"

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Access-Control-Allow-Origin': '*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private http: HttpClient) { }

  public getTags() {
    return this.http.get(endpoint + webPath +  "/getTags");
  }

  public getProfile(id: number) {
    return this.http.get(endpoint + userPath +  "/getProfileById" + "?" + "id=" + id);
  }

  public uploadArtwork(artwork: FormData) {
    return this.http.post(endpoint + webPath + '/uploadArtwork', artwork);
  }
}
