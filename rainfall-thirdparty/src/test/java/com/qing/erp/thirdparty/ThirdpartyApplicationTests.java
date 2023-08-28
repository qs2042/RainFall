/*
package com.qing.erp.thirdparty;


import com.qing.erp.thirdparty.elasticsearch.ElasticSearchWrapper;
import lombok.SneakyThrows;
import lombok.val;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class ThirdpartyApplicationTests {
    @Autowired
    RestHighLevelClient client;

    @SneakyThrows
    @Test
    void query() {
        ElasticSearchWrapper wrapper = new ElasticSearchWrapper(client);

        */
/*
        System.out.println(wrapper.searchAll("qs2042"));
        System.out.println(wrapper.searchMatch("qs2042", "root"));
        System.out.println(wrapper.searchMatch("qs2042", "admin", "username"));
        System.out.println(wrapper.searchMatch("qs2042", "false", "username"));

        System.out.println(wrapper.searchTerm("qs2042", "10", "age"));
        System.out.println(wrapper.searchRange("qs2042",
                // 大于15, 小于20
                QueryBuilders.rangeQuery("age")
                .gt("15").lt("20")
        ));

        wrapper.searchGeoBoundingBox("qs2042");
        wrapper.searchGeoDistance("qs2042");
        wrapper.searchFunctionScore("qs2042");

        wrapper.searchBool("qs2042",
                // 性别男, 城市杭州, 年龄小于等于18
                QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("sex", "男"))
                .mustNot(QueryBuilders.termQuery("city", "杭州"))
                .filter(QueryBuilders.rangeQuery("age").lte(18))
        );
         *//*


        */
/*SearchRequest request = new SearchRequest("qs2042");
        request.source().query(QueryBuilders.matchQuery("username", "root"));
        request.source().highlighter(new HighlightBuilder().field("username").requireFieldMatch(false));
        System.out.println(client.search(request, RequestOptions.DEFAULT));*//*


//        System.out.println(wrapper.getDoc("qs2042", "1"));
//        System.out.println(wrapper.deleteDoc("qs2042", "1"));
//        System.out.println(wrapper.updateDoc("qs2042", "3", "age", "10"));
//        System.out.println(wrapper.updateDoc("qs2042", "3", new HashMap<String, String>(){{
//            put("username", "admin-plus");
//            put("sex", "女");
//        }}));

    }

    @Test
    void test() {
        ElasticSearchWrapper wrapper = new ElasticSearchWrapper(client);

        val r1 = wrapper.addDoc("qs2042", "1", new HashMap<String, Object>(){{
            put("username", "admin");
        }});

        val r2 = wrapper.addDoc("qs2042", "1", "username", "root");

        System.out.println(r1);
        System.out.println(r2);
    }

}
*/
