import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}

interface IDevLogs {
    id: number;
    userId: number;
    projectId: number;
    taskName: string;
    taskDescription: string;
    hoursSpent: string;
    createdAt: string;
    updatedAt: string;
}

const devLogsApi = {
    add(entity: IDevLogs) {
        return request.post(toUrl("devLogs", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("devLogs", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("devLogs", "removeList"), {
            ids
        })
    },
    update(entity: IDevLogs) {
        return request.post(toUrl("devLogs", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("devLogs", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IDevLogs, devLogsApi
}
