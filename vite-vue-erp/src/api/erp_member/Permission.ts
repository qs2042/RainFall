import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IPermission {
    id: string;
    code: string;
    name: string;
    introduce: string;
    uri: string;
    flag: string;
    created_at: string;
    updated_at: string;
}

const permissionApi = {
    add(entity: IPermission) {
        return request.post(toUrl("permission", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("permission", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("permission", "removeList"), {
            ids
        })
    },
    update(entity: IPermission) {
        return request.post(toUrl("permission", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("permission", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IPermission, permissionApi
}
