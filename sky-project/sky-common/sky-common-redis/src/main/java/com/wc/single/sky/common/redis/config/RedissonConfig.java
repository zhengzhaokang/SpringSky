package com.wc.single.sky.common.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class RedissonConfig {

    private static final String Single = "Single";
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.host:127.0.0.1}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private String port;
    @Value("${spring.redis.database:0}")
    private int  database;
    @Value("${spring.redis.sentinel.nodes:Single}")
    private  String nodes;
    @Value("${spring.redis.sentinel.master:Master}")
    private String master;

    @Bean(destroyMethod = "shutdown")
    RedissonClient redissonClient() throws IOException {
        Config config = new Config();
        config.setCodec(new FastjsonCodec());
        if (Single.equals(nodes)) {
            config.useSingleServer()
                    .setPassword(password)
                    .setAddress("redis://" + host + ":" + port)
                    .setDatabase(database)
                    .setConnectTimeout(30000)//同任何节点建立连接时的等待超时。时间单位是毫秒。
                    .setTimeout(3000)
            ;
        }
        else {
            config.useSentinelServers()
                    .setMasterName(master)
                    .setPassword(password)
                    .setScanInterval(200000)//设置集群状态扫描间隔
                    .setMasterConnectionPoolSize(10000)//设置对于master节点的连接池中连接数最大为10000
                    .setSlaveConnectionPoolSize(10000)//设置对于slave节点的连接池中连接数最大为500
                    .setIdleConnectionTimeout(10000)//如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
                    .setConnectTimeout(30000)//同任何节点建立连接时的等待超时。时间单位是毫秒。
                    .setTimeout(3000)//等待节点回复命令的时间。该时间从命令发送成功时开始计时。
                    .setRetryInterval(3000)//当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
                    .setSentinelAddresses(getNodes());
        }
        return Redisson.create(config);
    }

    private List<String> getNodes() {
        Set<String> set = new HashSet<>();
        if (!StringUtils.isEmpty(nodes)) {
            String[] nodeArray = nodes.split(",");
            for (String node : nodeArray) {
                StringBuilder stringBuilder = new StringBuilder("redis://");
                stringBuilder.append(node);
                set.add(stringBuilder.toString());
            }
        }
        return new ArrayList<>(set);
    }

    @Bean
    public RBloomFilter<String> bloomFilter() {
        RBloomFilter<String> bloomFilter = null;
        try {
            bloomFilter = redissonClient().getBloomFilter("bloom-filter");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bloomFilter.tryInit(100000, 0.03);
        return bloomFilter;
    }

}