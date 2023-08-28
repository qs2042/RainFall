import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITJurisdiction {
    id: string;
    user_id: string;
    type: string;
}

const tJurisdictionApi = {
    add(entity: ITJurisdiction) {
        return request.post(toUrl("tJurisdiction", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tJurisdiction", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tJurisdiction", "removeList"), {
            ids
        })
    },
    update(entity: ITJurisdiction) {
        return request.post(toUrl("tJurisdiction", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tJurisdiction", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITJurisdiction, tJurisdictionApi
}