import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/${databaseName}/` + args.join("/")
}

interface I${className} {
<#list fields as test>
    <#if test.type == "Integer">
    ${test.name}: number;
    <#elseif test.type== "Boolean">
    ${test.name}: boolean;
    <#else>
    ${test.name}: string;
    </#if>
</#list>
}

const ${camelCase}Api = {
    add(entity: I${className}) {
        return request.post(toUrl("${camelCase}", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("${camelCase}", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("${camelCase}", "removeList"), {
            ids
        })
    },
    update(entity: I${className}) {
        return request.post(toUrl("${camelCase}", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("${camelCase}", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    I${className}, ${camelCase}Api
}