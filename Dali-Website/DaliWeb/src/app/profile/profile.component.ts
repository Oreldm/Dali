import { Component, OnInit } from '@angular/core';
import { RestService } from '../_services/rest.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.styl']
})
export class ProfileComponent implements OnInit {

  public markers: any[] = [];
  public profile: any;
  public pictureUrl = "";
  public artistName = "";
  public artistBio = "";

  constructor(private rest: RestService) { }

  ngOnInit() {
    this.rest.getProfile(1).subscribe(response => {
      this.profile = response;
      this.setMarkers(this.profile.artworks)
      this.profile.pictureUrl = "https://avatars0.githubusercontent.com/u/9754901?s=460&v=4"

      this.pictureUrl = this.profile.pictureUrl;
      this.artistName = this.profile.name;
      this.artistBio = this.profile.bio;
      console.log(this.profile)
    });
  }

  private setMarkers(artworks: any[]) {
    artworks.forEach(artwork => {
      this.markers.push({
        id: artwork.id,
        name: artwork.name,
        lat: artwork.positionX,
        lng: artwork.positionY
      });
      console.log(artwork);
    })
  }
}
