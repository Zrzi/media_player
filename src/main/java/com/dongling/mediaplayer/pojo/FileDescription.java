package com.dongling.mediaplayer.pojo;

import com.dongling.mediaplayer.enums.FileTypesEnum;

public class FileDescription {

    private String fileName;

    private FileTypesEnum fileType;

    private String absolutePath;

    public FileDescription(String fileName, FileTypesEnum fileType, String absolutePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.absolutePath = absolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileTypesEnum getFileType() {
        return fileType;
    }

    public void setFileType(FileTypesEnum fileType) {
        this.fileType = fileType;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    @Override
    public String toString() {
        return this.fileName;
    }
}
