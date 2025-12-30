package com.dfs.backendserver.repositories;

import com.dfs.backendserver.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {
}
