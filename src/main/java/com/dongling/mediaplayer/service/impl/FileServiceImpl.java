package com.dongling.mediaplayer.service.impl;

import com.dongling.mediaplayer.enums.FileTypesEnum;
import com.dongling.mediaplayer.exception.BizException;
import com.dongling.mediaplayer.pojo.FileDescription;
import com.dongling.mediaplayer.service.FileService;
import com.dongling.mediaplayer.utils.FileChecker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<FileDescription> getValidFilesWithinDirectory(String directory) {
        if (StringUtils.isBlank(directory)) {
            log.info("接收到的路径为空。");
            throw new BizException("路径不能为空。");
        }
        if (!StringUtils.endsWith(directory, "\\")) {
            directory += "\\";
        }
        File file = Path.of(directory).toFile();
        if (!file.exists()) {
            log.info("接收到的路径{}为空。", directory);
            throw new BizException("路径不存在。");
        }
        if (!file.isDirectory()) {
            log.info("接收到的路径{}为不是文件夹。", directory);
            throw new BizException("路径不是文件夹。");
        }
        File[] files = file.listFiles();
        if (Objects.isNull(files)) {
            return Collections.emptyList();
        }
        List<FileDescription> fileDescriptions = new ArrayList<>();
        for (File f : files) {
            if (StringUtils.equals(f.getName(), "$RECYCLE.BIN")
                    || StringUtils.equals(f.getName(), "System Volume Information")) {
                continue;
            }
            if (f.isDirectory()) {
                fileDescriptions.add(new FileDescription(f.getName(), FileTypesEnum.FOLDER, f.getAbsolutePath()));
            } else if (FileChecker.isMP4File(f)) {
                fileDescriptions.add(new FileDescription(f.getName(), FileTypesEnum.MP4, f.getAbsolutePath()));
            } else if (FileChecker.isMP3File(f)) {
                fileDescriptions.add(new FileDescription(f.getName(), FileTypesEnum.MP3, f.getAbsolutePath()));
            }
        }
        return fileDescriptions;
    }

}
