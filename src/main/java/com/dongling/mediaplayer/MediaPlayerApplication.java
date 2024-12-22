package com.dongling.mediaplayer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MediaPlayerApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(MediaPlayerApplication.class);
        builder.headless(false);
        builder.run(args);
    }

}
