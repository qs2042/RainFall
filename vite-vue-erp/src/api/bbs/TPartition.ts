import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITPartition {
    id: string;
    module_id: string;
    title: string;
    icon: string;
    content: string;
    create_time: string;
}

const tPartitionApi = {
    add(entity: ITPartition) {
        return request.post(toUrl("tPartition", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tPartition", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tPartition", "removeList"), {
            ids
        })
    },
    update(entity: ITPartition) {
        return request.post(toUrl("tPartition", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tPartition", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITPartition, tPartitionApi
}