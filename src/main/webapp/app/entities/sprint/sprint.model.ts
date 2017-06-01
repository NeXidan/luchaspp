import { Project } from '../project';
export class Sprint {
    constructor(
        public id?: number,
        public name?: string,
        public fromDate?: any,
        public toDate?: any,
        public project?: Project,
    ) {
    }
}
