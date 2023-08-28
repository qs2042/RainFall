package com.qing.erp.member.pojo;

import com.qing.erp.member.valid.custom.ListValue;
import com.qing.erp.member.valid.group.AddGroup;
import com.qing.erp.member.valid.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

// @NotNull()  用于基本类型
// @NotEmpty() 用于集合, size不能等于0
// @NotBlank() 用于String, 去除两端空白字符后的长度要大于0

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // 添加时必须为null, 修改时必须不为null
    @Null(groups = {AddGroup.class})
    @NotNull(groups = {UpdateGroup.class})
    private Integer id;

    //
    @NotNull
    @NotBlank(message = "username不能为null")
    private String username;

    @NotNull
    @NotBlank
    @Size(max = 10, min = 5, message = "字段长度要在5-10之间")
    private String password;

    @NotNull
    @NotBlank
    @Max(value = 10, message = "最大长度为10")
    @Min(value = 5, message = "最小长度为5")
    private String introduce;

    @NotNull
    @NotBlank
    @Email(message = "邮箱格式不正确")
    private String email;

    @ListValue(values = {0,1})
    private Integer sex;

}
