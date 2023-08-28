package com.qing.erp.search.service.base;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.util.List;

public interface IService {
    // TODO: Mappings信息

    // 创建索引
    CreateIndexResponse createIndex(String index, Object properties);
    // 删除索引
    AcknowledgedResponse deleteIndex(String index);
    // 索引是否存在
    boolean existsIndex(String index);
    // 获取索引
    GetIndexResponse getIndex(String index);
    List<String> getIndexList();

    // TODO: 批量增删改查
    // 创建文档
    IndexResponse createDoc(String index, String id, Object bean);
    IndexResponse createDoc(String index, String id, Object... kv);
    // 删除文档
    DeleteResponse deleteDoc(String index, String id);
    // 文档是否存在
    boolean existsDoc(String index, String id);
    // 获取文档
    GetResponse getDoc(String index, String id);
    List<String> getDocList(String index);
    // 更新文档
    UpdateResponse updateDoc(String index, String id, Object bean);
    UpdateResponse updateDoc(String index, String id, Object... kv);


    // TODO: 查询某个索引的所有数据
    //   全文检索, 精确查询, 地理坐标查询, 复合查询, 布尔查询, 分页, 排序, 高亮
    //   条件查询, 过滤字段, 组合查询, 范围查询, 模糊查询, 聚合操作

}
