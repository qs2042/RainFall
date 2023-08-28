import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IUserInfo {
    id: string;
    user_id: string;
    nick: string;
    face: string;
    age: string;
    sex: string;
    introduce: string;
    register_ip: string;
    created_at: string;
    updated_at: string;
}

const userInfoApi = {
    add(entity: IUserInfo) {
        return request.post(toUrl("userInfo", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("userInfo", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("userInfo", "removeList"), {
            ids
        })
    },
    update(entity: IUserInfo) {
        return request.post(toUrl("userInfo", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("userInfo", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IUserInfo, userInfoApi
}
