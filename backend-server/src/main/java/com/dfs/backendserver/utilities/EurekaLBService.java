package com.dfs.backendserver.utilities;

import com.dfs.backendserver.model.EurekaLBModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EurekaLBService {

    private final EurekaLBModel elbs;

    @Autowired
    public EurekaLBService(EurekaLBModel elbs) {
        this.elbs = elbs;
    }

    public List<ServiceInstance> getAllNodes() {
        return elbs.getAllServers("NODE-SERVER");
    }
}
