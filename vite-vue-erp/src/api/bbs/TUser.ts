import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/bbs/` + args.join("/")
}

interface ITUser {
    id: string;
    uuid: string;
    username: string;
    password: string;
    age: string;
    sex: string;
    qq: string;
    phone: string;
    email: string;
    introduce: string;
    image: string;
    create_time: string;
}

const tUserApi = {
    add(entity: ITUser) {
        return request.post(toUrl("tUser", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("tUser", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("tUser", "removeList"), {
            ids
        })
    },
    update(entity: ITUser) {
        return request.post(toUrl("tUser", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("tUser", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    ITUser, tUserApi
}