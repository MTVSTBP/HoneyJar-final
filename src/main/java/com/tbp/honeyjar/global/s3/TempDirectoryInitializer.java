package com.tbp.honeyjar.global.s3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TempDirectoryInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        File uploadDir = new File("C:/mtvs/project01/tbp/upload");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File tempDir = new File("C:/mtvs/project01/tbp/temp");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
    }
}

