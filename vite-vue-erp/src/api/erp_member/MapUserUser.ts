import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IMapUserUser {
    id: string;
    user_id: string;
    friend_id: string;
    status: string;
    created_at: string;
    updated_at: string;
}

const mapUserUserApi = {
    add(entity: IMapUserUser) {
        return request.post(toUrl("mapUserUser", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("mapUserUser", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("mapUserUser", "removeList"), {
            ids
        })
    },
    update(entity: IMapUserUser) {
        return request.post(toUrl("mapUserUser", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("mapUserUser", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IMapUserUser, mapUserUserApi
}
