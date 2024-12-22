package com.dongling.mediaplayer.service;

import com.dongling.mediaplayer.enums.FileTypesEnum;

public interface MediaService {

    void play(String absolutePath, FileTypesEnum fileType);

    void stop();

}
