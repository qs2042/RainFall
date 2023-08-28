import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IMapRolePermission {
    id: string;
    role_id: string;
    permission_id: string;
}

const mapRolePermissionApi = {
    add(entity: IMapRolePermission) {
        return request.post(toUrl("mapRolePermission", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("mapRolePermission", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("mapRolePermission", "removeList"), {
            ids
        })
    },
    update(entity: IMapRolePermission) {
        return request.post(toUrl("mapRolePermission", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("mapRolePermission", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IMapRolePermission, mapRolePermissionApi
}
