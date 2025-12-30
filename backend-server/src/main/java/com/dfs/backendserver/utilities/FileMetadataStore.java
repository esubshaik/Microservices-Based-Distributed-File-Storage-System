package com.dfs.backendserver.utilities;

import org.springframework.stereotype.Component;

import java.util.List;

import com.dfs.backendserver.entities.*;
import com.dfs.backendserver.repositories.FileRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileMetadataStore {

    private final FileRepository repository;

    public FileMetadataStore(FileRepository repository) {
        this.repository = repository;
    }

    public void put(String fileId, List<List<String>> chunkReplicas, String filename) {

        FileEntity file = new FileEntity();
        file.setFileId(fileId);
        file.setOriginalName(filename);

        List<ChunkEntity> chunks = new ArrayList<>();

        for (int i = 0; i < chunkReplicas.size(); i++) {

            ChunkEntity chunk = new ChunkEntity();
            chunk.setChunkIndex(i);
            chunk.setFile(file);

            List<ReplicaEntity> replicas = new ArrayList<>();
            for (String chunkId : chunkReplicas.get(i)) {
                replicas.add(new ReplicaEntity(chunkId, chunk));
            }

            chunk.setReplicas(replicas);
            chunks.add(chunk);
        }

        file.setChunks(chunks);
        repository.save(file);
    }

    public List<List<String>> get(String fileId) {

        return repository.findById(fileId)
                .map(file ->
                        file.getChunks().stream()
                                .map(chunk ->
                                        chunk.getReplicas().stream()
                                                .map(ReplicaEntity::getChunkId)
                                                .toList()
                                ).toList()
                )
                .orElse(null);
    }
}

