package com.tbp.honeyjar.global.Firebase;



import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


//@Service
//public class FireBaseService {
//
//    private final Bucket bucket;
//
//    public FireBaseService(Bucket bucket) {
//        this.bucket = bucket;
//    }
//
////    public String uploadFile(MultipartFile file, String fileName) throws IOException, FirebaseAuthException {
////        InputStream content = new ByteArrayInputStream(file.getBytes());
////        Blob blob = bucket.create(fileName, content, file.getContentType());
////        return blob.getMediaLink();
////    }
//
//    public String uploadFile(MultipartFile file, String fileName) throws IOException {
//        InputStream content = new ByteArrayInputStream(file.getBytes());
//        Blob blob = bucket.create(fileName, content, file.getContentType());
//        return getDownloadUrl(blob);
//    }
//
//    public String getFileUrl(String fileName) throws IOException {
//        Blob blob = bucket.get(fileName);
//        if (blob == null) {
//            throw new IOException("File not found in the bucket: " + fileName);
//        }
//        return getDownloadUrl(blob);
//    }
//
////    private String getDownloadUrl(Blob blob) {
////        return String.format("https://storage.googleapis.com/%s/%s", blob.getBucket(), blob.getName());
////    }
//
//    private String getDownloadUrl(Blob blob) throws IOException {
//        if (blob == null) {
//            throw new IOException("Blob is null");
//        }
//        URL signedUrl = blob.signUrl(15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
//        return signedUrl.toString();
//    }
//
//}

//@Service
//public class FireBaseService {
//
//    private final Bucket bucket;
//
//    public FireBaseService(FirebaseApp firebaseApp) {
//        this.bucket = StorageClient.getInstance(firebaseApp).bucket();
//    }
//
//    public String uploadFile(MultipartFile file, String fileName) throws IOException, FirebaseAuthException {
//        InputStream content = new ByteArrayInputStream(file.getBytes());
//        Blob blob = bucket.create(fileName, content, file.getContentType());
//        return blob.getMediaLink();
//    }
//
//    public String getFileUrl(String fileName) throws IOException {
//        Blob blob = bucket.get(fileName);
//        if (blob == null) {
//            throw new IOException("File not found in the bucket: " + fileName);
//        }
//        return getDownloadUrl(blob);
//    }
//    private String getDownloadUrl(Blob blob) throws IOException {
//        if (blob == null) {
//            throw new IOException("Blob is null");
//        }
//        URL signedUrl = blob.signUrl(15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
//        return signedUrl.toString();
//    }
//
//}

//@Service
//public class FireBaseService {
//
//    private final Bucket bucket;
//
//    public FireBaseService(FirebaseApp firebaseApp) {
//        this.bucket = StorageClient.getInstance(firebaseApp).bucket();
//    }
//
//    public String uploadFile(MultipartFile file, String fileName) throws IOException, FirebaseAuthException {
//        InputStream content = new ByteArrayInputStream(file.getBytes());
//        Blob blob = bucket.create(fileName, content, file.getContentType());
//        return blob.getMediaLink();
//    }
//
//    public String getFileUrl(String fileName) throws IOException {
//        Blob blob = bucket.get(fileName);
//        if (blob == null) {
//            throw new IOException("File not found in the bucket: " + fileName);
//        }
//        return getDownloadUrl(blob);
//    }
//
//    public void deleteFile(String fileName) throws IOException {
//        Blob blob = bucket.get(fileName);
//        if (blob == null) {
//            throw new IOException("File not found in the bucket: " + fileName);
//        }
//        blob.delete();
//    }
//
//    private String getDownloadUrl(Blob blob) throws IOException {
//        if (blob == null) {
//            throw new IOException("Blob is null");
//        }
//        URL signedUrl = blob.signUrl(15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
//        return signedUrl.toString();
//    }
//}


@Service
public class FireBaseService {

    private final Bucket bucket;

    public FireBaseService(FirebaseApp firebaseApp) {
        this.bucket = StorageClient.getInstance(firebaseApp).bucket();
    }

    public String uploadFile(MultipartFile file, String fileName) throws IOException, FirebaseAuthException {
        InputStream content = new ByteArrayInputStream(file.getBytes());
        Blob blob = bucket.create(fileName, content, file.getContentType());
        return blob.getMediaLink();
    }

    public String getFileUrl(String fileName) throws IOException {
        Blob blob = bucket.get(fileName);
        if (blob == null) {
            throw new IOException("File not found in the bucket: " + fileName);
        }
        return getDownloadUrl(blob);
    }

    public void deleteFile(String fileUrl) throws IOException {
        // URL에서 파일 경로 추출
        String fileName = extractFileName(fileUrl);

        // 파일 삭제 시도
        System.out.println("Attempting to delete file: " + fileName);
        Blob blob = bucket.get(fileName);
        if (blob == null) {
            System.out.println("File not found in the bucket: " + fileUrl);
            throw new IOException("File not found in the bucket: " + fileUrl);
        }

        boolean deleted = blob.delete();
        if (!deleted) {
            System.out.println("Failed to delete file: " + fileUrl);
            throw new IOException("Failed to delete file: " + fileUrl);
        } else {
            System.out.println("File successfully deleted: " + fileUrl);
        }
    }

    private String extractFileName(String fileUrl) {
        if (fileUrl.startsWith("gs://")) {
            // gs:// URL의 경우
            return fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        } else {
            // 일반 URL의 경우
            try {
                URL url = new URL(fileUrl);
                String path = url.getPath();
                // "/v0/b/<bucket-name>/o/<file-path>" 에서 <file-path> 추출
                return path.substring(path.lastIndexOf('/') + 1);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Invalid URL: " + fileUrl);
            }
        }
    }




    private String getDownloadUrl(Blob blob) throws IOException {
        if (blob == null) {
            throw new IOException("Blob is null");
        }
        URL signedUrl = blob.signUrl(15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
        return signedUrl.toString();
    }
}
