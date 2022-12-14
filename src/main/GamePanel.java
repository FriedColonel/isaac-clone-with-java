package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
  // SCREEN SETTINGS
  final int originalTileSize = 16;
  final int scale = 3;

  final int tileSize = originalTileSize * scale;
  final int maxScreenCol = 16;
  final int maxScreenRow = 12;
  final int screenWidth = tileSize * maxScreenCol;
  final int ScreenHeight = tileSize * maxScreenRow;

  KeyHandler keyHandler = new KeyHandler();
  Thread gameThread;

  int playerX = 100;
  int playerY = 100;
  int playSpeed = 4;
  int FPS = 60;

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, ScreenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  // public void run() {
  //   double drawInterval = 1000000000/FPS;
  //   double nextDrawTime = System.nanoTime() + drawInterval;

  //   while(gameThread != null) {
  //     update();

  //     repaint();

  //     try {
  //       double remainingTime = nextDrawTime - System.nanoTime();
  //       remainingTime = remainingTime/1000000;

  //       if (remainingTime < 0) {
  //         remainingTime = 0;
  //       }

  //       Thread.sleep((long) remainingTime);
  //       nextDrawTime += drawInterval;
  //     } catch (InterruptedException e) {
  //       // TODO Auto-generated catch block
  //       e.printStackTrace();
  //     }
  //   }
  // }

  public void run() {
    double drawInterval = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    long drawCount = 0;

    while (gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
        drawCount++;
      }

      if (timer > 1000000000) {
        System.out.println("FPS: " + drawCount);
        drawCount = 0;
        timer = 0;
      }
    }
  }

  public void update() {
    if (keyHandler.upPressed == true) {
      playerY -= playSpeed;
    } else if (keyHandler.downPressed == true) {
      playerY += playSpeed;
    } else if (keyHandler.rightPressed == true) {
      playerX += playSpeed;
    } else if (keyHandler.leftPressed == true) {
      playerX -= playSpeed;
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D)g;
    g2.setColor(Color.white);

    g2.fillRect(playerX, playerY, tileSize, tileSize);
    g2.dispose();
  }
}
