package com.qing.erp.common.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qing.erp.common.ReflectionUtils;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 200);
        put("msg", "success");
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常, 请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    // add
    public R dataAdd(String key, Object value) {
        val data = super.get("data");
        if (data == null) {
            val tmp = new HashMap<String, Object>();
            tmp.put(key, value);
            super.put("data", tmp);
        }
        if (data != null) {
            val map = (HashMap<String, Object>) data;
            map.put(key, value);
            super.put("data", map);
        }
        return this;
    }
    public R addResult(Object value) {
        this.dataAdd("result", value);
        return this;
    }
    public R addList(Object value) {
        this.dataAdd("list", value);
        return this;
    }
    public R addMap(Object value) {
        this.dataAdd("map", value);
        return this;
    }
    public R addTree(Object value) {
        this.dataAdd("tree", value);
        return this;
    }
    public R addPage(Object value) {
        this.dataAdd("page", value);
        return this;
    }
    public R addBean(Object value) {
        Map<String, Object> map = ReflectionUtils.beanFieldsToHashMap(value);
        map.forEach(this::dataAdd);
        return this;
    }
    public R addBean(String key, Object value) {
        dataAdd(key, value);
        return this;
    }
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    // get
    public Integer getCode() {
        return (Integer.valueOf((String) get("code")));
    }

    public String getMsg() {
        return String.valueOf(get("msg"));
    }

    public Object getData() {
        return get("data");
    }
    public HashMap<String, Object> getDataAsMap() {
        return (HashMap<String, Object>) get("data");
    }
    @SneakyThrows
    public <T> T getDataAs(Class<T> t) {
        Object data = get("data");
        val json = new Gson().toJson(data);
        return new GsonBuilder().create().fromJson(json, t);
    }
}
