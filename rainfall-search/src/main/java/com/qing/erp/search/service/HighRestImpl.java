package com.qing.erp.search.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qing.erp.search.bean.Json;
import com.qing.erp.search.service.base.IService;
import lombok.SneakyThrows;
import lombok.val;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HighRestImpl implements IService {
    @Autowired
    RestHighLevelClient rhlc;

    public static final Object properties = new HashMap<String, HashMap<String, Object>>() {{
        put("id", new Json() {{
            put("type", "keyword");
        }});
        put("username", new Json() {{
            put("type", "text");
            put("analyzer", "ik_max_word");
            put("copy_to", "all");
        }});
        put("address", new Json() {{
            put("type", "keyword");
            put("index", "false");
        }});
        put("price", new Json() {{
            put("type", "integer");
        }});
        put("score", new Json() {{
            put("type", "integer");
        }});
        put("brand", new Json() {{
            put("type", "keyword");
            put("copy_to", "all");
        }});
        put("city", new Json() {{
            put("type", "keyword");
            put("copy_to", "all");
        }});
        put("starName", new Json() {{
            put("type", "keyword");
        }});
        put("business", new Json() {{
            put("type", "keyword");
        }});
        put("location", new Json() {{
            put("type", "geo_point");
        }});
        put("pic", new Json() {{
            put("type", "keyword");
            put("index", "false");
        }});
        put("all", new Json() {{
            put("type", "text");
            put("analyzer", "ik_max_word");
        }});
    }};

    /**
     * 创建索引库
     * @param index 索引名称
     * @param properties 字段类型
     */
    @SneakyThrows
    @Override
    public CreateIndexResponse createIndex(String index, Object properties) {
        if (existsIndex(index)) {
            return null;
        }

        // 1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest(index);

        // 2.请求参数, DSL语句
        val json = new HashMap<String, Object>(){{
            put("mappings", new HashMap<String, Object>(){{
                put("properties", properties);
            }});
        }};

        val mappings = JSONObject.toJSONString(json);
        request.source(mappings, XContentType.JSON);

        // 3.发起请求
        val r = rhlc.indices().create(request, RequestOptions.DEFAULT);
        // 索引创建操作是否被确认
//        System.out.println(r.isAcknowledged());
        // 索引创建操作是否是部分创建
//        System.out.println(r.isFragment());
        // 索引创建操作中的分片是否被确认
//        System.out.println(r.isShardsAcknowledged());
        // 创建成功的索引的名称
//        System.out.println(r.index());
        return r;
    }

    /**
     * 删除索引库
     * @param index 索引库名称
     *
     * DELETE /{索引}
     */
    @SneakyThrows
    @Override
    public AcknowledgedResponse deleteIndex(String index) {
        // 1.创建Request对象
        DeleteIndexRequest request = new DeleteIndexRequest(index);

        // 2.发起请求
        return rhlc.indices().delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 判断索引库是否存在
     * @param index 索引名称
     */
    @SneakyThrows
    @Override
    public boolean existsIndex(String index) {
        // 1.创建Request对象
        GetIndexRequest request = new GetIndexRequest(index);

        // 2.发起请求
        return rhlc.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 获取索引
     * @param index 索引名称
     */
    @SneakyThrows
    @Override
    public GetIndexResponse getIndex(String index) {
        if (!existsIndex(index)) {
            return null;
        }

        // 创建Request对象
        GetIndexRequest request = new GetIndexRequest(index);

        // 发送请求
        // System.out.println("Aliases : " + indexResponse.getAliases());
        // System.out.println("DefaultSettings : " + indexResponse.getDefaultSettings());
        // System.out.println("Indices : " + Arrays.toString(indexResponse.getIndices()));
        // System.out.println("Mappings : " + indexResponse.getMappings());
        // System.out.println("Settings : " + indexResponse.getSettings());
        return rhlc.indices().get(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Override
    public List<String> getIndexList() {
        GetIndexRequest request = new GetIndexRequest("*"); // 获取所有索引

        GetIndexResponse response = rhlc.indices().get(request, RequestOptions.DEFAULT);

        String[] indices = response.getIndices();
        return Arrays.stream(indices).collect(Collectors.toList());
    }

    /**
     * 写入/更新文档
     *  @param index 索引名称
     * @param id    key
     * @param bean  bean
     */
    @SneakyThrows
    @Override
    public IndexResponse createDoc(String index, String id, Object bean) {
        IndexRequest request = new IndexRequest(index);
        request.id(id).source(JSON.toJSONString(bean), XContentType.JSON);
        // RequestOptions.DEFAULT 可以替换为自己的 COMMON_OPTIONS
        return rhlc.index(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Override
    public IndexResponse createDoc(String index, String id, Object... kv) {
        IndexRequest request = new IndexRequest(index);
        request.id(id).source(kv);
        return rhlc.index(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Override
    public DeleteResponse deleteDoc(String index, String id) {
        val request = new DeleteRequest(index);
        request.id(id);
        return rhlc.delete(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Override
    public boolean existsDoc(String index, String id) {
        val request = new GetRequest(index, id);
        return rhlc.exists(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Override
    public GetResponse getDoc(String index, String id) {
        val request = new GetRequest(index);
        request.id(id);
        return rhlc.get(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Override
    public List<String> getDocList(String index) {
        if (!existsIndex(index)) {
            return null;
        }

        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder search = new SearchSourceBuilder();

        // 查询所有
        search.query(QueryBuilders.matchAllQuery());

        // TODO: 分页之后在写
        //  search.from((page - 1) * show).size(show);

        request.source(search);
        SearchResponse response = rhlc.search(request, RequestOptions.DEFAULT);
        return Arrays.stream(response.getHits().getHits()).map(SearchHit::getId)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public UpdateResponse updateDoc(String index, String id, Object bean) {
        val request = new UpdateRequest(index, id);
        request.doc(JSON.toJSONString(bean), XContentType.JSON);
        return rhlc.update(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @Override
    public UpdateResponse updateDoc(String index, String id, Object... kv) {
        val request = new UpdateRequest(index, id);
        request.doc(kv);
        return rhlc.update(request, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    public void method() {

        // 创建SearchRequest
        SearchRequest request = new SearchRequest("test");

        // [查询所有]
        request.source().query(null);

        // [全文检索] match
        val match = QueryBuilders.matchQuery("username", "halfRain");
        request.source().query(match);

        // [全文检索] match_all
        val matchAll = QueryBuilders.multiMatchQuery("username", "halfRain");
        request.source().query(matchAll);

        // [精确查询] term
        val term = QueryBuilders.termQuery("age", 100);
        request.source().query(term);

        // [范围查询] range
        val rangeQuery = QueryBuilders.rangeQuery("age");
        rangeQuery.lt(100);
        rangeQuery.lte(100);
        rangeQuery.gt(5);
        rangeQuery.gte(5);
        request.source().query(rangeQuery);

        // [布尔查询]
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("city", "杭州"));
        boolQuery.filter(QueryBuilders.rangeQuery("price").lte(250));
        request.source().query(boolQuery);

        // [字符串查询]
        val queryStringQuery=  QueryBuilders.queryStringQuery("");
        request.source().query(queryStringQuery);

        // [地理坐标查询] BoundingBox,Distance
        request.source().query(new GeoBoundingBoxQueryBuilder(""));

        // [复合查询] bool查询 + aggregation聚合
        BoolQueryBuilder boolQuery2 = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("city", "杭州"))
                .filter(QueryBuilders.rangeQuery("price").lte(250));
        val avgBalance = AggregationBuilders.avg("avg_balance").field("balance");
        val aggAge = AggregationBuilders.terms("agg_age").field("age").size(10);
        request.source().query(boolQuery2);
        request.source().aggregation(aggAge).aggregation(avgBalance);

        // [高亮]
        request.source().query(QueryBuilders.matchQuery("all", "如家"));
        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));

        // 排序+分页
        request.source().query(QueryBuilders.matchAllQuery());
        request.source().sort("price", SortOrder.ASC);
        int page = 1, size = 5;
        request.source().from((page - 1) * size).size(5);

        // 查询 + 排序 + 搜索的超时时间
        request.source().query(null);
        request.source().sort("xxx");
        request.source().timeout(new TimeValue(1000 * 5));
        // 设置返回结果的最大数量 + 偏移量
        request.source().size(10);
        request.source().from(0);
        // 设置是否要计算匹配的总命中数
        request.source().trackTotalHits(false);
        // 设置是否解释每个匹配的评分
        request.source().explain(false);
        // 设置是否要返回文档的源数据(true)
        request.source().fetchSource(true);
        // 设置高亮显示的规则和参数
        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));
        // 设置是否启用搜索请求分析
        request.source().profile(false);
        // 添加一个或多个重新评分查询
        request.source().rescores().add(null);
        // 设置一个后置过滤器, 用于在查询结果返回之前进行过滤
        request.source().postFilter(null);
        // 设置结果的最低评分阈值
        request.source().minScore(0.5f);
        // 设置在返回结果之前要执行的最大滚动搜索次数
        request.source().terminateAfter(114);
        // 设置建议（自动完成）的参数和规则
        request.source().suggest(null);
        // 加一个或多个聚合操作
        request.source().aggregation((AggregationBuilder) null);
        // 设置折叠（合并）搜索结果的参数和规则
        request.source().collapse(null);



        // ExistsQueryBuilder：用于检查文档中是否存在指定字段。
        // PrefixQueryBuilder：用于执行前缀匹配查询。
        // WildcardQueryBuilder：用于执行通配符匹配查询。
        // FuzzyQueryBuilder：用于执行模糊匹配查询。
        // IdsQueryBuilder：用于根据文档ID执行查询。
        // GeoDistanceQueryBuilder：用于执行基于地理位置距离的查询。
        // NestedQueryBuilder：用于执行嵌套查询, 可以在嵌套的对象内执行查询。
        // SpanTermQueryBuilder：用于执行在指定字段中的精确匹配查询。
        // SpanNearQueryBuilder：用于执行在指定字段中的邻近词查询。


        // 发送请求
        val r = rhlc.search(request, RequestOptions.DEFAULT);
    }

}
