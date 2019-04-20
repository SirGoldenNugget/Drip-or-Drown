package com.minhvu.dripordrown;

import com.minhvu.dripordrown.entity.CannonBall;
import com.minhvu.dripordrown.entity.Player;
import com.minhvu.dripordrown.essentials.Menu;
import com.minhvu.dripordrown.map.Maps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {
    private static Game instance;
    private JFrame frame;
    private boolean running = false;
    private Thread thread;

    private State state;
    private Maps maps;

    private CopyOnWriteArrayList<Player> players;
    private CopyOnWriteArrayList<CannonBall> cannonBalls;

    public Game() {
        instance = this;

        state = State.menu;

        KeyListener keylistener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                for (Player player : players) {
                    player.keyPressed(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                for (Player player : players) {
                    player.keyReleased(e);
                }
            }
        };

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

        maps = new Maps();

        frame = new JFrame("Drip or Drown");
        frame.add(this);
        frame.setSize(1920, 1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        players = new CopyOnWriteArrayList<>();
        players.add(new Player(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_E));

        cannonBalls = new CopyOnWriteArrayList<>();

        start();
    }

    // Entry Point.
    public static void main(String[] args) {
        new Game();
    }

    public static Game getInstance() {
        return instance;
    }

    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        thread = new Thread(this);
        thread.start();
    }

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

    private void update() {
        if (state.equals(State.play)) {
            for (Player player : players) {
                player.update();
            }

            cannonBalls.removeIf(cannonBall -> !cannonBall.isAlive());
        }

        repaint();
    }

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

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(Color.BLACK);

        super.paint(g2d);

        g2d.drawImage(maps.getCurrentMap().getBufferedImage(), 0, 0, Game.getInstance());

        if (state.equals(State.play)) {
//            Scoreboard.paint(g2d);

            for (CannonBall cannonBall : cannonBalls) {
                cannonBall.paint(g2d);
            }

            for (Player player : players) {
                player.paint(g2d);
            }
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

    public CopyOnWriteArrayList<Player> getPlayers() {
        return players;
    }

    public CopyOnWriteArrayList<CannonBall> getCannonBalls() {
        return cannonBalls;
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
