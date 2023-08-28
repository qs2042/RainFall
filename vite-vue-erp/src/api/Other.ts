import request from "@/plugins/axios/index"

const toUrl = (module, ...args: string[]) => {
    if (module === null) {
        return "/system/" + args.join("/")
    }
    return `/${module}/` + args.join("/")
}

const monitorApi = {
    repo(module = null) {
        return request.get(toUrl(module, "server", "repo"))
    },
    all(module = null) {
        return request.get(toUrl(module, "server", "all"))
    },
    cpu(module = null) {
        return request.get(toUrl(module, "server", "cpu"))
    },
    physicalMemory(module = null) {
        return request.get(toUrl(module, "server", "physicalMemory"))
    },
    hdd(module = null) {
        return request.get(toUrl(module, "server", "hdd"))
    },
    network(module = null) {
        return request.get(toUrl(module, "server", "network"))
    },
    project(module = null) {
        return request.get(toUrl(module, "server", "project"))
    },
    jvm(module = null) {
        return request.get(toUrl(module, "server", "jvm"))
    },
    redis(module = null) {
        return request.get(toUrl(module, "server", "redis"))
    },
    cacheList(module = null) {
        return request.get(toUrl(module, "server", "cacheList"))
    },
    docker(module = null) {
        return request.get(toUrl(module, "server", "docker"))
    },
    maven(module = null) {
        return request.get(toUrl(module, "server", "maven"))
    },

    getServiceList(module = null) {
        return request.get(toUrl(module, "nacos", "getServiceList"))
    },
    getServiceInstanceList(serviceName: string, module = null) {
        return request.get(toUrl(module, "nacos", "getServiceInstanceList"), {
            params: {serviceName}
        })
    }
}

const wsApi = {
    info() {
        return request.get(toUrl("system", "ws", "info"))
    },
    list() {
        return request.get(toUrl("system", "ws", "list"))
    }
}


export {
    monitorApi, wsApi
}
