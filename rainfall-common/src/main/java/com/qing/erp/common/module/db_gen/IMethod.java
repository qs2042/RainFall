package com.qing.erp.common.module.db_gen;

import com.qing.erp.common.module.db_gen.pojo.GenData;
import com.qing.erp.common.module.db_gen.pojo.GenResult;

public interface IMethod {
    GenResult getEntity(GenData gd);
    GenResult getDao(GenData gd);

    GenResult getService(GenData gd);
    GenResult getServiceImpl(GenData gd);

    GenResult getController(GenData gd);
    GenResult getGateway(GenData gd);

    GenResult getVue(GenData gd);
    GenResult getVueApi(GenData gd);
}
