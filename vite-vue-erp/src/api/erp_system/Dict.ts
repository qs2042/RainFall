import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}


interface IDict {
    id: number;
    code: string;
    name: string;
    description: string;
    flag: boolean;
    createdAt: string;
    updatedAt: string;
}

const dictApi = {
    add(entity: IDict) {
        return request.post(toUrl("dict", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("dict", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("dict", "removeList"), {
            ids
        })
    },
    update(entity: IDict) {
        return request.post(toUrl("dict", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("dict", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}



export {
    IDict, dictApi
}
