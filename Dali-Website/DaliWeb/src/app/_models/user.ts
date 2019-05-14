import { UserType } from './user-type.enum';

export class User {
    public id: number;
    public token: string;
    public username: string;
    public email: string;
    public type: UserType;

    constructor(userInfo: any, userType: UserType) {
        switch(userType) {
            case UserType.GOOGLE:
                this.initGoogleUser(userInfo);
                break;
        }

        this.type = userType;
    }

    private initGoogleUser(userInfo: any) {
        let profile = userInfo.getBasicProfile();
        this.id = profile.getId()
        this.token = userInfo.getAuthResponse().id_token;
        this.username = profile.getName();
        this.email = profile.getEmail();
    }
}