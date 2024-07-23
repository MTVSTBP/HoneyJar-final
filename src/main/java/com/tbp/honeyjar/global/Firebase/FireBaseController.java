package com.tbp.honeyjar.global.Firebase;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


//@RestController
//public class FireBaseController {
//
//    private final FireBaseService fireBaseService;
//
//    public FireBaseController(FireBaseService fireBaseService) {
//        this.fireBaseService = fireBaseService;
//    }
//
//    @PostMapping("/files")
//    public List<String> uploadFiles(@RequestParam("file") MultipartFile[] files) throws IOException, FirebaseAuthException {
//        List<String> fileUrls = new ArrayList<>();
//        for (MultipartFile file : files) {
//            String fileName = UUID.randomUUID().toString();
//            String fileUrl = fireBaseService.uploadFile(file, fileName);
//            fileUrls.add(fileUrl);
//        }
//        return fileUrls;
//    }
//}

@RestController
public class FireBaseController {

    private final FireBaseService fireBaseService;

    public FireBaseController(FireBaseService fireBaseService) {
        this.fireBaseService = fireBaseService;
    }

    @PostMapping("/files")
    public List<String> uploadFiles(@RequestParam("file") MultipartFile[] files) throws IOException, FirebaseAuthException {
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString();
            String fileUrl = fireBaseService.uploadFile(file, fileName);
            fileUrls.add(fileUrl);
        }
        return fileUrls;
    }
}
