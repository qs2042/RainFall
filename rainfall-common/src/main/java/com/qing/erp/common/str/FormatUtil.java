package com.qing.erp.common.str;

import java.text.DecimalFormat;

public class FormatUtil {
    // 将文件大小转成字符串
    public static String convertFileSizeToString(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0 B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + " B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + " KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + " MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + " GB";
        }
        return fileSizeString;
    }

    public static String convertSeconds(long seconds) {
        if (seconds < 60) {
            return seconds + "秒";
        } else {
            long minutes = seconds / 60;
            long remainingSeconds = seconds % 60;
            if (minutes < 60) {
                return minutes + "分钟 " + remainingSeconds + "秒";
            } else {
                long hours = minutes / 60;
                long remainingMinutes = minutes % 60;
                return hours + "小时 " + remainingMinutes + "分钟 " + remainingSeconds + "秒";
            }
        }
    }
}
