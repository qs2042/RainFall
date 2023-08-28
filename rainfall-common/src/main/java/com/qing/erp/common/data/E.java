package com.qing.erp.common.data;


import lombok.Data;
import lombok.EqualsAndHashCode;

// 如果继承Exception, 那么Spring的事务管理会失效
@EqualsAndHashCode(callSuper = true)
@Data
public class E extends RuntimeException {
    private String errorMessage;

    public E(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}

