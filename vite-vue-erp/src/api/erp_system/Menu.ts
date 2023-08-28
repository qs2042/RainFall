import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}

interface IMenu {
    id: number;
    parentId: number;
    title: string;
    icon: string;
    url: string;
    isRedirect: boolean;
    content: string;
    sort: number;
    flag: boolean;
    createdAt: string;
    updatedAt: string;
}

const menuApi = {
    add(entity: IMenu) {
        return request.post(toUrl("menu", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("menu", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("menu", "removeList"), {
            ids
        })
    },
    update(entity: IMenu) {
        return request.post(toUrl("menu", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("menu", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
    queryTree(flag = undefined) {
        return request.get(toUrl("menu", "queryTree"), {
            params: { flag }
        })
    },
}

export {
    IMenu, menuApi
}
