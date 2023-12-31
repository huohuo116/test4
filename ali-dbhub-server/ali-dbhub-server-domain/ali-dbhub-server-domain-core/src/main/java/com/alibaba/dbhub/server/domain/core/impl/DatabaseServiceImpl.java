package com.alibaba.dbhub.server.domain.core.impl;

import java.util.List;

import com.alibaba.dbhub.server.domain.api.param.DatabaseOperationParam;
import com.alibaba.dbhub.server.domain.api.param.SchemaOperationParam;
import com.alibaba.dbhub.server.domain.api.service.DatabaseService;
import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.support.model.Schema;
import com.alibaba.dbhub.server.domain.api.param.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.domain.api.param.SchemaQueryParam;
import com.alibaba.dbhub.server.domain.support.sql.DbhubContext;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Override
    public ListResult<Database> queryAll(DatabaseQueryAllParam param) {
        List<String> databases = DbhubContext.getMetaSchema().databases();
        return ListResult.of(EasyCollectionUtils.toList(databases, name -> Database.builder().name(name).build()));
    }

    @Override
    public ListResult<Schema> querySchema(SchemaQueryParam param) {
        List<String> databases = DbhubContext.getMetaSchema().schemas(param.getDataBaseName());
        return ListResult.of(EasyCollectionUtils.toList(databases, name -> Schema.builder().name(name).build()));
    }

    @Override
    public ActionResult deleteDatabase(DatabaseOperationParam param) {
        DbhubContext.getMetaSchema().dropDatabase(param.getDatabaseName());
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult createDatabase(DatabaseOperationParam param) {
        DbhubContext.getMetaSchema().createDatabase(param.getDatabaseName());
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult modifyDatabase(DatabaseOperationParam param) {
        DbhubContext.getMetaSchema().modifyDatabase(param.getDatabaseName(),param.getNewDatabaseName());
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult deleteSchema(SchemaOperationParam param) {
        DbhubContext.getMetaSchema().dropSchema(param.getDatabaseName(),param.getSchemaName());
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult createSchema(SchemaOperationParam param) {
        DbhubContext.getMetaSchema().createSchema(param.getDatabaseName(),param.getSchemaName());
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult modifySchema(SchemaOperationParam param) {
        DbhubContext.getMetaSchema().modifySchema(param.getDatabaseName(),param.getSchemaName(),
            param.getNewSchemaName());
        return ActionResult.isSuccess();
    }

}
