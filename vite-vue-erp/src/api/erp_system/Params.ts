import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}


interface IParams {
    id: number;
    key: string;
    value: string;
    description: string;
    flag: boolean;
    createdAt: string;
    updatedAt: string;
}

const paramsApi = {
    add(entity: IParams) {
        return request.post(toUrl("params", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("params", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("params", "removeList"), {
            ids
        })
    },
    update(entity: IParams) {
        return request.post(toUrl("params", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("params", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}



export {
    IParams, paramsApi
}
