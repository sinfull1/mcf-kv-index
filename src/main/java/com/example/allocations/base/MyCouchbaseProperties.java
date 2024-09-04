package com.example.allocations.base;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MyCouchbaseProperties {

    final String connectionString;
    final String username;
    final String password;
    final String bucketName;


    public MyCouchbaseProperties(
            @Value("${spring.couchbase.connection.string}") String connectionString,
            @Value("${spring.couchbase.username}") String username,
            @Value("${spring.couchbase.password}") String password,
            @Value("${spring.couchbase.bucketName}") String bucketName) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
        this.bucketName = bucketName;
    }

    // getters
}