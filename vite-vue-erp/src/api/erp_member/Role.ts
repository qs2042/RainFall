import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IRole {
    id: string;
    code: string;
    name: string;
    label: string;
    introduce: string;
    flag: string;
    created_at: string;
    updated_at: string;
}

const roleApi = {
    add(entity: IRole) {
        return request.post(toUrl("role", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("role", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("role", "removeList"), {
            ids
        })
    },
    update(entity: IRole) {
        return request.post(toUrl("role", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("role", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IRole, roleApi
}
