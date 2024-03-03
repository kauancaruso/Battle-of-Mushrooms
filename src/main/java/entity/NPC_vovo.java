package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_vovo extends Entity {

	public NPC_vovo(GamePanel gp) {
		super(gp);

		direction = "down";
		velocidade = 1;

		getImage();
		setDialogue();
	}

	// Busca os diretorios das imagens
	public void getImage() {

		up1 = setup("/npc/velho-costas-1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/velho-costas-2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/velho-frente-1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/velho-frente-2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/velho-esquerda-1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/velho-esquerda-2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/velho-direita-1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/velho-direita-2", gp.tileSize, gp.tileSize);
	}

	public void setDialogue() {

		dialogues[0] = "Olá, caro jogador.";
		dialogues[1] = "Seja Bem-Vindo ao Battle of Mushrooms!";
		dialogues[2] = "Você está a procura de um DESAFIO??";
		dialogues[3] = "Muito bem, para começar quero que você encontre as famosas \ncabeças da Ilha de Páscoa.";
		dialogues[4] = "Elas estão localizadas no canto direito desta ilha...";
		dialogues[5] = "Encontre elas e mais duas que estão restando, também procure por \nalgumas chaves e abra baús para uma nova conquista ao final.";
		dialogues[6] = "Boa sorte meu jovem, que os cogumelos estejam com você!";
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

	public void speak() {

		// Colocar falas especificas do personagem

		super.speak();
	}

}
