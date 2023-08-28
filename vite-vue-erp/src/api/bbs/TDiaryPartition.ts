import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITDiaryPartition {
    id: string;
    title: string;
    image: string;
    content: string;
    create_time: string;
}

const tDiaryPartitionApi = {
    add(entity: ITDiaryPartition) {
        return request.post(toUrl("tDiaryPartition", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tDiaryPartition", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tDiaryPartition", "removeList"), {
            ids
        })
    },
    update(entity: ITDiaryPartition) {
        return request.post(toUrl("tDiaryPartition", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tDiaryPartition", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITDiaryPartition, tDiaryPartitionApi
}