import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITDiaryPosts {
    id: string;
    diary_partition_id: string;
    user_id: string;
    title: string;
    image: string;
    content: string;
    create_time: string;
}

const tDiaryPostsApi = {
    add(entity: ITDiaryPosts) {
        return request.post(toUrl("tDiaryPosts", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tDiaryPosts", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tDiaryPosts", "removeList"), {
            ids
        })
    },
    update(entity: ITDiaryPosts) {
        return request.post(toUrl("tDiaryPosts", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tDiaryPosts", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITDiaryPosts, tDiaryPostsApi
}