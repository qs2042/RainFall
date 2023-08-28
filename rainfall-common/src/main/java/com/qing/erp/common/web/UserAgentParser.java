package com.qing.erp.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgentParser {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserAgent {
        private String os;
        private String osVersion;
        private String browser;
        private String browserVersion;
    }

    private static final Pattern UA_PATTERN = Pattern.compile(
            "(?i)(android|iphone|ipod|ipad|windows phone|windows nt|mac os x|linux)[/\\s]?([\\d._]+)?[;)]");

    private static final Pattern BROWSER_PATTERN = Pattern.compile(
            "(?i)(msie|trident|firefox|chrome|safari)[\\s/]?([\\d._]+)?");

    public static UserAgent parse(String userAgentString) {
        UserAgent userAgent = new UserAgent();
        if (userAgentString == null) {
            return userAgent;
        }
        Matcher uaMatcher = UA_PATTERN.matcher(userAgentString);
        if (uaMatcher.find()) {
            String os = uaMatcher.group(1).toLowerCase();
            String osVersion = uaMatcher.group(2);
            userAgent.setOs(os);
            userAgent.setOsVersion(osVersion);
        }
        Matcher browserMatcher = BROWSER_PATTERN.matcher(userAgentString);
        if (browserMatcher.find()) {
            String browser = browserMatcher.group(1).toLowerCase();
            String browserVersion = browserMatcher.group(2);
            userAgent.setBrowser(browser);
            userAgent.setBrowserVersion(browserVersion);
        }
        return userAgent;
    }

    public static void main(String[] args) {
        String ua = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 Edg/109.0.1518.115";
    }
}
