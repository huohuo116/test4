package com.alibaba.dbhub.server.web.api.controller.environment.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

/**
 * 环境修改请求
 *
 * @author 是仪
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentUpdateRequest {
    /**
     * 主键
     */
    private Long id;

    /**
     * 环境名称
     */
    @Length(max = 120, message = "环境名称超过最大限制")
    private String name;

    /**
     * 环境缩写
     */
    @Length(max = 4, message = "环境缩写超过最大限制")
    private String shortName;

    /**
     * 样式类型
     */
    @Length(max = 32, message = "样式类型超过最大限制")
    private String style;
}
