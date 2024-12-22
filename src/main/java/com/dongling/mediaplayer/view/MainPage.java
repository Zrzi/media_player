package com.dongling.mediaplayer.view;

import com.dongling.mediaplayer.controller.FileController;
import com.dongling.mediaplayer.controller.MediaPlayerController;
import com.dongling.mediaplayer.enums.FileTypesEnum;
import com.dongling.mediaplayer.pojo.FileDescription;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

@Component
public class MainPage extends JFrame {

    @Autowired
    private FileController fileController;

    @Autowired
    private MediaPlayerController mediaPlayerController;

    @Value("${service.base.url}")
    private String basePath;

    private String currentPath;

    private JButton backButton;

    private JButton stopButton;

    private JButton prevButton;

    private JButton nextButton;

    private DefaultListModel<FileDescription> listModel;

    private JList<FileDescription> list;

    private JScrollPane pane;

    @PostConstruct
    public void init() {
        this.currentPath = basePath;

        this.setTitle("多媒体播放器");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(1200, 800);
        this.setResizable(false);
        this.setLocation(180, 80);

        this.setLayout(null);

        this.backButton = new JButton("返回上一级");
        this.backButton.addActionListener(e -> moveBack());
        this.backButton.setBounds(60, 20, 100, 20);
        this.add(backButton);

        this.prevButton = new JButton("<");
        this.prevButton.addActionListener(e -> {
            if (Objects.nonNull(list.getSelectedValue())) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex > 0 && !Objects.equals(listModel.get(selectedIndex - 1).getFileType(), FileTypesEnum.FOLDER)) {
                    list.setSelectedIndex(selectedIndex - 1);
                    playMedia();
                }
            }
        });
        this.prevButton.setBounds(450, 20, 50, 20);
        this.add(prevButton);

        this.nextButton = new JButton(">");
        this.nextButton.addActionListener(e -> {
            if (Objects.nonNull(list.getSelectedValue())) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex < listModel.size() - 1 && !Objects.equals(listModel.get(selectedIndex + 1).getFileType(), FileTypesEnum.FOLDER)) {
                    list.setSelectedIndex(selectedIndex + 1);
                    playMedia();
                }
            }
        });
        this.nextButton.setBounds(600, 20, 50, 20);
        this.add(nextButton);

        this.stopButton = new JButton("停止");
        this.stopButton.addActionListener(e -> {
            stopButton.setEnabled(false);
            mediaPlayerController.stop();
        });
        this.stopButton.setBounds(520, 20, 60, 20);
        this.stopButton.setEnabled(false);
        this.add(stopButton);

        this.listModel = new DefaultListModel<>();
        this.list = new JList<>(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.list.setVisibleRowCount(-1);
        this.list.setFont(new Font("Monospaced", Font.PLAIN, 12));

        this.list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileDescription selectedValue = list.getSelectedValue();
                if (Objects.isNull(selectedValue)) {
                    return;
                }
                if (Objects.equals(FileTypesEnum.FOLDER, selectedValue.getFileType())) {
                    String newCurrentPath = currentPath + (StringUtils.endsWith(currentPath, "\\") ? "" : "\\") + selectedValue.getFileName();
                    changeCurrentPath(newCurrentPath);
                } else {
                    playMedia();
                }
            }
        });

        this.pane = new JScrollPane(list);
        this.pane.setBounds(60, 60, 1060, 550);
        this.add(pane);

        this.changeCurrentPath(currentPath);

        this.setVisible(true);
    }

    private void moveBack() {
        if (!StringUtils.equals(basePath, this.currentPath) && !StringUtils.equals(basePath, this.currentPath + "\\")) {
            int index = StringUtils.lastIndexOf(this.currentPath, "\\");
            String newCurrentPath = StringUtils.substring(this.currentPath, 0, index);
            changeCurrentPath(newCurrentPath);
        }
    }

    private void changeCurrentPath(String currentPath) {
        this.currentPath = currentPath;
        List<FileDescription> fileNames = fileController.getFilesWithinDirectory(currentPath);
        this.listModel.clear();
        this.listModel.addAll(fileNames);
    }

    private void playMedia() {
        mediaPlayerController.play(this.list.getSelectedValue().getAbsolutePath(), this.list.getSelectedValue().getFileType());
        stopButton.setEnabled(true);
    }

}
