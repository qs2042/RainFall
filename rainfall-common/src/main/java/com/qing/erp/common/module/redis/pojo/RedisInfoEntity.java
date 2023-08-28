package com.qing.erp.common.module.redis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RedisInfoEntity {
    private String type = null;
    private List<HashMap<String, String>> value = new ArrayList<>();
    private String raw = "";

    public void addValue(String k, String v) {
        value.add(new HashMap<String, String>(){{
            put(k,v);
        }});
    }

    public void addRaw(String s) {
        raw += raw.equals("") ? s : "\n" + s;
    }

}
