import request from "@/plugins/axios/index.ts"
const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}

interface IAdvertisements {
    id: string;
    title: string;
    imageUrl: string;
    linkUrl: string;
    startDate: string;
    endDate: string;
    createdAt: string;
    updatedAt: string;
}

const advertisementsApi = {
    add(entity: IAdvertisements) {
        return request.post(toUrl("advertisements", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("advertisements", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("advertisements", "removeList"), {
            ids
        })
    },
    update(entity: IAdvertisements) {
        return request.post(toUrl("advertisements", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("advertisements", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    IAdvertisements, advertisementsApi
}
