package com.saraebadi.github.roozeto.model;

public class Download {
    private int downloadId;
    private String downloadUrl;
    private int downloadComplete;

    public Download(String downloadUrl, int downloadComplete) {
        this.downloadUrl = downloadUrl;
        this.downloadComplete = downloadComplete;
    }

    public Download(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Download(int downloadId, String downloadUrl, int downloadComplete) {
        this.downloadId = downloadId;
        this.downloadUrl = downloadUrl;
        this.downloadComplete = downloadComplete;
    }

    public int getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(int downloadId) {
        this.downloadId = downloadId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getDownloadComplete() {
        return downloadComplete;
    }

    public void setDownloadComplete(int downloadComplete) {
        this.downloadComplete = downloadComplete;
    }
}
