package com.qing.erp.common.module.docker.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandExecutionEntity {
    private String command;
    private String result;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public CommandExecutionEntity(String command) {
        this.command = command;
        this.startTime = LocalDateTime.now();
    }

    public void setResult(String result) {
        this.result = result;
        this.endTime = LocalDateTime.now();
    }

    @JsonProperty("executionTime")
    public String getExecutionTime() {
        Duration duration = Duration.between(startTime, endTime);
        long seconds = duration.getSeconds();
        long millis = duration.toMillis() % 1000;
        return String.format("%d.%03d seconds", seconds, millis);
    }
}
