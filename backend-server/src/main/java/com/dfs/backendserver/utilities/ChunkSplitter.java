package com.dfs.backendserver.utilities;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class ChunkSplitter {
    static final int CHUNK_SIZE = 5 * 1024 * 1024; // 5 MB


    public List<byte[]> split(byte[] fileData) {
        List<byte[]> chunks = new ArrayList<>();
        int offset = 0;

        while (offset < fileData.length) {
            int end = Math.min(fileData.length, offset + CHUNK_SIZE);
            chunks.add(Arrays.copyOfRange(fileData, offset, end));
            offset = end;
        }
        return chunks;
    }
}
