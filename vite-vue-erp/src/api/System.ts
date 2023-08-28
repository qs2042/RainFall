import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/system/` + args.join("/")
}

// menu
interface IMenu {
    id: number
    parentId: number
    title: string
    icon: string
    url: string
    selfPath: string
    path: string
    isRedirect: boolean
    content: string
    flag: string
    createTime: string
    children: Array<IMenu>
}
const menuApi = {
    add(menu: {}) {
        return request.post(toUrl("menu", "add"), menu)
    },
    update(entity: IMenu) {
        return request.post(toUrl("menu", "update"), entity)
    },
    queryTree(flag = undefined) {
        return request.get(toUrl("menu", "queryTree"), {
            params: { flag }
        })
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("menu", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
    remove(id: number) {
        return request.post(toUrl("menu", "remove"), {
            id
        })
    },
    removeList(ids) {
        return request.post(toUrl("menu", "removeList"), {
            ids
        })
    },
}

// gen
const genApi = {
    getDatabases() {
        return request.get(toUrl("gen", "getDatabases"))
    },
    getTableMetaData(dbName = undefined) {
        return request.get(toUrl("gen", "getTableMetaData"), {
            params: {dbName}
        })
    },
    getTableCode(dbName, tableName, packagePath = undefined, comments = undefined, author = undefined, email = undefined) {
        return request.get(toUrl("gen", "getTableCode"), {
            params: {dbName, tableName, packagePath, comments, author, email}
        })
    },
    getTableAllCode(dbName, packagePath = undefined, comments = undefined, author = undefined, email = undefined) {
        return request.get(toUrl("gen", "getTableAllCode"), {
            params: {dbName, packagePath, comments, author, email}
        })
    },
}

// devLogs
interface IDevLogs {
    id: number;
    userId: number;
    projectId: number;
    taskName: string;
    taskDescription: string;
    hoursSpent: number;
    createdAt: string;
    updatedAt: string;
}
const devLogsApi = {
    add(entity: IDevLogs) {
        return request.post(toUrl("devLogs", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("devLogs", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("devLogs", "removeList"), {
            ids
        })
    },
    update(entity: IDevLogs) {
        return request.post(toUrl("devLogs", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("devLogs", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

// settings
interface ISettings {
    id: number;
    key: string;
    value: string;
    type: string;
    description: string;
    createTime: string;
}

const settingsApi = {
    add(entity: ISettings) {
        return request.post(toUrl("settings", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("settings", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("settings", "removeList"), {
            ids
        })
    },
    update(entity: ISettings) {
        return request.post(toUrl("settings", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("settings", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}


export {
    IMenu, menuApi,
    genApi,
    IDevLogs, devLogsApi,
    ISettings, settingsApi,
}
