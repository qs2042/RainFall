import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITMessageBoard {
    id: string;
    user_id: string;
    content: string;
    create_time: string;
}

const tMessageBoardApi = {
    add(entity: ITMessageBoard) {
        return request.post(toUrl("tMessageBoard", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tMessageBoard", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tMessageBoard", "removeList"), {
            ids
        })
    },
    update(entity: ITMessageBoard) {
        return request.post(toUrl("tMessageBoard", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tMessageBoard", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITMessageBoard, tMessageBoardApi
}