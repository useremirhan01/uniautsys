package com.studentmanagement;

import javax.swing.*;

public class AnimationUtils {

    public static void switchScreen(JFrame currentFrame, JFrame newFrame) {
        Timer timer = new Timer(5, e -> {
            int x = currentFrame.getX();
            currentFrame.setLocation(x - 10, currentFrame.getY());
            if (x < -currentFrame.getWidth()) {
                ((Timer) e.getSource()).stop();
                currentFrame.dispose();
                newFrame.setVisible(true);
            }
        });
        timer.start();
    }
}
