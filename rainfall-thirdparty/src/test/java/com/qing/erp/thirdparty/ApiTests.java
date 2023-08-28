/*
package com.qing.erp.thirdparty;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.Data;
import lombok.val;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@Data
class Product {
    private String name;
}

@SpringBootTest
public class ApiTests {
    @Autowired
    RestClient restClient;

//    @Autowired
    ElasticsearchClient elasticsearchClient;

    @Test
    public void method() throws IOException {
        System.out.println(restClient);
        System.out.println(elasticsearchClient);

        val test = elasticsearchClient.search(s -> s
                .index("customer"), Product.class);
        System.out.println(test.hits().hits());

        SearchResponse<Product> search = elasticsearchClient.search(s -> s
                        .index("customer")
                        .query(q -> q
                                .term(t -> t
                                        .field("name")
                                        .value(v -> v.stringValue("bicycle"))
                                )),
                Product.class);

        for (Hit<Product> hit : search.hits().hits()) {
            System.out.println(hit.source());
        }
    }

}
*/
