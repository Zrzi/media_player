package com.dongling.mediaplayer.service;

import com.dongling.mediaplayer.pojo.FileDescription;

import java.util.List;

public interface FileService {

    List<FileDescription> getValidFilesWithinDirectory(String directory);

}
