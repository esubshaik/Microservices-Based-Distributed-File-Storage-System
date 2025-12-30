package com.dfs.backendserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NODE-SERVER")
public interface NodeFeignClient {


    @PostMapping(
            value = "/chunks",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    String uploadChunk(@RequestBody byte[] data);


    @GetMapping(
            value = "/{chunkId}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    byte[] downloadChunk(@PathVariable String chunkId);
}
