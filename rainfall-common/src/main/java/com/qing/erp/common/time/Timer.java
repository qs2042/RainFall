package com.qing.erp.common.time;

import lombok.val;

public class Timer {
    private final Long timestamp = System.currentTimeMillis();

    public static Timer timer() { return new Timer(); }

    public Integer stop() {
        val end = System.currentTimeMillis();
        return Math.toIntExact(end - timestamp);
    }
    public String stopByString() {
        val end = System.currentTimeMillis();
        return String.valueOf(end - timestamp);
    }
}
