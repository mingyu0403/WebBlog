package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Domain.Attachment;
import kr.hs.dgsw.web02blog.Domain.Post;
import kr.hs.dgsw.web02blog.Domain.User;
import kr.hs.dgsw.web02blog.Protocol.AttachmentProtocol;
import kr.hs.dgsw.web02blog.Repository.AttachmentRepository;
import kr.hs.dgsw.web02blog.Repository.PostRepository;
import kr.hs.dgsw.web02blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public AttachmentProtocol upload(MultipartFile srcFile) {
        String destFinlename
                = "D:/PORTFOLIO/DGSW/3Grade/WebPractice/web02blog/upload/"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"))
                + UUID.randomUUID().toString() + "_"
                + srcFile.getOriginalFilename();

        try {
            File destFile = new File(destFinlename);
            destFile.getParentFile().mkdirs();
            srcFile.transferTo(destFile);
            return new AttachmentProtocol(destFinlename, srcFile.getOriginalFilename());
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public HttpServletResponse download(String type, Long id, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String filePath = null;
            String fileName = null;

            if(type.equals("user")){
                User user = this.userRepository.findById(id).get();
                filePath = user.getProfilePath();
                fileName = user.getProfileName();
            } else if(type.equals("post")){
                Post post = this.postRepository.findById(id).get();
                //TODO: 원래는 하나의 파일만 저장하는 용도로 filePath와 fileName을 받았지만,
                // List로 수정했기 때문에 고쳐야 함.
            }

            File file = new File(filePath);
            if(file.exists() == false) return null;

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if(mimeType == null) mimeType = "application/octet-stream";

            resp.setContentType(mimeType);
            resp.setHeader("Content-Disposition", "inline; filename=\""+ fileName + "\"");
            resp.setContentLength((int)file.length());

            InputStream is = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(is, resp.getOutputStream());
            return resp;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
