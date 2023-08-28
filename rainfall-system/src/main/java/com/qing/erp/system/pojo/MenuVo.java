package com.qing.erp.system.pojo;

import com.qing.erp.common.str.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo {
    /**
     * ID
     */
    private Integer id;

    /**
     * 自身ID
     */
    private Integer parentId;

    /**
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 组件路径(虚拟字段, 由java自动生成, URL+View.vue)
     */
    private String url;

    /**
     * 是否为重定向
     */
    private Boolean isRedirect;

    /**
     * 介绍
     */
    private String content;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 逻辑删除
     */
    private Boolean flag;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    private String urlView;     // url + view
    private String selfPath;    // url + view.vue
    private String path;        // 上级菜单path+自身path

    private List<MenuVo> children;

    public void urlToSelfPath() {
        if (getUrl() == null || getUrl().isEmpty()) {
            return;
        }
        this.setUrlView(String.format(
                "%sView",
                StrUtil.underscoreToCamel(url)
                )
        );
        this.setSelfPath(String.format(
                "%sView.vue",
                StrUtil.underscoreToCamel(url)
                )
        );
    }
}
