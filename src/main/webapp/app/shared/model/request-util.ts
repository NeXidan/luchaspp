import { URLSearchParams, BaseRequestOptions } from '@angular/http';

export const createRequestOption = (req?: any): BaseRequestOptions => {
    const options: BaseRequestOptions = new BaseRequestOptions();
    if (req) {
        const {page, size, sort, query, ...rest} = req;

        const params: URLSearchParams = new URLSearchParams();
        params.set('page', page);
        params.set('size', size);
        if (sort) {
            params.paramsMap.set('sort', sort);
        }
        params.set('query', query);

        for (const option in rest) {
            if (rest[option]) {
                params.set(option, rest[option]);
            }
        }

        options.search = params;
    }
    return options;
};
