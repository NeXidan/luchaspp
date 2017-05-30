export class User {
    id: number;
    fullName: string;
    phoneNumber: string;
    username: string;

    constructor({id, fullName, phoneNumber, username} = <any>{}) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }
}
