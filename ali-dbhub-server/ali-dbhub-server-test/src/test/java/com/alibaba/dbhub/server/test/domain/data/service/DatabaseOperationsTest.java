package com.alibaba.dbhub.server.test.domain.data.service;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.api.param.DataSourcePreConnectParam;
import com.alibaba.dbhub.server.domain.api.service.DataSourceService;
import com.alibaba.dbhub.server.domain.api.service.DatabaseService;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.api.param.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.test.common.BaseTest;
import com.alibaba.dbhub.server.test.domain.data.service.dialect.DialectProperties;
import com.alibaba.dbhub.server.test.domain.data.utils.TestUtils;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据库测试
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class DatabaseOperationsTest extends BaseTest {
    @Resource
    private DataSourceService dataSourceService;
    @Autowired
    private List<DialectProperties> dialectPropertiesList;
    @Resource
    private DatabaseService databaseService;

    @Test
    @Order(1)
    public void queryAll() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();

            // 准备上下文
            putConnect(dialectProperties.getUrl(), dialectProperties.getUsername(), dialectProperties.getPassword(),
                dialectProperties.getDbType(), dialectProperties.getDatabaseName(), dataSourceId, null);

            DataSourcePreConnectParam dataSourceCreateParam = new DataSourcePreConnectParam();

            dataSourceCreateParam.setType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUser(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceService.preConnect(dataSourceCreateParam);

            DatabaseQueryAllParam databaseQueryAllParam = new DatabaseQueryAllParam();
            databaseQueryAllParam.setDataSourceId(dataSourceId);
            ListResult<Database> databaseList = databaseService.queryAll(databaseQueryAllParam);
            log.info("查询数据库返回:{}", JSON.toJSONString(databaseList));

            Database Database = databaseList.getData().stream()
                .filter(database -> dialectProperties.getDatabaseName().equals(database.getName()))
                .findFirst()
                .orElse(null);
            Assertions.assertNotNull(Database, "查询数据库失败");

            removeConnect();
        }
    }

}
