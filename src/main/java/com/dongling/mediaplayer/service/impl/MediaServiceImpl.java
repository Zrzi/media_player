package com.dongling.mediaplayer.service.impl;

import com.dongling.mediaplayer.enums.FileTypesEnum;
import com.dongling.mediaplayer.exception.BizException;
import com.dongling.mediaplayer.pojo.singlton.ProcessHolder;
import com.dongling.mediaplayer.service.MediaService;
import com.dongling.mediaplayer.utils.FileChecker;
import jakarta.annotation.PreDestroy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service
public class MediaServiceImpl implements MediaService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Override
    public void play(String absolutePath, FileTypesEnum fileType) {
        checkValidFile(absolutePath, fileType);
        ProcessBuilder processBuilder = null;
        switch (fileType) {
            case MP3:
                processBuilder = new ProcessBuilder("ffplay", "-i", absolutePath, "-autoexit", "-nodisp");
                break;
            case MP4:
                processBuilder = new ProcessBuilder("ffplay", "-i", absolutePath, "-autoexit", "-loglevel", "quiet");
                break;
        }

        try {
            ProcessHolder.start(processBuilder);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void stop() {
        try {
            ProcessHolder.stopProcess();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void checkValidFile(String absolutePath, FileTypesEnum fileType) {
        if (StringUtils.isBlank(absolutePath)) {
            LOGGER.info("接收到的路径为空。");
            throw new BizException("路径不能为空。");
        }
        File file = Paths.get(absolutePath).toFile();
        if (!file.exists()) {
            LOGGER.info("接收到的路径{}不存在。", absolutePath);
            throw new BizException("路径不存在。");
        }
        switch (fileType) {
            case FileTypesEnum.MP3:
                if (!FileChecker.isMP3File(file)) {
                    LOGGER.info("接收到的文件{}不是MP3文件。", absolutePath);
                    throw new BizException("不是MP3文件。");
                }
                break;
            case FileTypesEnum.MP4:
                if (!FileChecker.isMP4File(file)) {
                    LOGGER.info("接收到的文件{}不是MP4文件。", absolutePath);
                    throw new BizException("不是MP4文件。");
                }
                break;
            default:
                LOGGER.info("接收到的文件{}类型是{}，不支持。", absolutePath, fileType.getCode());
                throw new BizException("不支持的文件类型。");
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            ProcessHolder.stopProcess();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
