package ru.bookstore.config;


import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastCacheConfig {

    @Bean
    public Config hazelcastConfig(){
        Config hh = new Config().setInstanceName("hazelcast-instance11")
                .addMapConfig(new MapConfig().setName("genreCache")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(3600))
                .addMapConfig(new MapConfig().setName("publishingHouseCache")
                        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(3600));
        hh.getNetworkConfig().setPort(5555);
        return hh;
    }

//    ClientConfig config = new ClientConfig();
//    GroupConfig groupConfig = config.getGroupConfig();
//groupConfig.setName("dev");
//groupConfig.setPassword("dev-pass");
//    HazelcastInstance hzClient
//            = HazelcastClient.newHazelcastClient(config);



//    public ClientConfig clientConfig() {
//        ClientConfig clientConfig = new ClientConfig();
//        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();
//        networkConfig.addAddress("172.17.0.4:5701", "172.17.0.6:5701")
//                .setSmartRouting(true)
//                .addOutboundPortDefinition("34700-34710")
//                .setRedoOperation(true)
//                .setConnectionTimeout(5000)
//                .setConnectionAttemptLimit(5);
//
//        return clientConfig;
//
//    }
//
//    @Bean
//    public HazelcastInstance hazelcastInstance(ClientConfig clientConfig) {
//        return HazelcastClient.newHazelcastClient(clientConfig);
//    }
}
