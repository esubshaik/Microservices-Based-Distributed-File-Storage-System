package com.dfs.backendserver.services;

import com.dfs.backendserver.utilities.ChunkSplitter;
import com.dfs.backendserver.utilities.FileMetadataStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {

    private final ChunkSplitter splitter;
    private final ChunkReplicationService replicationService;
    private final FileMetadataStore metadataStore;


    public FileUploadService(ChunkSplitter splitter,
                             ChunkReplicationService replicationService,
                             FileMetadataStore metadataStore) {
        this.splitter = splitter;
        this.replicationService = replicationService;
        this.metadataStore = metadataStore;
    }

    public String uploadFile(byte[] fileData, String filename) {

        String fileId = UUID.randomUUID().toString();

        List<byte[]> chunks =  splitter.split(fileData);
        List<List<String>> allChunkReplicas = new ArrayList<>();

        for (byte[] chunk : chunks) {
            List<String> replicaChunkIds =
                    replicationService.replicateChunkToAllNodes(chunk);

            allChunkReplicas.add(replicaChunkIds);
        }

        metadataStore.put(fileId, allChunkReplicas, filename);
        return fileId;
    }

}
