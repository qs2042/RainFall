package com.qing.erp.system.ws.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qing.erp.common.str.FormatUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Integer id;
    private String account;

    @JsonIgnore
    private WebSocketSession session;

    private String token;
    private Date createdAt;

    public static UserEntity build(Integer id, String account, WebSocketSession session, String token) {
        return new UserEntity(id, account, session, token, new Date());
    }

    @JsonProperty("onlineDuration")
    private String onlineDuration() {
        val now = new Date();

        long currentTime = now.getTime();
        long createdAtTime = createdAt.getTime();

        return FormatUtil.convertSeconds((currentTime - createdAtTime) / 1000);
    }
}
