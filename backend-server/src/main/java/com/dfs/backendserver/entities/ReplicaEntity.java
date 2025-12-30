package com.dfs.backendserver.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "replicas")
public class ReplicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chunkId;

    @ManyToOne
    @JoinColumn(name = "chunk_id_fk")
    private ChunkEntity chunk;

    public ReplicaEntity() {}

    public ReplicaEntity(String chunkId, ChunkEntity chunk) {
        this.chunkId = chunkId;
        this.chunk = chunk;
    }

    public String getChunkId() {
        return chunkId;
    }
}

