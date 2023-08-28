package com.qing.erp.common.module.docker;

import com.google.gson.GsonBuilder;

public class BaseJsonEntity {
    public String toJson() {
        return new GsonBuilder().create().toJson(this);
    }
}
