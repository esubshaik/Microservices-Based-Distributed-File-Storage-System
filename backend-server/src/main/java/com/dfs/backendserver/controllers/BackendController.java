package com.dfs.backendserver.controllers;

import com.dfs.backendserver.entities.FileEntity;
import com.dfs.backendserver.repositories.FileRepository;
import com.dfs.backendserver.services.FileDownloadService;
import com.dfs.backendserver.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/files")
public class BackendController {

    private final FileUploadService uploadService;
    private final FileDownloadService downloadService;
    private final FileRepository fileRepository;

    @Autowired
    public BackendController(FileUploadService uploadService,
                          FileDownloadService downloadService, FileRepository fileRepository) {
        this.uploadService = uploadService;
        this.downloadService = downloadService;
        this.fileRepository = fileRepository;
    }

//    @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public String upload(@RequestBody byte[] fileData) {
//        return uploadService.uploadFile(fileData);
//    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public String upload(
            @RequestBody byte[] fileData,
            @RequestHeader("X-Filename") String filename
    ) {
        return uploadService.uploadFile(fileData, filename);
    }


    @GetMapping(value = "/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] download(@PathVariable String fileId) {
        return downloadService.downloadFile(fileId);
    }

//    @GetMapping("/list")
//    public List<String> listFiles() {
//        return fileRepository.findAll()
//                .stream()
//                .map(FileEntity::getFileId)
//                .toList();
//    }

    public record FileListDTO(
            String fileId,
            String originalName
    ) {}


    @GetMapping("/list")
    public List<FileListDTO> listFiles() {
        return fileRepository.findAll()
                .stream()
                .map(file -> new FileListDTO(
                        file.getFileId(),
                        file.getOriginalName()
                ))
                .toList();
    }

}
