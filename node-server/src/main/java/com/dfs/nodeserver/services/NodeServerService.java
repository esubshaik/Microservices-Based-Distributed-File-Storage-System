package com.dfs.nodeserver.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class NodeServerService {

    @Value("${storage.base-path}")
    private String basePath;

//    public void save(String chunkId, byte[] data) throws IOException {
//        Path path = Paths.get(basePath, chunkId);
//        Files.createDirectories(path.getParent());
//        Files.write(path, data);
//    }

    public Resource load(String chunkId) throws IOException {
        Path path = Paths.get(basePath, chunkId);
        return new UrlResource(path.toUri());
    }
    public void save(String chunkId, byte[] data) throws IOException {
        Path path = Paths.get(basePath, chunkId);
        Files.createDirectories(path.getParent());

        try (OutputStream os = Files.newOutputStream(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {
            os.write(data);
        }
    }

}
