package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.FeedbackEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Sun Jul 16 19:02:28 CST 2023
*/
public interface IFeedbackService {
    // 增
    R add(FeedbackEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(FeedbackEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
