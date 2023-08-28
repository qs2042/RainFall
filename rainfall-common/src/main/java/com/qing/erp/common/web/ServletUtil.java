package com.qing.erp.common.web;

import com.qing.erp.common.other.JavaUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ServletUtil {
    public static HttpServletRequest toReq(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    public static HttpServletResponse toResp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    // show
    public static void show(HttpServletRequest req) {
        log.info("[method] " + req.getMethod());
        log.info("[ParameterNames] " + JavaUtil.enumerationToList(req.getParameterNames()));
        log.info("[URI] " + req.getRequestURI());
        log.info("[URL] " + req.getRequestURL());
        //log.info("[PathInfo] " + req.getPathInfo());
        //log.info("[ServletPath] " + req.getServletPath());
        //log.info("[ContextPath] " + req.getContextPath());
        //log.info("[ServletContext] " + req.getServletContext().getRealPath("/"));
    }

    // url, preUrl
    public static String getUrl(HttpServletRequest req, String[]... args) {
        LinkedList<String> list = new LinkedList<>();
        Set<Map.Entry<String, String[]>> entries = req.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String k = entry.getKey();
            if (k.equals("page")) {
                continue;
            }

            String v = entry.getValue()[entry.getValue().length - 1];
            list.add(String.format("%s=%s", k, v));
        }

        return req.getRequestURL() + "?" + String.join("&", list);
    }

    public static String getPreUrl(HttpServletRequest req) {
        return req.getHeader("Referer");
    }

    //
    public static String parameterToString(HttpServletRequest req) {
        val list = new ArrayList<String>();
        //req.getParameterMap().forEach((key, value) -> list.add(String.format("%s=%s", key, Arrays.toString(value))));
        req.getParameterMap().forEach((key, value) -> list.add(String.format("%s=%s", key, value[value.length - 1])));
        return String.join("&", list);
    }

    public static String parameterToJson(HttpServletRequest req) {
        val list = new ArrayList<String>();
        req.getParameterMap().forEach((key, value) -> list.add(String.format("%s: %s", key, Arrays.toString(value))));
        return "{" + String.join(",", list) + "}";
    }

    // 获取IP, MAC
    public static String getIp(HttpServletRequest request) {
        String ip = null;

        // X-Forwarded-For: Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        // Proxy-Client-IP: apache 服务代理
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        // WL-Proxy-Client-IP: weblogic 服务代理
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("WL-proxy-Client-IP");
        }

        // HTTP_CLIENT_IP: 有些代理服务器
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        // X-Real-IP: nginx服务代理
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("X-Real-IP");
        }

        // 防止有些网络通过多层代理, 此时获取到的IP就会出现多个, 一般都是以","区分开来, 第一个IP为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        // 如果通过上面的代码都不能获取到, 那就通过request.getRemoteAddr()获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    @SneakyThrows
    public static String getMac(String ip) {
        String mac = null;

        // 执行命令: nbtstat -a [ip]
        Process process = Runtime.getRuntime().exec("nbtstat -a" + ip);

        // 读取结果流
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // 正则表达式
        Pattern pattern = Pattern.compile("([A-F0-9]{2}-){5}[A-F0-9]{2}");
        Matcher matcher;

        //
        for (String strLine = br.readLine(); strLine != null; strLine = br.readLine()) {
            matcher = pattern.matcher(strLine);
            if (matcher.find()) {
                mac = matcher.group();
                break;
            }
        }

        return mac;
    }

    // 获取body(只能获取一次)
    public static String getBody(HttpServletRequest request) throws Exception {
        try (InputStream is = request.getInputStream()) {
            if (is == null) {
                throw new Exception("获取失败: 重复调用request.getInputStream()");
            }
            return IOUtils.toString(is, StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            throw new Exception("读取失败: " + e.getMessage());
        }
    }

    @SneakyThrows
    public static void setEncoding(ServletRequest req, ServletResponse resp, String encoding) {
        if (null == encoding) {
            encoding = "utf-8";
        }
        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);
        resp.setContentType(String.format("text/html; charset=%s", encoding));
    }

    public static Object getAttribute(HttpServletRequest req, String k) {
        Object res = req.getAttribute(k);

        if (null == res) {
            res = req.getSession().getAttribute(k);
        }
        if (null == res) {
            res = req.getServletContext().getAttribute(k);
        }
        return res;
    }

    @SneakyThrows
    public static void write(HttpServletResponse resp, String html) {
        resp.getWriter().write(html);
    }

    @SneakyThrows
    public static void println(HttpServletResponse response, String html) {
        response.getWriter().println(html);
    }

    @SneakyThrows
    public static void forward(HttpServletRequest req, HttpServletResponse resp, String path) {
        req.getRequestDispatcher(path).forward(req, resp);
    }

    @SneakyThrows
    public static void redirect(HttpServletResponse resp, String url) {
        resp.sendRedirect(url);
    }

    public static HashMap<String, String> getHeadersByMap(HttpServletRequest req) {
        val map = new HashMap<String, String>();
        val names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            val name = names.nextElement();
            map.put(name, req.getHeader(name));
        }
        return map;
    }

    public static HashMap<String, String> getHeadersByMap(HttpServletResponse resp) {
        val map = new HashMap<String, String>();
        resp.getHeaderNames().forEach(v -> map.put(v, resp.getHeader(v)));
        return map;
    }
}
