import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITComment {
    id: string;
    posts_id: string;
    user_id: string;
    parent_id: string;
    content: string;
    create_time: string;
}

const tCommentApi = {
    add(entity: ITComment) {
        return request.post(toUrl("tComment", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tComment", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tComment", "removeList"), {
            ids
        })
    },
    update(entity: ITComment) {
        return request.post(toUrl("tComment", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tComment", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITComment, tCommentApi
}