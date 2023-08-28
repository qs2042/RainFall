import request from "@/plugins/axios/index"

const toUrl = (...args: string[]) => {
    return `/${databaseName}/` + args.join("/")
}

interface I${tableNameCamel} {
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

const ${tableNameCamelCase}Api = {
    add(entity: I${tableNameCamel}) {
        return request.post(toUrl("${tableNameCamelCase}", "add"), entity)
    },
    remove(id: number) {
        return request.post(toUrl("${tableNameCamelCase}", "remove"), {
            id
        })
    },
    removeList(ids: Array<number>) {
        return request.post(toUrl("${tableNameCamelCase}", "removeList"), {
            ids
        })
    },
    update(entity: I${tableNameCamel}) {
        return request.post(toUrl("${tableNameCamelCase}", "update"), entity)
    },
    queryPage(page = 1, show = 10) {
        return request.get(toUrl("${tableNameCamelCase}", "queryPage"), {
            params: {"page": page - 1, "show": show}
        })
    },
}

export {
    I${tableNameCamel}, ${tableNameCamelCase}Api
}