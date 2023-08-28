package com.qing.erp.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Tue Jul 11 19:53:08 CST 2023
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class TaskEntity implements Runnable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 调用目标
     */
    @Column(name = "invoke_target")
    private String invokeTarget;

    /**
     * 描述
     */
    @Column(name = "cron_describe")
    private String cronDescribe;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    @Override
    public void run() {
        try {

            Class<?> clazz = Class.forName(invokeTarget.substring(0, invokeTarget.lastIndexOf(".")));
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod(invokeTarget.substring(invokeTarget.lastIndexOf(".") + 1));
            method.invoke(obj);
        } catch (Exception e) {
            // handle exception
            log.info("[qing] 调用失败");
            e.printStackTrace();
        }
    }
}
