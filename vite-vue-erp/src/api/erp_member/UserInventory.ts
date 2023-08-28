import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IUserInventory {
    id: string;
    user_id: string;
    item_id: string;
    quantity: string;
    created_at: string;
    updated_at: string;
}

const userInventoryApi = {
    add(entity: IUserInventory) {
        return request.post(toUrl("userInventory", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("userInventory", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("userInventory", "removeList"), {
            ids
        })
    },
    update(entity: IUserInventory) {
        return request.post(toUrl("userInventory", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("userInventory", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IUserInventory, userInventoryApi
}
