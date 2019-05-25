import { Component, OnInit } from '@angular/core';
import { RestService } from '../_services/rest.service';
import { AuthenticationService } from '../_services/authentication.service';

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

  constructor(private auth: AuthenticationService,private rest: RestService) { }

  ngOnInit() {
    var myUser = this.auth.currentUserValue();
    console.log(myUser);
    this.rest.getProfile(myUser.id).subscribe(response => {
      this.profile = response;
      this.profile.pictureUrl = myUser.pictureUrl;
      this.artistName = this.profile.name;
      this.artistBio = this.profile.bio;
      this.pictureUrl = this.profile.pictureUrl;

      try {
        this.setMarkers(this.profile.artworks)
      } catch(err) {
        console.log("No artworks")
      }
      console.log(this.profile)
    });
  }

  private setMarkers(artworks: any[]) {
    artworks.forEach(artwork => {
      this.markers.push({
        id: artwork.id,
        name: artwork.name,
        lat: artwork.lat,
        lng: artwork.lng
      });
      console.log(artwork);
    })
  }
}
