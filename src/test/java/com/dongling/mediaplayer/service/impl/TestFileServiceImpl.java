package com.dongling.mediaplayer.service.impl;

import com.dongling.mediaplayer.exception.BizException;
import com.dongling.mediaplayer.service.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestFileServiceImpl {

    @Autowired
    private FileService fileService;

    @Test
    public void testGetFilesWithinDirectory() {
        Assertions.assertThrows(BizException.class, () -> fileService.getValidFilesWithinDirectory(null));
        Assertions.assertThrows(BizException.class, () -> fileService.getValidFilesWithinDirectory(""));
        System.out.println(fileService.getValidFilesWithinDirectory("D:\\Software"));
    }

}
