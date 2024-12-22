package com.dongling.mediaplayer.enums;

import org.apache.commons.lang3.StringUtils;

public enum FileTypesEnum {

    FOLDER("folder", "文件夹"),

    MP4("mp4", "mp4类型文件"),

    MP3("mp3", "mp3类型文件"),

    ;

    /**
     * 类型编码
     */
    private final String code;

    /**
     * 类型描述
     */
    private final String description;

    FileTypesEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static FileTypesEnum getByCode(String code) {
        for (FileTypesEnum e : FileTypesEnum.values()) {
            if (StringUtils.equals(e.getCode(), code)) {
                return e;
            }
        }
        return null;
    }

}
