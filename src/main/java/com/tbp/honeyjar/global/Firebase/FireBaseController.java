package com.tbp.honeyjar.global.Firebase;

import com.google.firebase.FirebaseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FireBaseController {

    private final FireBaseService fireBaseService;

    public FireBaseController(FireBaseService fireBaseService) {
        this.fireBaseService = fireBaseService;
    }

    @PostMapping("/files")
    public String uploadFile(@RequestParam("file") MultipartFile[] files, @RequestParam("nameFile") String nameFile) throws IOException, FirebaseException {
        if (files.length == 0) {
            return "is empty";
        }

        for (MultipartFile file : files) {
            fireBaseService.uploadFile(file, nameFile);
        }

        return "Files uploaded successfully";
    }
}
