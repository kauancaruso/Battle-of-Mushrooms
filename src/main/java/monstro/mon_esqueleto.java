package monstro;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class mon_esqueleto extends Entity {

	GamePanel gp;
	
	public mon_esqueleto(GamePanel gp) {
		super(gp);
		
		this.gp = gp;

		type = 2;

		name = "Esqueleto de Sacerdotes";
		velocidade = 1;
		maxLife = 6;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 3;

		getImage();
	}

	public void getImage() {

		up1 = setup("/monstro/esqueleto-costas-1", gp.tileSize, gp.tileSize);
		up2 = setup("/monstro/esqueleto-costas-2", gp.tileSize, gp.tileSize);
		down1 = setup("/monstro/esqueleto-frente-1", gp.tileSize, gp.tileSize);
		down2 = setup("/monstro/esqueleto-frente-2", gp.tileSize, gp.tileSize);
		right1 = setup("/monstro/esqueleto-direita-1", gp.tileSize, gp.tileSize);
		right2 = setup("/monstro/esqueleto-direita-2", gp.tileSize, gp.tileSize);
		left1 = setup("/monstro/esqueleto-esquerda-1", gp.tileSize, gp.tileSize);
		left2 = setup("/monstro/esqueleto-esquerda-2", gp.tileSize, gp.tileSize);
	}

	public void setAction() {

		actionLockCounter++;

		if (actionLockCounter == 120) {

			Random random = new Random();
			int i = random.nextInt(100) + 1;

			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "down";
			}
			if (i > 50 && i <= 75) {
				direction = "left";
			}
			if (i > 75 && i <= 100) {
				direction = "right";
			}

			actionLockCounter = 0;

		}

	}
	
	public void damageReaction() {
		
		actionLockCounter = 0;
		direction = gp.player.direction;
		
	}

}
