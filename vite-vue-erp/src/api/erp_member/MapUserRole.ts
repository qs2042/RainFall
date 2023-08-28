import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IMapUserRole {
    id: string;
    user_id: string;
    role_id: string;
}

const mapUserRoleApi = {
    add(entity: IMapUserRole) {
        return request.post(toUrl("mapUserRole", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("mapUserRole", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("mapUserRole", "removeList"), {
            ids
        })
    },
    update(entity: IMapUserRole) {
        return request.post(toUrl("mapUserRole", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("mapUserRole", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IMapUserRole, mapUserRoleApi
}
