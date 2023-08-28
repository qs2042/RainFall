package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.FriendLinkEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Wed Jul 12 18:22:26 CST 2023
*/
public interface IFriendLinkService {
    // 增
    R add(FriendLinkEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(FriendLinkEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
