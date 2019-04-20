package com.minhvu.dripordrown;

import com.minhvu.dripordrown.entity.Player;
import com.minhvu.dripordrown.essentials.Menu;
import com.minhvu.dripordrown.essentials.Scoreboard;
import com.minhvu.dripordrown.map.Maps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {
    // Used For Accessing JPanel Method.
    private static Game instance;
    private JFrame frame;
    private boolean running = false;
    private Thread thread;

    private State state;
    private Maps maps;

    // Objects Used In The Game.
    private Player player;

    // Constructor.
    public Game() {
        instance = this;

        state = State.menu;

        // Anonymous Use Of Keyboard Input.
        KeyListener keylistener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }
        };

        // Anonymouse Use Of Mouse Input.
        MouseListener mouselistener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Menu.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };

        addKeyListener(keylistener);
        addMouseListener(mouselistener);
        setFocusable(true);

        // Load In The Sprite Sheets.
        maps = new Maps();

        // Create The Frame.
        frame = new JFrame("Drip or Drown");
        frame.add(this);
        frame.setSize(1920, 1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize Everyhing.
        player = new Player();

        // Begins The Thread.
        start();
    }

    // Entry Point.
    public static void main(String[] args) {
        new Game();
    }

    public static Game getInstance() {
        return instance;
    }

    // Starts The Thread.
    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        thread = new Thread(this);
        thread.start();
    }

    // The Heart Of The Game: The Game Loop.
    @Override
    public void run() {
        long lasttime = System.nanoTime();
        final double ticks = 60.0;
        double nanoseconds = 1000000000 / ticks;
        double delta = 0;

        while (running) {
            long time = System.nanoTime();
            delta += (time - lasttime) / nanoseconds;
            lasttime = time;

            if (delta >= 1) {
                update();
                delta--;
            }
        }

        stop();
    }

    // Updates The Objects.
    private void update() {
        if (state.equals(State.play)) {
            player.update();
        }

        repaint();
    }

    // Stops The Thread.
    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(1);
    }

    // Used For Painting/Rendering Images.
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(Color.BLACK);

        super.paint(g2d);

        g2d.drawImage(maps.getCurrentMap().getBufferedImage(), 0, 0, Game.getInstance());

        if (state.equals(State.play)) {
            Scoreboard.paint(g2d);
            player.paint(g2d);
        } else if (state.equals(State.menu)) {
            Menu.paint(g2d);
        } else if (state.equals(State.end)) {
        }
    }

    public void reset() {

    }

    public Frame getFrame() {
        return frame;
    }

    public Maps getMaps() {
        return maps;
    }

    public Player getPlayer() {
        return player;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        menu,
        play,
        end
    }
}
