import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/member/` + args.join("/")
}

interface IUserSecurity {
    id: string;
    user_id: string;
    email: string;
    phone_number: string;
    qq: string;
    security_questions: string;
    updated_at: string;
}

const userSecurityApi = {
    add(entity: IUserSecurity) {
        return request.post(toUrl("userSecurity", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("userSecurity", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("userSecurity", "removeList"), {
            ids
        })
    },
    update(entity: IUserSecurity) {
        return request.post(toUrl("userSecurity", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("userSecurity", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IUserSecurity, userSecurityApi
}
