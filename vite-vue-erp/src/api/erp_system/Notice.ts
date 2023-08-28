import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}

interface INotice {
    id: number;
    title: string;
    content: string;
    type: number;
    flag: boolean;
    createdAt: string;
    updatedAt: string;
}

const noticeApi = {
    add(entity: INotice) {
        return request.post(toUrl("notice", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("notice", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("notice", "removeList"), {
            ids
        })
    },
    update(entity: INotice) {
        return request.post(toUrl("notice", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("notice", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    INotice, noticeApi
}
