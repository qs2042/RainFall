import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IOrganization {
    id: string;
    uuid: string;
    name: string;
    info: string;
    notes: string;
    parent_id: string;
}

const organizationApi = {
    add(entity: IOrganization) {
        return request.post(toUrl("organization", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("organization", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("organization", "removeList"), {
            ids
        })
    },
    update(entity: IOrganization) {
        return request.post(toUrl("organization", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("organization", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IOrganization, organizationApi
}
