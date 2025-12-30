package com.dfs.backendserver.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EurekaLBModel {
    @Autowired
    private DiscoveryClient discoveryClient;

    public List<ServiceInstance> getAllServers(String serviceName) {
        return discoveryClient.getInstances(serviceName);
    }
}
