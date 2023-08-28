package com.qing.erp.system.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeType {
    // 0-99 公司相关
    COMPANY_CHANGE_NOTICE(1, "公司变动公告"),
    EMPLOYEE_TRANSFER_NOTICE(2, "员工调动通知"),
    RECRUITMENT_NOTICE(3, "招聘公告"),
    ANNUAL_REPORT_RELEASE(4, "年度报告发布"),
    TENDER_NOTICE(5, "招标公告"),
    BUSINESS_COOPERATION_NOTICE(6, "业务合作公告"),
    NULL(7, "宣传公告"),

    // 100-199 用户相关
    HOLIDAY_GREETING(100, "节日祝福"),
    WEBSITE_MAINTENANCE_NOTICE(101, "网站维护公告"),
    MEMBER_RIGHTS_UPDATE_NOTICE(105, "会员权益更新通知"),
    SYSTEM_UPGRADE_NOTICE(103, "系统升级通知");

    private final int id;
    private final String value;
}