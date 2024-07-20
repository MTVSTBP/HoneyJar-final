package com.tbp.honeyjar.global.Firebase;



import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FireBaseService {

    private final Bucket bucket;

    public FireBaseService(Bucket bucket) {
        this.bucket = bucket;
    }

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        InputStream content = file.getInputStream();
        Blob blob = bucket.create(fileName, content, file.getContentType());
        return blob.getMediaLink();
    }
}