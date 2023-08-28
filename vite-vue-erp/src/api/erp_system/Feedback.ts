import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}


interface IFeedback {
    id: number;
    userId: number;
    text: string;
    date: string;
    rating: number;
    category: number;
    status: number;
    createdAt: string;
}

const feedbackApi = {
    add(entity: IFeedback) {
        return request.post(toUrl("feedback", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("feedback", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("feedback", "removeList"), {
            ids
        })
    },
    update(entity: IFeedback) {
        return request.post(toUrl("feedback", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("feedback", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}



export {
    IFeedback, feedbackApi
}
