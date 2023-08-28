import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/oss/` + args.join("/")
}

const minioApi = {
    getObject(bucketName, objectName) {
        return request.get(toUrl("oss", "getObject"), {
            params: {bucketName, objectName}
        })
    },
}

const baseApi = {
    getObject(bucketName, objectName) {
        return request.get(toUrl("base", "getObject"), {
            params: {bucketName, objectName}
        })
    },
}


export {
    minioApi, baseApi
}
