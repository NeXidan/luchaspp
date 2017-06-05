export const TaskStatus = [
    'OPEN',
    'NEED_INFO',
    'IN_PROGRESS',
    'ON_REVIEW',
    'READY_FOR_RELEASE',
    'CLOSED',
    'WONT_FIX'
];

export const TaskPriority = [
    'LOW',
    'MEDIUM',
    'HIGH',
    'CRITICAL'
];

import { Sprint } from '../sprint';
import { Project } from '../project';
import { User } from '../../shared';
import { Tag } from '../tag';
export class Task {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public status = 'OPEN',
        public priority = 'LOW',
        public createdAt?: any,
        public updatedAt?: any,
        public parentTask?: Task,
        public sprint?: Sprint,
        public project?: Project,
        public author?: User,
        public assignee?: User,
        public watcher?: User,
        public tag?: Tag,
    ) {
    }
}
