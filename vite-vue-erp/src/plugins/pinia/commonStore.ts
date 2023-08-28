import { defineStore } from 'pinia'

export default defineStore({
    id: 'commonStore',
    state: () => ({
        // 计数
        count: 10,
        // 在线人数
        onlineCount: 0,
        peakOnlineCount: 0,
        // ws
        ws: {
            isOpen: false,
            bean: undefined
        },
        WS_MESSAGE_TYPES: {
            TEXT_MESSAGE: 0,
            BINARY_MESSAGE: 1,
            PING_MESSAGE: 2,
            PONG_MESSAGE: 3,
            CLOSE_MESSAGE: 4,
            SUBSCRIBE_MESSAGE: 5,
            UNSUBSCRIBE_MESSAGE: 6,
            ERROR_MESSAGE: 7,
            HEARTBEAT_MESSAGE: 8,
            BROADCAST_MESSAGE: 9,
            GROUP_CHAT: 100,
            PRIVATE_CHAT: 101,
            NORMAL_MESSAGE: 102,
            ADMIN_MESSAGE: 103,
            SYSTEM_MESSAGE: 104,
            SYSTEM_DATA: 105,
            EMAIL: 106,
            SOCIAL_MEDIA: 107,
            NOTIFICATION: 108,
            TRANSACTION: 109,
            GAME: 110,
            IOT: 111,
            FINANCE: 112,
            HEALTH_CARE: 113,
            EDUCATION: 114,
            NEWS: 115,

            GET_USER_ID_LIST: 200
        }
    }),
    getters: {
        // 算 count 的平方
        countPow2(state) {
            return state.count ** 2;
        },
        countPow2Pro() {
            return this.count ** 2;
        },
    },
    actions: {
        increment() {
            this.count++
        },
        decrement() {
            this.count--
        },
        setWs(bean) {
            this.ws.isOpen = bean !== undefined;
            this.ws.bean = bean
        },
    },
})


