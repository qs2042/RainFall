import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IDepartment {
    id: string;
    organization_id: string;
    name: string;
    info: string;
    notes: string;
}

const departmentApi = {
    add(entity: IDepartment) {
        return request.post(toUrl("department", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("department", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("department", "removeList"), {
            ids
        })
    },
    update(entity: IDepartment) {
        return request.post(toUrl("department", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("department", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IDepartment, departmentApi
}
