package com.dongling.mediaplayer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileChecker {

    public static boolean isMP4File(File file) {
        // MP4 文件的魔术数字标志（通常包含 "ftyp"）
        byte[] magicBytes = new byte[12]; // 读取文件头的前 12 个字节
        try (FileInputStream fis = new FileInputStream(file)) {
            if (fis.read(magicBytes) < 12) {
                return false; // 文件长度不足 12 字节，不可能是 MP4
            }
        } catch (IOException e) {
            return false;
        }

        // 判断第 4 到第 8 字节是否是 "ftyp"
        if (magicBytes[4] == 'f' && magicBytes[5] == 't' &&
                magicBytes[6] == 'y' && magicBytes[7] == 'p') {
            // 检查第 8 到第 12 字节是否是常见的兼容品牌
            String brand = new String(magicBytes, 8, 4); // 获取兼容品牌标志
            return brand.equals("isom") || brand.equals("iso2") ||
                    brand.equals("mp41") || brand.equals("mp42");
        }
        return false;
    }

    public static boolean isMP3File(File file) {
        if (!file.exists() || !file.isFile()) {
            return false;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] header = new byte[3];
            // 读取文件的前3个字节
            if (fis.read(header) != 3) {
                return false;
            }

            // 检查是否为 ID3 标签
            if (header[0] == 'I' && header[1] == 'D' && header[2] == '3') {
                return true;
            }

            // 如果没有 ID3 标签，检查帧同步标记
            // 读取接下来的2个字节（帧同步标记所在）
            byte[] frameHeader = new byte[2];
            if (fis.read(frameHeader) != 2) {
                return false;
            }

            return (frameHeader[0] & 0xFF) == 0xFF && (frameHeader[1] & 0xE0) == 0xE0;

        } catch (IOException e) {
            return false;
        }
    }

}
