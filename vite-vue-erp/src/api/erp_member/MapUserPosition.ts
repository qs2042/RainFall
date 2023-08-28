import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IMapUserPosition {
    id: string;
    user_id: string;
    position_id: string;
}

const mapUserPositionApi = {
    add(entity: IMapUserPosition) {
        return request.post(toUrl("mapUserPosition", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("mapUserPosition", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("mapUserPosition", "removeList"), {
            ids
        })
    },
    update(entity: IMapUserPosition) {
        return request.post(toUrl("mapUserPosition", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("mapUserPosition", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IMapUserPosition, mapUserPositionApi
}
