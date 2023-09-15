package com.zerobase.elasticsearch;

import com.zerobase.domain.repository.MovieSearchRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackageClasses = MovieSearchRepository.class)
@Configuration
@ComponentScan(basePackageClasses = MovieSearchRepository.class)
public class ElasticSearchConfiguration extends ElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .withBasicAuth("elastic", "b7WVXI63CbDUoCagcUcj")
                .build();
    }
}
