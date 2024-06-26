package com.example.phonestoreapplication.common;

public interface HandleFileUploadListener {
    void on_upload_success(String download_url);

    void on_upload_failure(String error_message);
}
