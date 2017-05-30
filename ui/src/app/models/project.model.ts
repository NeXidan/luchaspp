export class Project {
    id: number;
    name: string;
    description: string;
    status: number;

    constructor({id, name, description, status} = <any>{}) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }
}
