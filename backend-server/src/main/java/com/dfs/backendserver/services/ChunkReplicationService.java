package com.dfs.backendserver.services;

import com.dfs.backendserver.utilities.EurekaLBService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkReplicationService {

    private final EurekaLBService lbService;
    private final RestTemplate restTemplate;

    public ChunkReplicationService(EurekaLBService lbService,
                                   RestTemplate restTemplate) {
        this.lbService = lbService;
        this.restTemplate = restTemplate;
    }

    private String uploadToNode(ServiceInstance node, byte[] chunkData) {

        String url = node.getUri() + "/chunks";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setAccept(List.of(MediaType.TEXT_PLAIN));

        HttpEntity<byte[]> request = new HttpEntity<>(chunkData, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        return response.getBody();
    }


    public List<String> replicateChunkToAllNodes(byte[] chunkData) {

        List<ServiceInstance> nodes = lbService.getAllNodes();

        if (nodes.isEmpty()) {
            throw new RuntimeException("No storage nodes available");
        }

        List<String> chunkIds = new ArrayList<>();

        for (ServiceInstance node : nodes) {
            String chunkId = uploadToNode(node, chunkData);
            chunkIds.add(chunkId);

            System.out.println(
                    "Stored chunk on " + node.getUri() +
                            " chunkId=" + chunkId
            );
        }

        return chunkIds;
    }
}
