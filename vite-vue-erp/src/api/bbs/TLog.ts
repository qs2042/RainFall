import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITLog {
    id: string;
    user_id: string;
    module: string;
    method: string;
    type: string;
    content: string;
    equipment: string;
    url: string;
    ip: string;
    mac: string;
    create_time: string;
}

const tLogApi = {
    add(entity: ITLog) {
        return request.post(toUrl("tLog", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tLog", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tLog", "removeList"), {
            ids
        })
    },
    update(entity: ITLog) {
        return request.post(toUrl("tLog", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tLog", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITLog, tLogApi
}