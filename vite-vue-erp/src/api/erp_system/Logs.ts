import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}

interface ILogs {
    id: number;
    userId: number;
    reqUri: string;
    reqMethod: string;
    reqParameter: string;
    ip: string;
    city: string;
    equipment: string;
    module: string;
    notes: string;
    method: string;
    spendTime: number;
    result: string;
    status: boolean;
    createdAt: string;
    updatedAt: string;
}

const logsApi = {
    add(entity: ILogs) {
        return request.post(toUrl("logs", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("logs", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("logs", "removeList"), {
            ids
        })
    },
    update(entity: ILogs) {
        return request.post(toUrl("logs", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("logs", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ILogs, logsApi
}
