package com.qing.erp.search.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// <7.15 选 low-level, high-level
// >7.15 选 api
@Configuration
public class ElasticSearchConfig {
    //
    private static final String addr = "http://192.168.126.128:9200";
    // 通用设置项
    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    // low-level-client
    @Bean
    public RestClient restClient() {
        return RestClient.builder(
                // 如果是集群, 那就多个HttpHost
                // new HttpHost("192.168.126.128", 9200, "http")
                // HttpHost.create(addr)
                new HttpHost(addr)
        ).build();
    }

    // high-level-client
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        // es封装了一个RestHighLevelClient类, 用于和es进行一切交互
        return new RestHighLevelClient(
                RestClient.builder(HttpHost.create(addr))
        );
    }

    // api-client
    // @Bean
    public ElasticsearchClient elasticsearchClient() {
        // 创建与ES集群进行通信的传输层对象, 使用Jackson库的JsonpMapper对象用于数据的序列化和反序列化
        ElasticsearchTransport transport = new RestClientTransport(
                RestClient.builder(new HttpHost(addr)).build(), new JacksonJsonpMapper()
        );
        // 创建 API client
        return new ElasticsearchClient(transport);
    }
}
