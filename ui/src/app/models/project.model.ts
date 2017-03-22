export class Project {
    id: number;
    name: string;
    description: string;
    status: number;
    createdAt: Date;

    constructor({id, name, description, status, createdAt} = <any>{}) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdAt = new Date(createdAt);
    }
}
