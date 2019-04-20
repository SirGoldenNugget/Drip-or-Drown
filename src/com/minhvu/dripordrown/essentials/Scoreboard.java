package com.minhvu.dripordrown.essentials;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Scoreboard {
    public static int score = 0;

    private static long timer = System.currentTimeMillis();
    private static int time;

    public static void paint(Graphics2D g2d) {
        time = (int) ((System.currentTimeMillis() - timer) / 1000.0);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("calibri", Font.BOLD, 16));
        g2d.drawString("Time: " + time + "s Score: " + score, 10, 20);
    }

    public static void output() {
        try {
            FileWriter fileWriter = new FileWriter(Calendar.getInstance().getTime().getTime() + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Time: " + time + "s");
            bufferedWriter.newLine();
            bufferedWriter.write("Score: " + score);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reset() {
        score = 0;
        timer = System.currentTimeMillis();
    }
}
