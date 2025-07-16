package bronya.admin.module.media.controller;

import java.io.File;

import org.dromara.x.file.storage.core.Downloader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import bronya.admin.base.util.MyFileUtil;
import bronya.admin.module.media.controller.resp.UploadResp;
import bronya.admin.module.media.domain.SysFile;
import bronya.admin.module.media.service.SysFileBizService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/sys-file")
@RequiredArgsConstructor
@FieldNameConstants
public class SysFileController {
    public final String baseUrl = "/admin/sys-file";
    private final SysFileBizService fileBizService;

    @SneakyThrows
    @PostMapping("/upload/file")
    public UploadResp uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        @Cleanup("deleteOnExit")
        File file = MyFileUtil.convert(multipartFile);
        SysFile upload = fileBizService.upload(file);
        String filename = upload.getFilename();
        String url = STR."\{SysFileController.Fields.baseUrl}/file/\{filename}";
        return new UploadResp(filename,url,upload.getOriginalFilename());
    }

    @SneakyThrows
    @GetMapping(value = "/file/{fileName}")
    public void file(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        Downloader download = fileBizService.downloadByFileName(fileName);
        download.outputStream(response.getOutputStream());
    }

    @SneakyThrows
    @PostMapping("/upload/image")
    public UploadResp uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        @Cleanup("deleteOnExit")
        File file = MyFileUtil.convert(multipartFile);
        SysFile upload = fileBizService.upload(file);
        String filename = upload.getFilename();
        String url = STR."\{SysFileController.Fields.baseUrl}/image/\{filename}";
        return new UploadResp(filename,url,upload.getOriginalFilename());
    }

    @SneakyThrows
    @GetMapping(value = "/image/{fileName}")
    public void image(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        Downloader download = fileBizService.downloadByFileName(fileName);
        download.outputStream(response.getOutputStream());
    }
}
