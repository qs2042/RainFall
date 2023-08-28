package com.qing.erp.thirdparty.sms.service;

public interface SmsService {
    Boolean sendCode(String phone, String code);
}
