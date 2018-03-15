package com.colm.cachetest.cachingrest;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.spring.cache.CacheBuilder;
import com.couchbase.client.spring.cache.CouchbaseCacheManager;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.tensorflow.Graph;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
@EnableCaching
public class CachingrestApplication {

    private static final Logger log = LoggerFactory.getLogger(CachingrestApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(CachingrestApplication.class, args);
    }

    @Bean
    public Graph tfModelGraph(@Value("${tf.frozenModelPath}") String tfFrozenModelPath) throws IOException {
        Resource graphResource = getResource(tfFrozenModelPath);

        Graph graph = new Graph();
        graph.importGraphDef(IOUtils.toByteArray(graphResource.getInputStream()));
        log.info("Loaded Tensorflow model");
        return graph;
    }

    private Resource getResource(@Value("${tf.frozenModelPath}") String tfFrozenModelPath) {
        Resource graphResource = new FileSystemResource(tfFrozenModelPath);
        if (!graphResource.exists()) {
            graphResource = new ClassPathResource(tfFrozenModelPath);
        }
        if (!graphResource.exists()) {
            throw new IllegalArgumentException(String.format("File %s does not exist", tfFrozenModelPath));
        }
        return graphResource;
    }

    @Bean
    public List<String> tfModelLabels(@Value("${tf.labelsPath}") String labelsPath) throws IOException {
        Resource labelsRes = getResource(labelsPath);
        log.info("Loaded model labels");
        return IOUtils.readLines(labelsRes.getInputStream(), Charset.forName("UTF-8")).stream()
                .map(label -> label.substring(label.contains(":") ? label.indexOf(":") + 1 : 0)).collect(Collectors.toList());
    }


    // Code for getting working with Couchbase
//    @Bean(destroyMethod = "disconnect")
//    public Cluster cluster() {
//        // connect to the couchbase-server running on your local machine
//        return CouchbaseCluster.create();
//    }
//
//    @Bean(destroyMethod = "close")
//    public Bucket bucket() {
//        cluster().authenticate("colm", "password");
//        return cluster().openBucket("colmcachetest");
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        CacheBuilder cacheBuilder = CacheBuilder.newInstance(bucket()).withExpiration(0);
//        return new CouchbaseCacheManager(cacheBuilder, "imageClassifications");
//    }
}
