package com.qing.erp.search.service.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qing.erp.search.bean.Json;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
public class ElasticSearchWrapper {
    private final RestHighLevelClient client;

    // mappings: properties的数据(用于展示)
    public static final HashMap properties = new HashMap<String, HashMap<String, Object>>() {{
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

    // 自定义RequestOptions.DEFAULT
    public static final RequestOptions COMMON_OPTIONS;

    static {
        val builder = RequestOptions.DEFAULT.toBuilder();
        // 设置鉴权
        builder.addHeader("Authorization", "Bearer" + "{TOKEN}");
        //
        builder.setHttpAsyncResponseConsumerFactory(
                new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024)
        );
        COMMON_OPTIONS = builder.build();
    }


    /**
     * 写入文档(批量)
     */
    @SneakyThrows
    void addBulk() {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList<HashMap<String, Object>> userList = new ArrayList<>();
        userList.add(new HashMap<String, Object>() {{
            put("username", "halfRain");
            put("age", "18");
        }});

        // 批处理请求
        for (int i = 0; i < userList.size(); i++) {
            // 如果是IndexRequest就是批量写入/更新
            // 如果是DeleteRequest那就是批量删除
            bulkRequest
                    .add(new IndexRequest("qs2042").id(String.valueOf(i + 1))
                            .source(JSON.toJSONString(userList.get(i)), XContentType.JSON)
                    );
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures()); // 是否失败,返回 false 代表 成功!
    }



    /**
     * 查询所有
     *
     * @param index 索引库名称
     */
    @SneakyThrows
    public SearchResponse searchAll(String index) {
        // 创建SearchRequest
        SearchRequest request = new SearchRequest(index);

        // DSL
        request.source().query(null);

        // 发送请求
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 全文检索: match
     * 全文检索的match和multi_match查询与match_all的API基本一致.
     * 差别是查询条件, 也就是query的部分.
     */
    @SneakyThrows
    public SearchResponse searchMatch(String index, String keyword, String field) {
        // 创建SearchRequest
        SearchRequest request = new SearchRequest(index);

        // DSL
        request.source().query(QueryBuilders.matchQuery(field, keyword));

        // 发送请求
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 全文检索: match_all
     */
    @SneakyThrows
    public SearchResponse searchMatch(String index, String keyword, String... field) {
        // 创建SearchRequest对象
        val req = new SearchRequest(index);

        req.source().query(QueryBuilders.multiMatchQuery(keyword, field));

        // 发送请求
        return client.search(req, RequestOptions.DEFAULT);
    }

    /**
     * 精确查询: term(词条精确匹配)
     */
    @SneakyThrows
    public SearchResponse searchTerm(String index, String keyword, String field) {
        val req = new SearchRequest(index);

        req.source().query(QueryBuilders.termQuery(field, keyword));

        return client.search(req, RequestOptions.DEFAULT);
    }

    /**
     * 精确查询: range(范围查询)
     */
    @SneakyThrows
    public SearchResponse searchRange(String index, RangeQueryBuilder rangeQuery) {
        val req = new SearchRequest(index);

        // val rangeQuery = QueryBuilders.rangeQuery(field);
        // rangeQuery.lt();
        // rangeQuery.lte();
        // rangeQuery.gt();
        // rangeQuery.gte();

        req.source().query(rangeQuery);

        return client.search(req, RequestOptions.DEFAULT);
    }

    // 地理坐标查询
    public void searchGeoBoundingBox(String index) {
    }

    public void searchGeoDistance(String index) {
    }

    /**
     * 复合查询(FunctionScore)
     */
    public void searchFunctionScore(String index) {
    }

    /**
     * 复合查询: bool(布尔查询)
     * 布尔查询是用must、must_not、filter等方式组合其它查询
     * API与其它查询的差别同样是在查询条件的构建, QueryBuilders, 结果解析等其他代码完全不变.
     */
    @SneakyThrows
    public SearchResponse searchBool(String index, BoolQueryBuilder boolQuery) {
        // 准备Request
        SearchRequest request = new SearchRequest(index);

//        // 准备DSL
//        BoolQueryBuilder testBoolQuery = QueryBuilders.boolQuery();
//        // 添加term
//        testBoolQuery.must(QueryBuilders.termQuery("city", "杭州"));
//        // 添加range
//        testBoolQuery.filter(QueryBuilders.rangeQuery("price").lte(250));
//        // 添加聚合
//        val avgBalance = AggregationBuilders.avg("avg_balance").field("balance");
//        val aggAge = AggregationBuilders.terms("agg_age").field("age").size(10);
//        request.source()
//                .aggregation(aggAge)
//                .aggregation(avgBalance);


        // 添加DSL
        request.source().query(boolQuery);

        // 发送请求
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 搜索结果处理: 排序, 分页
     * 搜索结果的排序和分页是与query同级的参数, 因此同样是使用request.source()来设置.
     */
    @SneakyThrows
    public SearchResponse testPageAndSort() {
        // 页码, 每页大小
        int page = 1, size = 5;

        // 1.准备Request
        SearchRequest request = new SearchRequest("hotel");

        // 2.准备DSL
        // 2.1.query
        request.source().query(QueryBuilders.matchAllQuery());
        // 2.2.排序 sort
        request.source().sort("price", SortOrder.ASC);
        // 2.3.分页 from、size
        request.source().from((page - 1) * size).size(5);

        // 发送请求
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 搜索结果处理: 高亮
     * 高亮查询必须使用全文检索查询, 并且要有搜索关键字, 将来才可以对关键字高亮.
     * 高亮的代码与之前代码差异较大, 有两点:
     * 查询的DSL: 其中除了查询条件, 还需要添加高亮条件, 同样是与query同级.
     * 结果解析: 结果除了要解析_source文档数据, 还要解析高亮结果
     */
    @SneakyThrows
    public SearchResponse testHighlight() {
        // 准备Request
        SearchRequest request = new SearchRequest("hotel");

        // 准备DSL
        request.source().query(QueryBuilders.matchQuery("all", "如家"));
        // 高亮
        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));

        // 发送请求
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 解析响应(json)
     *
     * @param response json
     */
    public void handleResponse(SearchResponse response) {
        _handleResponse(response, false, false);
    }

    /**
     * 解析响应(高亮)
     *
     * @param response ...
     */
    public void handleResponseHighlight(SearchResponse response) {
        _handleResponse(response, false, true);
    }

    /**
     * 解析响应(反序列化)
     *
     * @param response bean
     */
    public void handleResponseDeserialize(SearchResponse response) {
        _handleResponse(response, true, false);
    }

    public void _handleResponse(SearchResponse response, boolean isDeserialize, boolean isHighlight) {
        // elasticsearch返回的结果是一个JSON字符串, 因此需要逐层解析JSON字符串
        /*
        结构:  { hist: total, max_score, hits: _source }
        hits       命中的结果
        total      总条数, 其中的value是具体的总条数值
        max_score  所有结果中得分最高的文档的相关性算分
        hits       搜索结果的文档数组, 其中的每个文档都是一个json对象
        _source    文档中的原始数据, 也是json对象
         */

        // 获取命中的结果(JSON中的最外层的hits)
        SearchHits searchHits = response.getHits();
        System.out.println(searchHits);

        // 获取总条数信息
        long total = searchHits.getTotalHits().value;
        System.out.println("共搜索到" + total + "条数据");

        // 获取SearchHit数组(JSON中hits里面的hits, 也就是文档数组)
        SearchHit[] hits = searchHits.getHits();
        System.out.println(hits);

        // 4.4.遍历
        for (SearchHit hit : hits) {
            // 获取文档结果中的_source, 也就是原始的json文档数据
            String json = hit.getSourceAsString();
            System.out.println(json);

            // 反序列化
            // HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            // System.out.println("hotelDoc = " + hotelDoc);

            // 获取高亮结果
            /*高亮的结果与查询的文档结果默认是分离的, 并不在一起. 因此解析高亮的代码需要额外处理
            第一步: 从结果中获取source.hit.getSourceAsString(), 这部分是非高亮结果, json字符串.还需要反序列为HotelDoc对象
            第二步: 获取高亮结果.hit.getHighlightFields(), 返回值是一个Map, key是高亮字段名称, 值是HighlightField对象, 代表高亮值
            第三步: 从map中根据高亮字段名称, 获取高亮字段值对象HighlightField
            第四步: 从HighlightField中获取Fragments, 并且转为字符串.这部分就是真正的高亮字符串了
            第五步: 用高亮的结果替换HotelDoc中的非高亮结果*/

            /*Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (!CollectionUtils.isEmpty(highlightFields)) {
                // 根据字段名获取高亮结果
                HighlightField highlightField = highlightFields.get("name");
                if (highlightField != null) {
                    // 获取高亮值
                    String name = highlightField.getFragments()[0].string();
                    // 覆盖非高亮结果
                    hotelDoc.setName(name);
                }
            }
            System.out.println("hotelDoc = " + hotelDoc);*/

        }
    }

}
