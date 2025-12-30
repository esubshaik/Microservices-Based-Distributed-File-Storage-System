package com.dfs.backendserver.services;

import com.dfs.backendserver.client.NodeFeignClient;
import com.dfs.backendserver.utilities.FileMetadataStore;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class FileDownloadService {

    private final FileMetadataStore metadataStore;
    private final ChunkDownloadService chunkDownloadService;

    public FileDownloadService(FileMetadataStore metadataStore,
                               ChunkDownloadService chunkDownloadService) {
        this.metadataStore = metadataStore;
        this.chunkDownloadService = chunkDownloadService;
    }

    public byte[] downloadFile(String fileId) {

        List<List<String>> chunkReplicas = metadataStore.get(fileId);

        if (chunkReplicas == null) {
            throw new RuntimeException("File not found");
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        for (List<String> replicas : chunkReplicas) {

            boolean downloaded = false;

            for (String chunkId : replicas) {
                try {
                    byte[] chunk =
                            chunkDownloadService.downloadChunk(chunkId);
                    output.write(chunk);
                    downloaded = true;
                    break;
                } catch (Exception e) {
                    System.out.println(
                            "Replica failed for chunkId=" + chunkId
                    );
                }
            }

            if (!downloaded) {
                throw new RuntimeException(
                        "Failed to download chunk, all replicas unavailable"
                );
            }
        }

        return output.toByteArray();
    }
}
