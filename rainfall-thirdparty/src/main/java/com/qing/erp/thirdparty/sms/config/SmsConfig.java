package com.qing.erp.thirdparty.sms.config;

import com.qing.erp.thirdparty.sms.service.SmsServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
1.搜索短信接口，购买一个5条的短信
https://market.aliyun.com/products/57126001/cmapi024822.html?spm=5176.2020520132.101.2.70a87218D92LLv#sku=yuncode1882200000

2.购买成功后进入云市场，记录AppKey、AppSecret、AppCode
https://market.console.aliyun.com/?spm=5176.12818093.top-nav.dbutton.5adc16d0RrmD95#/?_k=pdf92o

3.参照案例，请求头使用Authorization作为key+值使用AppCode的值来验证身份
 */
//@Configuration
public class SmsConfig {

}
