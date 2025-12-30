package com.dfs.backendserver.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    private String fileId;

    private String originalName;

    @OneToMany(
            mappedBy = "file",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @OrderBy("chunkIndex ASC")
    private List<ChunkEntity> chunks;

    public String getFileId() {
        return fileId;
    }
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<ChunkEntity> getChunks() {
        return chunks;
    }

    public void setChunks(List<ChunkEntity> chunks) {
        this.chunks = chunks;
    }
}

