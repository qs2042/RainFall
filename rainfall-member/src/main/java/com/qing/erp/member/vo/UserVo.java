package com.qing.erp.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Integer id;
    private String username;
    private String password;
    private String introduce;
    private String email;
    private Integer sex;

}
