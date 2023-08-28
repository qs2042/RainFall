package com.qing.erp.common.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.val;


@AllArgsConstructor
@Getter
@ToString
public enum URLUtil {

    HTTP(URLUtil.TYPE_HTTP, "http://.*?"),
    HTTPS(URLUtil.TYPE_HTTPS, "https://.*?"),
    WS(URLUtil.TYPE_WS, "ws://.*?"),
    FTP(URLUtil.TYPE_FTP, "ftp://.*?"),
    FILE(URLUtil.TYPE_FILE, "^(file:///[A-Z]:\\\\|[A-Z]:\\\\).*?");

    public final static String TYPE_HTTP = "http";
    public final static String TYPE_HTTPS = "https";
    public final static String TYPE_WS = "ws";
    public final static String TYPE_FTP = "ftp";
    public final static String TYPE_FILE = "file";

    private final String type;
    private final String identity;
    private final static String suffix;
    static {
        val array = new String[]{
            "jpg", "jpeg", "png", "gif",
            "html", "js", "css"
        };
        val join = String.join("|", array);
        suffix = "[" + join + "]";
    }

    public static URLUtil getType(String url) {
        for (URLUtil value : URLUtil.values()) {
            val matches = url.matches(value.identity);
            if (matches) {
                return value;
            }
        }
        return null;
    }

    public static String getPathEnd(String url) {
        val array = url.split("/");
        return array[array.length-1];
    }


    public static void main(String[] args) {
        System.out.println(getType("https://blog.csdn.net/mei_shen_me/article/details/76161468.gif"));
        System.out.println(getType("http://blog.csdn.net/mei_shen_me/article/details/76161468.png"));
        System.out.println(getType("ftp://asdasd"));
        System.out.println(getType("ws://asdasd"));
        System.out.println(getType("file:///C:\\Users\\Administrator\\Desktop"));
        System.out.println(getType("C:\\Users\\Administrator\\Desktop"));
    }
}
