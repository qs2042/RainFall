package com.qing.erp.system.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FeedbackCategory {
    FEEDBACK(0, "反馈"),
    BUG_REPORT(1, "Bug报告"),
    FEATURE_REQUEST(2, "功能请求");

    private final int value;
    private final String description;
}