import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITModule {
    id: string;
    title: string;
    icon: string;
    content: string;
    create_time: string;
}

const tModuleApi = {
    add(entity: ITModule) {
        return request.post(toUrl("tModule", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tModule", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tModule", "removeList"), {
            ids
        })
    },
    update(entity: ITModule) {
        return request.post(toUrl("tModule", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tModule", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITModule, tModuleApi
}