package main;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][];

	int previousEventX, previousEventY;
	boolean canTouchEvent = true;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

		int col = 0;
		int row = 0;
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}

	}

	public void checkEvent() {

		// CHECANDO SE O JOGADOR ESTÁ A 1 TILE DE DISTANCIA DO ULTIMO EVENTO
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);

		if (distance > gp.tileSize) {
			canTouchEvent = true;
		}

		if (canTouchEvent == true) {
			// DANO - BURACO / TRAP
			if (hit(52, 51, "any") == true) {
				demagePit(52, 51, gp.dialogueState);
			}

			// CURA
			if (hit(49, 50, "any") == true) {
				pocaoCura(49, 50, gp.dialogueState);
			}

			// TELEPORTE
			if (hit(82, 23, "right") == true) {
				teleporte(gp.dialogueState);
			}
		}

	}

	public boolean hit(int col, int row, String reqDirection) {

		boolean hit = false;

		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;

				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}

		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;
	}

	public void teleporte(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teletranspotado!";
		gp.player.worldX = gp.tileSize * 18;
		gp.player.worldY = gp.tileSize * 82;
	}

	public void demagePit(int col, int row, int gameState) {

		gp.gameState = gameState;
		gp.ui.currentDialogue = "Você tropeçou, cabaço";
		gp.player.life -= 1;
//		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}

	public void pocaoCura(int col, int row, int gameState) {

		if (gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.ui.currentDialogue = "Você tomou uma poção, sua vida foi restaurada";
			gp.player.life = gp.player.maxLife;
			gp.aSetter.setMonstro();
		}
	}

}
