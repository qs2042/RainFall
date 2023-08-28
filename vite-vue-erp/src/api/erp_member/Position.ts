import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IPosition {
    id: string;
    department_id: string;
    name: string;
    info: string;
    notes: string;
}

const positionApi = {
    add(entity: IPosition) {
        return request.post(toUrl("position", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("position", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("position", "removeList"), {
            ids
        })
    },
    update(entity: IPosition) {
        return request.post(toUrl("position", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("position", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IPosition, positionApi
}
