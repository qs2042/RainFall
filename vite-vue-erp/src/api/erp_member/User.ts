import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IUser {
    id: number;
    uuid: string;
    account: string;
    password: string;
    flag: boolean;
    notes: string;
}

const userApi = {
    add(entity: IUser) {
        return request.post(toUrl("user", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("user", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("user", "removeList"), {
            ids
        })
    },
    update(entity: IUser) {
        return request.post(toUrl("user", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("user", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },

    login(account, password) {
        return request.post(toUrl("user", "login"), {
            account, password
        })
    },
}

export {
    IUser, userApi
}
