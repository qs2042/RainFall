const date = {
    getLocaleTimeString: () => new Date().toLocaleTimeString(),

    getCurrentTimeString: () => {
        const addZero = (num: number): string => {
            return num < 10 ? `0${num}` : `${num}`;
        };

        const date = new Date();
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        const hour = date.getHours();
        const minute = date.getMinutes();
        const second = date.getSeconds();
        const dayOfWeek = ['日', '一', '二', '三', '四', '五', '六'][date.getDay()];

        return `${year}年${addZero(month)}月${addZero(day)}日 ${addZero(hour)}:${addZero(minute)}:${addZero(second)} 星期${dayOfWeek}`;
    }
}

const random = {
    getEpitaph: (): string => {
        const epitaphs: string[] = [
            '永远相信美好的事情即将发生.',
            '越过山丘,才发现无人等候.',
            '生命不息,奋斗不止.',
            '路漫漫其修远兮,吾将上下而求索.',
            '生命如火,奋斗如歌.',
            '宁可一日清闲,莫做一日空闲.',
            '独行快,众行远.',
            '路在脚下,心向远方.',
            '天行健,君子以自强不息.',
            '人生就像一场旅行,不在乎目的地,在乎沿途的风景.',
        ];

        const randomIndex: number = Math.floor(Math.random() * epitaphs.length);

        return epitaphs[randomIndex];
    }
}

const network = {
    openInNewTab: (url) => {
        const win = window.open(url, '_blank');
        win.focus();
    }
}

const styleUtil = {
    copyColorByClass(selectors) {
        const myElement = document.querySelector(selectors);
        const computedStyle = getComputedStyle(myElement);
        return computedStyle.color;
    },
    copyBackgroundColorByClass(selectors) {
        const myElement = document.querySelector(selectors);
        const computedStyle = getComputedStyle(myElement);
        return computedStyle.backgroundColor;
    }
}

const strUtil = {
    convertNewlinesToBr(str: string): string {
        return str.replace(/\n/g, '<br>')
    },

    removePrefix(text, prefix): string {
    if (text.startsWith(prefix)) {
        return text.substring(prefix.length);
    } else {
        return text;
    }
}
}

const localStorageUtil = {
    setItem: (key, value) => {
        if (typeof value === 'object') {
            value = JSON.stringify(value);
        }
        localStorage.setItem(key, value);
    },

    getItem: (key, type = 'string') => {
        let value = localStorage.getItem(key);

        if (value) {
            switch (type) {
                case 'string':
                    return value;
                case 'number':
                    return Number(value);
                case 'boolean':
                    return value === 'true';
                case 'object':
                    return JSON.parse(value);
                default:
                    throw new Error('Unsupported data type');
            }
        }
    },

    removeItem: (key) =>  {
        localStorage.removeItem(key);
    }
}

export {
    random, date, network, styleUtil, strUtil, localStorageUtil
}
