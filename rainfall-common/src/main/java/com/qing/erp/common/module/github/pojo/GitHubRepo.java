package com.qing.erp.common.module.github.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitHubRepo {
    private long id;                // 仓库ID
    private String name;            // 仓库名称
    private String description;     // 仓库介绍
    private String language;        // 使用语言
    private int stargazers_count;   // star数量
    private int forks_count;        // fork数量
    private int subscribers_count;  // 订阅数量
}
