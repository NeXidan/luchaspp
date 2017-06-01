import { Project } from '../project';
import { Task } from '../task';

export class Tag {
    constructor(
        public id?: number,
        public name?: string,
        public project?: Project,
        public task?: Task
    ) {
    }
}
