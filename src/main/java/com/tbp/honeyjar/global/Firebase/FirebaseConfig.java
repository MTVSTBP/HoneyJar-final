package com.tbp.honeyjar.global.Firebase;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        ClassPathResource serviceAccountResource = new ClassPathResource(firebaseConfigPath);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountResource.getInputStream()))
                .setStorageBucket(firebaseBucket)
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    @Bean
    public Bucket bucket(FirebaseApp firebaseApp) {
        return StorageClient.getInstance(firebaseApp).bucket();
    }
}
