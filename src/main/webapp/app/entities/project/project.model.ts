
const enum ProjectStatus {
    'DEVELOPING',
    'SUPPORTING',
    'ARCHIVED'

};
import { User } from '../../shared';
export class Project {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public status?: ProjectStatus,
        public manager?: User,
    ) {
    }
}
