import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}

interface IFriendLink {
    id: number;
    name: string;
    url: string;
    logoUrl: string;
    description: string;
    sort: number;
    createdAt: string;
    updatedAt: string;
}

const friendLinkApi = {
    add(entity: IFriendLink) {
        return request.post(toUrl("friendLink", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("friendLink", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("friendLink", "removeList"), {
            ids
        })
    },
    update(entity: IFriendLink) {
        return request.post(toUrl("friendLink", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("friendLink", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IFriendLink, friendLinkApi
}
