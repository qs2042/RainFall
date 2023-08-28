import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IUserLevel {
    id: string;
    user_id: string;
    level: string;
    experience: string;
    updated_at: string;
}

const userLevelApi = {
    add(entity: IUserLevel) {
        return request.post(toUrl("userLevel", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("userLevel", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("userLevel", "removeList"), {
            ids
        })
    },
    update(entity: IUserLevel) {
        return request.post(toUrl("userLevel", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("userLevel", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IUserLevel, userLevelApi
}
