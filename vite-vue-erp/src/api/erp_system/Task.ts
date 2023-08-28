import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}

interface ITask {
    id: number;
    name: string;
    cronExpression: string;
    invokeTarget: string;
    cronDescribe: string;
    enabled: boolean;
    createdAt: string;
    updatedAt: string;
}

const taskApi = {
    add(entity: ITask) {
        return request.post(toUrl("task", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("task", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("task", "removeList"), {
            ids
        })
    },
    update(entity: ITask) {
        return request.post(toUrl("task", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("task", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITask, taskApi
}
