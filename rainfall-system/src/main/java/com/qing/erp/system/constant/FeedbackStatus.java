package com.qing.erp.system.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FeedbackStatus {
    PENDING(0, "待处理"),
    IN_PROGRESS(1, "处理中"),
    RESOLVED(2, "已解决");

    private final int value;
    private final String description;
}