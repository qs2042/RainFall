import {
    AppstoreOutlined,
    BarChartOutlined,
    BellOutlined,
    CheckOutlined,
    ChromeOutlined,
    CodeOutlined,
    EditOutlined,
    ExpandOutlined,
    GithubOutlined,
    HddOutlined,
    InfoCircleOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    RetweetOutlined,
    SearchOutlined,
    FileAddOutlined,
    DeleteOutlined,
    SettingOutlined,
    SlidersOutlined,
    SoundOutlined,
    TeamOutlined,
    ToolOutlined,
    UserOutlined,
    WindowsOutlined,
    CloudDownloadOutlined,
    PlusOutlined, FileImageOutlined, CheckSquareOutlined, CloudUploadOutlined, FormOutlined, BulbOutlined,
    StarOutlined,ReloadOutlined
} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";

// 获取assets静态资源
const resource = {
    getAssetsFile: (url: string) => {
        // 或者直接在setup中使用 import vueIcon from '../assets/vue.svg'
        return new URL(`../assets/${url}`, import.meta.url).href
    },
    getPublicFile: (url: string) => {
        return import.meta.env.BASE_URL + '/public/' + url;
    }
}

const iconUtil = {
    iconJson: {
        // other
        "user-outlined": UserOutlined,
        "check-outlined": CheckOutlined,
        "info-circle-outlined": InfoCircleOutlined,

        // 编辑框
        "file-image-outlined": FileImageOutlined,
        "check-square-outlined": CheckSquareOutlined,
        "cloud-upload-outlined": CloudUploadOutlined,
        "form-outlined": FormOutlined,
        "bulb-outlined": BulbOutlined,
        "star-outlined": StarOutlined,

        // 功能性
        "search-outlined": SearchOutlined,
        "retweet-outlined": RetweetOutlined,
        "reload-outlined": ReloadOutlined,
        "delete-outlined": DeleteOutlined,
        "file-add-outlined": FileAddOutlined,
        "cloud-download-outlined": CloudDownloadOutlined,
        "plus-outlined": PlusOutlined,

        // 导航栏
        "menu-unfold-outlined": MenuUnfoldOutlined,
        "menu-fold-outlined": MenuFoldOutlined,
        "expand-outlined": ExpandOutlined,
        "sound-outlined": SoundOutlined,
        "bell-outlined": BellOutlined,
        "github-outlined": GithubOutlined,

        // 菜单图标
        "appstore-outlined": AppstoreOutlined,
        "windows-outlined": WindowsOutlined,
        "bar-chart-outlined": BarChartOutlined,
        "team-outlined": TeamOutlined,
        "sliders-outlined": SlidersOutlined,
        "hdd-outlined": HddOutlined,
        "tool-outlined": ToolOutlined,
        "edit-outlined": EditOutlined,
        "setting-outlined": SettingOutlined,
        "chrome-outlined": ChromeOutlined,
        "code-outlined": CodeOutlined,
    },
    iconMap: new Map(),
    iconMapInit: () => {
        for (const key in iconUtil.iconJson) {
            if (iconUtil.iconJson.hasOwnProperty(key)) {
                iconUtil.iconMap.set(key, iconUtil.iconJson[key]);
            }
        }
    },
    getIcon: (iconName) => iconUtil.iconMap.get(iconName),
    getRandomIcon: () => {
        const arr = Array.from(iconUtil.iconMap.values());
        const randomIndex = Math.floor(Math.random() * arr.length);
        return arr[randomIndex]
    }
}
iconUtil.iconMapInit()

const apiUtil = {
    getBaseUrl: () => {
        // 本地获取localhost, 服务器上获取106.52.46.241
        const host = window.location.hostname
        return `http://${host}:88`
    },
    getApiUrl: () => {
        return apiUtil.getBaseUrl() + "/api"
    }
}

const otherUtil = {
    copyText: function (text) {
        navigator.clipboard.writeText(text).then(() => {
            message.success("已成功复制到粘贴板")
        }).catch(() => {
            message.error("复制失败")
        })
    }
}

const antdUtil = {
    tableSizeToOtherSize: (size: string) => {
        if (size === "small") {
            return "small"
        } else if (size === "middle") {
            return "default"
        } else if (size === "default") {
            return "large"
        }
        return size;
    }
}

export {
    resource, iconUtil, apiUtil, otherUtil, antdUtil
}
