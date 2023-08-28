package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.NoticeEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Jul 06 20:51:58 CST 2023
*/
public interface INoticeService {
    // 增
    R add(NoticeEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(NoticeEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
