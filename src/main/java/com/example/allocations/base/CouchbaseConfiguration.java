package com.example.allocations.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import com.couchbase.client.java.codec.JacksonJsonSerializer;
import com.couchbase.client.java.env.ClusterEnvironment;
import org.springframework.context.annotation.Bean;

@Configuration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    private final MyCouchbaseProperties couchbaseProperties;


    public CouchbaseConfiguration( MyCouchbaseProperties couchbaseProperties) {
        this.couchbaseProperties = couchbaseProperties;
    }

    @Override
    public String getConnectionString() {
        return couchbaseProperties.getConnectionString();
    }

    @Bean(
            destroyMethod = "shutdown"
    )
    @Override

    public ClusterEnvironment couchbaseClusterEnvironment() {
        ClusterEnvironment.Builder builder = ClusterEnvironment.builder();
        builder.jsonSerializer(JacksonJsonSerializer.create(this.getObjectMapper()));
        this.configureEnvironment(builder);
        return builder.build();

    }

    @Override
    public String getUserName() {
        return couchbaseProperties.getUsername();
    }

    @Override
    public String getPassword() {
        return couchbaseProperties.getPassword();
    }

    @Override
    public String getBucketName() {
        return couchbaseProperties.getBucketName();
    }



}