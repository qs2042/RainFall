import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}


interface IActivity {
    id: number;
    name: string;
    description: string;
    startTime: string;
    endTime: string;
    location: string;
    organizerId: number;
    maxAttendees: number;
    createdAt: string;
    updatedAt: string;
}

const activityApi = {
    add(entity: IActivity) {
        return request.post(toUrl("activity", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("activity", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("activity", "removeList"), {
            ids
        })
    },
    update(entity: IActivity) {
        return request.post(toUrl("activity", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("activity", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}



export {
    IActivity, activityApi
}
