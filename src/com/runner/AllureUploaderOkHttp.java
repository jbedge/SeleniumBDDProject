package com.runner;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AllureUploaderOkHttp {

    private static final String ALLURE_RESULTS_DIR = "G:\\IdeaProjects\\RD\\rrs\\allure-results";
    private static final String ALLURE_SERVER_URL = "http://localhost:5050";
    private static final String PROJECT_ID = "rd-all-modules";

    public static void main(String[] args) {
        try {
//            createAllureProject(PROJECT_ID,"RD ALL Modules");
            uploadAllureResults();
            generateAllureReport(PROJECT_ID, "Local Execution", "http://localhost", "manual");
        } catch (IOException e) {
            System.err.println("❌ Upload failed: " + e.getMessage());
        }
    }

    public static void uploadAllureResults() throws IOException {
        File resultsDir = new File(ALLURE_RESULTS_DIR);
        if (!resultsDir.exists() || !resultsDir.isDirectory()) {
            System.err.println("❌ Allure results directory not found: " + resultsDir.getAbsolutePath());
            return;
        }

        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        File[] resultFiles = resultsDir.listFiles();
        if (resultFiles == null || resultFiles.length == 0) {
            System.out.println("⚠️ No files to upload in allure-results.");
            return;
        }

        for (File file : resultFiles) {
            if (file.isFile()) {
                multipartBuilder.addFormDataPart("files[]",
                        file.getName(),
                        RequestBody.create(file, MediaType.parse("application/octet-stream")));
            }
        }

        Request request = new Request.Builder()
                .url(ALLURE_SERVER_URL + "/allure-docker-service/send-results?project_id=" + PROJECT_ID)
                .post(multipartBuilder.build())
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Allure results uploaded successfully.");
            } else {
                System.err.println("❌ Upload failed. HTTP Status: " + response.code());
                System.err.println("Response: " + Objects.requireNonNull(response.body()).string());
            }
        }
    }


    public static void createAllureProject(String projectId, String projectName) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String jsonBody = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", projectId, projectName);

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("http://localhost:5050/allure-docker-service/projects")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Project created successfully.");
            } else if (response.code() == 409) {
                System.out.println("⚠️ Project already exists: " + projectId);
            } else {
                System.err.println("❌ Failed to create project. Status: " + response.code());
                System.err.println("Response: " + response.body().string());
            }
        }
    }

    public static void generateAllureReport(String projectId, String executionName, String executionFrom, String executionType) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = HttpUrl.parse(ALLURE_SERVER_URL + "/allure-docker-service/generate-report")
                .newBuilder()
                .addQueryParameter("project_id", projectId)
                .addQueryParameter("execution_name", executionName)
                .addQueryParameter("execution_from", executionFrom)
                .addQueryParameter("execution_type", executionType)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("✅ Report generated successfully.");
                String reportUrl = responseBody.contains("report_url")
                        ? responseBody.split("\"report_url\":\"")[1].split("\"")[0]
                        : null;
                if (reportUrl != null) {
                    System.out.println("📄 Allure Report URL: " + reportUrl);
                }
            } else {
                System.err.println("❌ Report generation failed. Status: " + response.code());
                System.err.println("Response: " + response.body().string());
            }
        }
    }


}
