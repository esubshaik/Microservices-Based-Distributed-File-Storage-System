package com.dfs.backendserver.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "chunks")
public class ChunkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int chunkIndex;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    @OneToMany(
            mappedBy = "chunk",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<ReplicaEntity> replicas;

    public int getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(int chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public List<ReplicaEntity> getReplicas() {
        return replicas;
    }

    public void setReplicas(List<ReplicaEntity> replicas) {
        this.replicas = replicas;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }
}

