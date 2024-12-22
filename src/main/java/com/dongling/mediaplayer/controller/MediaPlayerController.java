package com.dongling.mediaplayer.controller;

import com.dongling.mediaplayer.enums.FileTypesEnum;
import com.dongling.mediaplayer.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MediaPlayerController {

    @Autowired
    private MediaService mediaService;

    public void play(String absolutePath, FileTypesEnum fileType) {
        mediaService.play(absolutePath, fileType);
    }

    public void stop() {
        mediaService.stop();
    }

}
