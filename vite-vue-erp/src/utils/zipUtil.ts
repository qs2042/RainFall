import JSZip from 'jszip'

interface IFile{
    name: string
    content: string
    folder: string
}

class zipUtil {
    static async zipFiles(files: Array<IFile>, options = {}) {
        // 创建一个新的 JSZip 实例
        const zip = new JSZip()

        // 将每个文件添加到 zip 包中
        for (const file of files) {
            const blob = new Blob([file.content], { type: 'text/plain' })
            if (file.folder) {
                // 如果当前文件对象中包含 folder 属性,则创建一个名为该属性值的文件夹,并将该文件添加到该文件夹中
                const folder = zip.folder(file.folder)
                folder.file(file.name, blob)
            } else {
                // 否则,直接将该文件添加到 zip 包中
                zip.file(file.name, blob)
            }
        }

        // 生成 zip 包
        return await zip.generateAsync({type: 'blob', ...options})
    }
}

export {
    zipUtil, IFile
}
