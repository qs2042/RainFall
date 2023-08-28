import {defineStore} from 'pinia'
import type {MenuTheme} from 'ant-design-vue';

export default defineStore({
    id: 'settingsStore',
    state: () => ({
        // 是否显示组件
        menu: true,
        headers: true,
        breadcrumb: true,
        tags: true,
        logo: true,
        welcomeScreen: false,

        // 菜单
        menuCollapsed: false,

        // 网站图标/标题
        icon: "vite.svg",
        title: "Rainfall后台管理系统 v1.0",

        // 网站logo/名称
        logoIcon: "windows-outlined",
        logoTitle: "Rainfall系统",

        // 网站描述
        introduce: "基于SpringCloud开发的轻量级Java快速开发框架",
        // 版权信息
        copyrightInformation: "Copyright © 2022-2023 halfRain, All rights reserved. Version: 1.0.0",
        // 关键词
        keyWorlds: [],

        // 字体
        font: "",
        fontSize: 18,
        fontList: [
            {'name': '默认', 'value': ''},
            {'name': '宋体', 'value': 'SimSun'},
            {'name': '黑体', 'value': 'SimHei'},
            {'name': '微软雅黑', 'value': 'Microsoft YaHei'},
            {'name': '微软正黑体', 'value': 'Microsoft JhengHei'},
            {'name': '新宋体', 'value': 'NSimSun'},
            {'name': '新细明体', 'value': 'PMingLiU'},
            {'name': '细明体', 'value': 'MingLiU'},
            {'name': '标楷体', 'value': 'DFKai-SB'},
            {'name': '仿宋', 'value': 'FangSong'},
            {'name': '楷体', 'value': 'KaiTi'},
            {'name': '仿宋_GB2312', 'value': 'FangSong_GB2312'},
            {'name': '楷体_GB2312', 'value': 'KaiTi_GB2312'},
        ],

        // 语言
        language: "zh",
        languageList: [
            {name: "英语", value: "en"},
            {name: "中文", value: "zh"},
            {name: "法语", value: "fr"},
            {name: "德语", value: "de"},
            {name: "日语", value: "ja"},
            {name: "韩语", value: "ko"},
            {name: "俄语", value: "ru"},
            {name: "西班牙语", value: "es"},
            {name: "葡萄牙语", value: "pt"},
            {name: "意大利语", value: "it"}
        ],

        // 背景图片/颜色
        bgImage: "",
        bgColor: "#f0f2f5",
        bgMusic: 0,
        bgMusicList: [
            { id: 0, name:"蒸汽"},
            { id: 1, name:"我相信"},
            { id: 2, name:"伴奏"}
        ],

        // 组件透明度/大小
        componentAlpha: 100,
        componentSizeList: [
            { id: 1, label: "大", value: "default" },
            { id: 2, label: "中", value: "middle" },
            { id: 3, label: "小", value: "small" },
        ],
        // 按钮, 表格
        componentSizeButton: "default",
        componentSizeTable: "default",

        // 主题: light|dark(default)
        theme: false,
        themeStr: "dark" as MenuTheme,
        themeStyles: {
            "dark": {
                color: "#ffffff", backgroundColor: "#001529"
            },
            "light": {
                color: "#000000", backgroundColor: "#ffffff"
            },
        }
    }),
    getters: {
        getTheme(state) {
            return state.theme ? "light" : "dark";
        },
        getThemeStyles(state) {
            return state.themeStyles[state.getTheme];
        },
        getThemeStylesReverse(state) {
            return state.themeStyles[!state.theme ? "light" : "dark"];
        },
    },
    actions: {},
})


