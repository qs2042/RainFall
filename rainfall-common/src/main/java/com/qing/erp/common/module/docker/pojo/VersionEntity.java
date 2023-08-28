package com.qing.erp.common.module.docker.pojo;

import com.qing.erp.common.module.docker.BaseJsonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionEntity extends BaseJsonEntity {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Platform {
        private String Name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Component {
        private String Name;
        private String Version;
        private Details Details;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {
        private String ApiVersion;
        private String Arch;
        private String BuildTime;
        private String Experimental;
        private String GitCommit;
        private String GoVersion;
        private String KernelVersion;
        private String MinAPIVersion;
        private String Os;
    }

    private Platform Platform;
    private String Version;
    private String ApiVersion;
    private String MinAPIVersion;
    private String GitCommit;
    private String GoVersion;
    private String Os;
    private String Arch;
    private String KernelVersion;
    private String BuildTime;
    private List<Component> Components;
}
