# Vue 3 + TypeScript + Vite

This template should help get you started developing with Vue 3 and TypeScript in Vite. The template uses Vue
3 `<script setup>` SFCs, check out
the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

## Recommended IDE Setup

- [VS Code](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (
  and disable Vetur)
  + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin)
  .

## Type Support For `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for
type checking. In editors, we
need [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin)
to make the TypeScript language service aware of `.vue` types.

If the standalone TypeScript plugin doesn't feel fast enough to you, Volar has also implemented
a [Take Over Mode](https://github.com/johnsoncodehk/volar/discussions/471#discussioncomment-1361669) that is more
performant. You can enable it by the following steps:

1. Disable the built-in TypeScript Extension
    1. Run `Extensions: Show Built-in Extensions` from VSCode's command palette
    2. Find `TypeScript and JavaScript Language Features`, right click and select `Disable (Workspace)`
2. Reload the VSCode window by running `Developer: Reload Window` from the command palette.

# 创建项目(vite2)

```shell
# 因为只有vite2支持node 12+, vite3 需要 node 14+, 而win7不支持
# 2.7.2 或者 2.9.5
npm create vite@2.9.5
# Project name: ... vite-vue-erp
# Select a framework: » vue
# Select a variant: » vue-ts

# 或者直接用vue模板: vue, vue-ts, react, react-ts
npm create vite@2.9.5 vite-vue-erp --template vue-ts
```

2.9.5,相对2.9之前的版本来说变化较多

比如build后,vendor.js 不再打包为单独的js

如果想分离出vendor.js 需借助splitVendorChunkPlugin插件,使用方式如下:

```js
import {splitVendorChunkPlugin} from 'vite'

plugins: [splitVendorChunkPlugin()]
```

# 创建项目(vite3)

## 安装NVM

**nvm-setup.exe**

我的电脑有安装 Node v12.18.4

使用 nvm-setup.exe 进行安装, 会有一个提示: Node v12.18.4 is already installed. do you want nvm to control this version

点击确认让它接管即可(之前配置的npm就无效了)

**nvm-oninstall.zip**

或者用免安装的绿色版 nvm-oninstall.zip 但是需要进行配置

新建 settings.txt 文件

```text
root: NVM安装路径
path: node安装路径
```

0. 高级系统设置 —> 环境变量 —> 系统变量 —> NVM_HOME: NVM安装路径
0. 高级系统设置 —> 环境变量 —> 系统变量 —> NVM_SYMLINK: NODE安装路径
0. 最后在path中写入%NVM_HOME%;%NVM_SYMLINK%

## 安装NodeJS

0. https://nodejs.org/zh-cn/
0. 点击其他下载(如果需要下载其他版本),下拉到网页底部,找到以往的版本点击进入.
0. 选择win-x64.zip 结尾的安装包(例如node-v16.17.0-win-x64.zip )
0. 将下载好的安装包解压到nvm目录下
0. 然后将文件名称改为v16.17.0(下载的哪个版本就改成哪个版本)

## 配置环境

0. 右键: 计算机
0. 左键: 属性
0. 左键: 高级系统设置
0. 左键: 高级: 环境变量

0. 系统栏: 新建: Key=NVM_HOME, Value=F:\program\nvm
0. 系统栏: 新建: Key=NVM_SYMLINK, Value=F:\program\nvm\v12.18.4
0. 系统栏: 编辑Path: %NVM_HOME%;%NVM_SYMLINK%

## 测试

```shell
nvm list

npm -v
node -v

nvm install 14.15.0
nvm use 14.15.0
```

注意事项: 这里我测试了下, use切换不了node环境, 手动把NVM_SYMLINK改成对应版本即可

## 注意事项

更新了npm版本后, 旧的vue项目会报错, 准确来说是vue-cli依赖会报错: 

```shell
> ruoyi@3.8.5 dev
> vue-cli-service serve

node:os:68
      throw new ERR_SYSTEM_ERROR(ctx);
      ^

SystemError [ERR_SYSTEM_ERROR]: A system error occurred: uv_os_gethostname retur
ned ENOSYS (function not implemented)
    at new Defaults (C:\Users\Administrator\Desktop\RWeb\java-other\RuoYi-Vue\ru
oyi-ui\node_modules\@achrinza\node-ipc\entities\Defaults.js:26:20)
    at new Parser (C:\Users\Administrator\Desktop\RWeb\java-other\RuoYi-Vue\ruoy
i-ui\node_modules\@achrinza\node-ipc\entities\EventParser.js:8:14)
    at Object.<anonymous> (C:\Users\Administrator\Desktop\RWeb\java-other\RuoYi-
Vue\ruoyi-ui\node_modules\@achrinza\node-ipc\dao\client.js:11:19)
    at Module._compile (node:internal/modules/cjs/loader:1105:14)
    at Object.Module._extensions..js (node:internal/modules/cjs/loader:1159:10)
    at Module.load (node:internal/modules/cjs/loader:981:32)
    at Function.Module._load (node:internal/modules/cjs/loader:822:12)
    at Module.require (node:internal/modules/cjs/loader:1005:19)
    at require (node:internal/modules/cjs/helpers:102:18)
    at Object.<anonymous> (C:\Users\Administrator\Desktop\RWeb\java-other\RuoYi-
Vue\ruoyi-ui\node_modules\@achrinza\node-ipc\services\IPC.js:4:14) {
  code: 'ERR_SYSTEM_ERROR',
  info: {
    errno: -4054,
    code: 'ENOSYS',
    message: 'function not implemented',
    syscall: 'uv_os_gethostname'
  },
  errno: [Getter/Setter],
  syscall: [Getter/Setter]
}
```

试了试网上说的方法都不好使, 我决定重新下载依赖, 先查看vue-cli的版本是多少

```shell
npm ls
+-- @babel/parser@7.17.9 extraneous
+-- @ctrl/tinycolor@3.4.1 extraneous
+-- @element-plus/icons-vue@1.1.4 extraneous
+-- @floating-ui/core@0.6.1 extraneous
+-- @floating-ui/dom@0.4.4 extraneous
+-- @popperjs/core@2.11.5 extraneous
+-- @vue/compiler-core@3.2.31 extraneous
+-- @vue/compiler-dom@3.2.31 extraneous
+-- @vue/compiler-sfc@3.2.31 extraneous
+-- @vue/compiler-ssr@3.2.31 extraneous
+-- @vue/reactivity-transform@3.2.31 extraneous
+-- @vue/reactivity@3.2.31 extraneous
+-- @vue/runtime-core@3.2.31 extraneous
+-- @vue/runtime-dom@3.2.31 extraneous
+-- @vue/server-renderer@3.2.31 extraneous
+-- @vue/shared@3.2.31 extraneous
+-- @vueuse/core@8.2.5 extraneous
+-- @vueuse/metadata@8.2.5 extraneous
+-- @vueuse/shared@8.2.5 extraneous
+-- async-validator@4.0.7 extraneous
+-- csstype@2.6.20 extraneous
+-- dayjs@1.11.0 extraneous
+-- element-plus@2.1.8 extraneous
+-- escape-html@1.0.3 extraneous
+-- estree-walker@2.0.2 extraneous
+-- lodash-es@4.17.21 extraneous
+-- lodash-unified@1.0.2 extraneous
+-- lodash@4.17.21 extraneous
+-- magic-string@0.25.9 extraneous
+-- memoize-one@6.0.0 extraneous
+-- nanoid@3.3.2 extraneous
+-- normalize-wheel-es@1.1.2 extraneous
+-- picocolors@1.0.0 extraneous
+-- postcss@8.4.12 extraneous
+-- source-map-js@1.0.2 extraneous
+-- source-map@0.6.1 extraneous
+-- sourcemap-codec@1.4.8 extraneous
+-- vue-demi@0.12.5 extraneous
`-- vue@3.2.31 extraneous
```

太多了, 指定查看vue的
```shell
npm ls vue
+-- @element-plus/icons-vue@1.1.4 extraneous
| `-- vue@3.2.31 deduped
+-- @vue/server-renderer@3.2.31 extraneous
| `-- vue@3.2.31 deduped
+-- @vueuse/core@8.2.5 extraneous
| `-- vue@3.2.31 deduped
+-- @vueuse/shared@8.2.5 extraneous
| `-- vue@3.2.31 deduped
+-- element-plus@2.1.8 extraneous
| `-- vue@3.2.31 deduped
+-- vue-demi@0.12.5 extraneous
| `-- vue@3.2.31 deduped
`-- vue@3.2.31 extraneous
```

卸载并重新安装
```shell
npm uninstall @vue/cli

npm install -g @vue/cli
# OR
yarn global add @vue/cli
```

最后得出结论, v16.15.0用不了vue-cli命令, v12.18.4和v14.15.0可以使用

切换到v14.15.0版本的node了, 既可以老项目也可以新项目

## 创建项目

```shell
npm create vite@latest vite-vue-erp -- --template vue-ts
# Need to install the following packages: create-vite@latest
# Ok to proceed? (y)
# 输入y

cd vite-vue-erp

npm install

npm install ant-design-vue --save
```





# 

| 真雨凡 | 假雨凡 |
|:---:|:---:|
| 早睡早起 | 经常熬夜
| 华为忠实粉丝 | 买华为平板贪便宜的WiFi版本, 手机坏了不肯买新的
| 辣辣军 | 狗妈？那是谁？
| 爱CG, 高考前还要写CG插件 | 我都不想进CG群聊天了
| 爱学习 | 我要摆烂, 白萌萌和阿水布置的作业？我不写！
| 有当担, 写的CG插件不好不要钱 | 参与学习的项目, 摆烂！
| 富婆 | 呜呜呜, 我没钱了
