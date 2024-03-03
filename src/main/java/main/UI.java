package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import objetos.obj_coracao;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font chilankaR;
	BufferedImage coracao_cheio, coracao_metade, coracao_vazio;
	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0;
	
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;

	public UI(GamePanel gp) {

		this.gp = gp;

		try {
			InputStream is = getClass().getResourceAsStream("/fontes/Chilanka-Regular.ttf");
			chilankaR = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// HUD OBJETO
		Entity coracao = new obj_coracao(gp);
		coracao_cheio = coracao.image;
		coracao_metade = coracao.image2;
		coracao_vazio = coracao.image3;
	}

	public void addMessage(String text) {

		message.add(text);
		messageCounter.add(0);
		
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		g2.setFont(chilankaR);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.black);

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();
		}
		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		// DIALOGOS
		if (gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		// TELA DOS STATUS DO JOGADOR
		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
		}

	}

	public void drawPlayerLife() {

//		gp.player.life = 6;

		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int i = 0;

		// VIDA MAXIMA
		while (i < gp.player.maxLife / 2) {
			g2.drawImage(coracao_vazio, x, y, null);
			i++;
			x += gp.tileSize;
		}

		// RESET
		x = gp.tileSize / 2;
		y = gp.tileSize / 2;
		i = 0;

		// VIDA ATUAL
		while (i < gp.player.life) {
			g2.drawImage(coracao_metade, x, y, null);
			i++;
			if (i < gp.player.life) {
				g2.drawImage(coracao_cheio, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
	}
	
	public void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
		
		for (int i = 0; i < message.size(); i++) {
			
			if (message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1;
				messageCounter.set(i, counter);
				messageY += 50;
				
				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
		
	}

	public void drawTitleScreen() {

		g2.setColor(new Color(88, 29, 34));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		// TITULO
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
		String text = "Battle of Mushroom's";

		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;

		// SOMBRA
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);

		// COR PRINCIPAL
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		// IMAGEM PRINCIPAL
		x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

		text = "NOVO JOGO";
		x = getXforCenteredText(text);
		y += gp.tileSize * 6;
		g2.setColor(Color.black);
		g2.drawString(text, x + 3, y + 3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y - 5);
		}

		text = "CARREGAR JOGO";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.setColor(Color.black);
		g2.drawString(text, x + 3, y + 3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y - 5);
		}

		text = "SAIR";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.setColor(Color.black);
		g2.drawString(text, x + 3, y + 3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y - 5);
		}

	}

	public void drawDialogueScreen() {

		// JANELA DE DIALOGO
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;

		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22F));
		x += gp.tileSize;
		y += gp.tileSize;

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}

	}

	public void drawCharacterScreen() {
		
		// CRIANDO UM FRAME
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*7;
		final int frameHeight = gp.tileSize*11;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXTO
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 40;
		
		// NOMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		
		g2.drawString("Vida", textX, textY);
		textY += lineHeight;
		
		g2.drawString("Força", textX, textY);
		textY += lineHeight;

		g2.drawString("Destreza", textX, textY);
		textY += lineHeight;

		g2.drawString("Ataque", textX, textY);
		textY += lineHeight;
		
		g2.drawString("Defesa", textX, textY);
		textY += lineHeight;

		g2.drawString("Exp", textX, textY);
		textY += lineHeight;

		g2.drawString("Próximo Level", textX, textY);
		textY += lineHeight;

		g2.drawString("Moeda", textX, textY);
		textY += lineHeight + 20;

		g2.drawString("Arma", textX, textY);
		textY += lineHeight + 15;

		g2.drawString("Escudo", textX, textY);
		textY += lineHeight;
		
		// VALUES
		int tailX = (frameX + frameWidth) - 30;
		
		// RESETANDO O TEXTY
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "|" + gp.player.maxLife);
		textX = getXforAlignToRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 14, null);		
	}

	public void drawSubWindow(int x, int y, int width, int height) {

		Color c = new Color(0, 0, 0, 180);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

	}

	public void drawPauseScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSADO";
		int x = getXforCenteredText(text);

		int y = gp.screenHeight / 2;

		g2.drawString(text, x, y);

	}

	public int getXforCenteredText(String text) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;

	}
	
	public int getXforAlignToRight(String text, int tailX) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;

	}
}
