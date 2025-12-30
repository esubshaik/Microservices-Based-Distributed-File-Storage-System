package com.dfs.backendserver.services;

import com.dfs.backendserver.utilities.EurekaLBService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChunkDownloadService {

    private final RestTemplate restTemplate;
    private final EurekaLBService lbService;

    public ChunkDownloadService(RestTemplate restTemplate,
                                EurekaLBService lbService) {
        this.restTemplate = restTemplate;
        this.lbService = lbService;
    }

    public byte[] downloadChunk(String chunkId) {

        List<ServiceInstance> nodes = lbService.getAllNodes();

        for (ServiceInstance node : nodes) {
            try {
                String url = node.getUri() + "/chunks/" + chunkId;

                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

                HttpEntity<Void> request = new HttpEntity<>(headers);

                ResponseEntity<byte[]> response =
                        restTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                request,
                                byte[].class
                        );

                return response.getBody();

            } catch (Exception e) {
                System.out.println(
                        "Replica failed for chunkId=" + chunkId +
                                " on " + node.getUri()
                );
            }
        }

        throw new RuntimeException(
                "Failed to download chunk, all replicas unavailable"
        );
    }
}
