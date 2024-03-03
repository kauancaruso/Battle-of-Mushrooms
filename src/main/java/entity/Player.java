package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import objetos.obj_escudo_de_madeira;
import objetos.obj_espada_normal;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	int chavePega = 0;
	int bauAberto = 0;
	int cogumeloPego = 0;
	int estrelaPega = 0;
	public boolean attackCanceled = false;

	public Player(GamePanel gp, KeyHandler keyH) {

		super(gp);

		this.gp = gp;
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 12;
		solidArea.y = 20;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 20;
		solidArea.height = 20;
		
		attackArea.width = 36;
		attackArea.height = 36;

		setDefaultValues(); // Carrega os valores "DEFAULTS"
		getPlayerImage(); // Carrega o metodo que esta buscando as sprites
		getPlayerAttackImage(); // Imagens para o ataque
	}

	public void setDefaultValues() {

		worldX = gp.tileSize * 50;
		worldY = gp.tileSize * 51;
		velocidade = 4;
		direction = "down";

		// STATUS DO JOGADOR
		level = 1;
		maxLife = 6;
		life = maxLife;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new obj_espada_normal(gp);
		currentShield = new obj_escudo_de_madeira(gp);
		attack = getAttack();
		defense = getDefense();
	}
	
	public int getAttack() {
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}

	// Busca os diretorios das imagens
	public void getPlayerImage() {

		up1 = setup("/player/toad-costas-1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/toad-costas-2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/toad-frente-1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/toad-frente-2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/toad-esquerda-1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/toad-esquerda-2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/toad-direita-1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/toad-direita-2", gp.tileSize, gp.tileSize);
	}
	
	public void getPlayerAttackImage() {
		
		ataqueUp1 = setup("/player/ataque-costas-1", gp.tileSize, gp.tileSize*2);
		ataqueUp2 = setup("/player/ataque-costas-2", gp.tileSize, gp.tileSize*2);
		ataqueDown1 = setup("/player/ataque-frente-1", gp.tileSize, gp.tileSize*2);
		ataqueDown2 = setup("/player/ataque-frente-2", gp.tileSize, gp.tileSize*2);
		ataqueLeft1 = setup("/player/ataque-esquerda-1", gp.tileSize*2, gp.tileSize);
		ataqueLeft2 = setup("/player/ataque-esquerda-2", gp.tileSize*2, gp.tileSize);
		ataqueRight1 = setup("/player/ataque-direita-1", gp.tileSize*2, gp.tileSize);
		ataqueRight2 = setup("/player/ataque-direita-2", gp.tileSize*2, gp.tileSize);
	}
	public void update() { // Atualiza as direções do player
		
		if (attacking == true) {
			attacking();
		} else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true || keyH.enterPressed == true) {

			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}

			// CHECANDO COLISÃO
			collisionOn = false;
			gp.cChecker.checkTile(this);

			// CHECANDO COLISÃO DO OBJETO
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			// CHECANDO COLISÃO DE NPC
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			// CHECANDO COLISÃO DE MONSTRO
			int monIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monIndex);

			// CHECANDO EVENTO
			gp.eHandler.checkEvent();


			// SE A COLISAO FOR FALSA, O JOGADOR PODE SE MOVER
			if (collisionOn == false && keyH.enterPressed == false) {

				switch (direction) {
				case "up":
					worldY -= velocidade;
					break;
				case "down":
					worldY += velocidade;
					break;
				case "left":
					worldX -= velocidade;
					break;
				case "right":
					worldX += velocidade;
					break;

				}
			}
			
			if (keyH.enterPressed == true && attackCanceled == false) {
				gp.playSE(14);
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCanceled = false;
			gp.keyH.enterPressed = false;
			
			spriteCounter++;
			if (spriteCounter > 10) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}

		// FORA DO KEY STATEMENT
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}

	}
	
	public void attacking() {
		
		spriteCounter++;
		
		if (spriteCounter <= 5) {
			spriteNum = 1;
		}
		if (spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			// SALVA CURRENT WORLDX, WORLDY, SOLIDAREA
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			// AJUSTANDO WORLDX/Y PARA O ATAQUE
			switch(direction) {
			case "up":
				worldY -= attackArea.height;
				break;
			case "down":
				worldY += attackArea.height;
				break;
			case "left":
				worldX -= attackArea.width;
				break;
			case "right":
				worldX += attackArea.width;
				break;
			}
			
			// ATTACKAREA VIRANDO SOLIDAREA
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			// CHECANDO COLISÃO DEPOIS DE ATUALIZAR WORLDX/Y E SOLIDAREA
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			
			// DEPOIS DE CHECAR A COLISÃO RESTAURA PARA A FORMA ORIGINAL
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
		
	}

	public void pickUpObject(int i) {

		if (i != 999) {

			String objectName = gp.obj[i].name;

			switch (objectName) {
			case "Chave":
				gp.playSE(10);
				chavePega++;
				gp.obj[i] = null;
				gp.ui.addMessage("Você pegou uma chave!!");
				break;
			case "Bau":
				if (chavePega > 0) {
					gp.obj[i] = null;
					gp.playSE(11);
					gp.ui.addMessage("Você abriu um baú!\n Recompensa: 1 Moeda");
					coin++;
					chavePega--;
				} else {
					gp.ui.addMessage("Você precisa de uma chave!");
				}

				if (bauAberto > 3) {
					gp.ui.gameFinished = true;
					gp.stopMusic();
					gp.playSE(6);
				}
				break;
			case "Cogumelo":
				cogumeloPego++;
				gp.playSE(8);
				maxLife += 4;
				life = maxLife;
				gp.obj[i] = null;
				gp.ui.addMessage("Parabéns você pegou um PowerUP - Cogumelo");
				break;
			case "Estrela":
				estrelaPega++;
				gp.playSE(2);
				velocidade += 2;
				gp.obj[i] = null;
				gp.ui.addMessage("Parabéns você pegou um PowerUP - Estrela");
				break;
			}
		}
	}

	public void interactNPC(int i) {
		
		if (gp.keyH.enterPressed == true) {
			
			if (i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}	
	}

	public void contactMonster(int i) {

		if (i != 999) {
			if (invincible == false) {
				gp.playSE(12);
				
				int damage = attack - gp.monster[i].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				
				life -= damage;
				invincible = true;
			}
		}
	}
	
	public void damageMonster(int i) {
		
		if (i != 999) {
			
			if (gp.monster[i].invincible == false) {
				
				gp.playSE(13);
				
				int damage = attack - gp.monster[i].defense;
				if(damage < 0) {
					damage = 0;
				}
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage + " de Dano!!");
				
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				if (gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
					gp.ui.addMessage("Matou o " + gp.monster[i].name + "!");
					gp.ui.addMessage("Exp " + gp.monster[i].exp);
					exp += gp.monster[i].exp;
					checkLevelUp();
				}
			}
		}
	}
	
	public void checkLevelUp() {
		
		if (exp >= nextLevelExp) {
			
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(15);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "Você agora está no nível: " + level + "\n Você ficou mais forte!";
		}
	}

	public void draw(Graphics2D g2) { // Define a atualização das imagens para ocorrer a animação

		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch (direction) {
		case "up":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
			} 
			if (attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) {
					image = ataqueUp1;
				}
				if ( spriteNum == 2) {
					image = ataqueUp2;
				}
			}
			break;
		case "down":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
			}
			if (attacking == true) {
				if (spriteNum == 1) {
					image = ataqueDown1;
				}
				if (spriteNum == 2) {
					image = ataqueDown2;
				}
			}
			break;
		case "left":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
			}
			if (attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) {
					image = ataqueLeft1;
				}
				if (spriteNum == 2) {
					image = ataqueLeft2;
				}
			}
			break;
		case "right":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
			}
			if (attacking == true) {
				if (spriteNum == 1) {
					image = ataqueRight1;
				}
				if (spriteNum == 2) {
					image = ataqueRight2;
				}
			}
			break;
		}

		if (invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		g2.drawImage(image, tempScreenX, tempScreenY, null);

		// RESETANDO O ALPHA COMPOSITE
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
     
	}

}
