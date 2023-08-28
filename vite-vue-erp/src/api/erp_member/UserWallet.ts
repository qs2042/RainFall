import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IUserWallet {
    id: string;
    user_id: string;
    coins: string;
    gold: string;
    diamonds: string;
    custom_currency: string;
    updated_at: string;
}

const userWalletApi = {
    add(entity: IUserWallet) {
        return request.post(toUrl("userWallet", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("userWallet", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("userWallet", "removeList"), {
            ids
        })
    },
    update(entity: IUserWallet) {
        return request.post(toUrl("userWallet", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("userWallet", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IUserWallet, userWalletApi
}
