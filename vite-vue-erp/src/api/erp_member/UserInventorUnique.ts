import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IUserInventorUnique {
    id: string;
    user_id: string;
    item_id: string;
    name: string;
    description: string;
    image_url: string;
    nbt: string;
    created_at: string;
}

const userInventorUniqueApi = {
    add(entity: IUserInventorUnique) {
        return request.post(toUrl("userInventorUnique", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("userInventorUnique", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("userInventorUnique", "removeList"), {
            ids
        })
    },
    update(entity: IUserInventorUnique) {
        return request.post(toUrl("userInventorUnique", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("userInventorUnique", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IUserInventorUnique, userInventorUniqueApi
}
