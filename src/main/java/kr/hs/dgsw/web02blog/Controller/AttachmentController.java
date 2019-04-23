package kr.hs.dgsw.web02blog.Controller;

import kr.hs.dgsw.web02blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web02blog.Protocol.ResponseType;
import kr.hs.dgsw.web02blog.Service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@RestController
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/attachment")
    public ResponseFormat upload(@RequestPart MultipartFile srcFile){
        return new ResponseFormat(
                ResponseType.ATTACHMENT_UPLOAD,
                attachmentService.upload(srcFile)
        );
    }

    @GetMapping("/attachment/{type}/{id}")
    public void download(@PathVariable String type, @PathVariable Long id,
                         HttpServletRequest req, HttpServletResponse resp){
        resp =  attachmentService.download(type, id, req, resp);
    }

}
