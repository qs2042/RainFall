import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITPosts {
    id: string;
    partition_id: string;
    user_id: string;
    title: string;
    icon: string;
    content: string;
    create_time: string;
}

const tPostsApi = {
    add(entity: ITPosts) {
        return request.post(toUrl("tPosts", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tPosts", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tPosts", "removeList"), {
            ids
        })
    },
    update(entity: ITPosts) {
        return request.post(toUrl("tPosts", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tPosts", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITPosts, tPostsApi
}