package com.dongling.mediaplayer.controller;

import com.dongling.mediaplayer.pojo.FileDescription;
import com.dongling.mediaplayer.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 本地文件管理
 * @author 东羚
 */
@Component
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 根据路径获取当前路径下的所有文件名称
     * @param directory 路径
     * @return 路境内的所有文件名称
     */
    public List<FileDescription> getFilesWithinDirectory(String directory) {
        return fileService.getValidFilesWithinDirectory(directory);
    }

}
