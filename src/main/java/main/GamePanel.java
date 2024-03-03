package main;

import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;
import tiles.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// CONFIGURAÇOES DE TELA
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 16;
	public final int screenWidth = tileSize * maxScreenCol; // 960px
	public final int screenHeight = tileSize * maxScreenRow; // 768px

	// WORLD MAP - CONFIGURAÇÕES
	public final int maxWorldCol = 100;
	public final int maxWorldRow = 100;

	// FPS
	int FPS = 60;

	// SISTEMA
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Thread gameThread;

	// ENTIDADE(PLAYER) E OBJETO
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	ArrayList<Entity> entityList = new ArrayList<>();

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;

	// LOCALIZAÇÃO PADRÃO DO JOGADOR
	int jogadorX = 100;
	int jogadorY = 100;
	int jogadorVelocidade = 4;
//	private int drawCount;

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {

		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonstro();
		playMusic(0);
		gameState = titleState;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
//        drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
//                drawCount++;
			}

			if (timer >= 1000000000) {
//                drawCount = 0;
				timer = 0;
			}
		}
	}

	// Atualiza sempre que ocorre algo no player
	public void update() {

		if (gameState == playState) {
			// PLAYER
			player.update();

			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					if(monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					if(monster[i].alive == false) {
						monster[i] = null;
					}
				}
			}
		}
		if (gameState == pauseState) {
			// NADA
		}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// DEBUG
		long drawStart = 0;
		if (keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}

		// TELA DE TITULO
		if (gameState == titleState) {
			ui.draw(g2);
		}
		// OUTROS
		else {

			// TILE
			tileM.draw(g2);

			// ADICIONA AS ENTITADES PARA UMA LISTA
			entityList.add(player);

			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}

			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					entityList.add(obj[i]);
				}
			}

			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}

			// SORT
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {

					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});

			// DRAW ENTIDADES
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}

			// REMOVE LISTA VAZIA
			entityList.clear();

			// UI
			ui.draw(g2);
		}

		// DEBUG
		if (keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed);
		}

		g2.dispose();
	}

	public void playMusic(int i) {

		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {

		music.stop();
	}

	public void playSE(int i) {

		se.setFile(i);
		se.play();
	}

}
