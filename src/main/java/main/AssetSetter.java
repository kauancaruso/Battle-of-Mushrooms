package main;

import entity.NPC_vovo;
import monstro.mon_esqueleto;
import objetos.obj_bau;
import objetos.obj_chave;
import objetos.obj_cogumelo;
import objetos.obj_estrela;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {

		// CHAVE LESTE
		gp.obj[0] = new obj_chave(gp);
		gp.obj[0].worldX = 26 * gp.tileSize;
		gp.obj[0].worldY = 59 * gp.tileSize;

		// CHAVE NORTE
		gp.obj[1] = new obj_chave(gp);
		gp.obj[1].worldX = 51 * gp.tileSize;
		gp.obj[1].worldY = 30 * gp.tileSize;

		// CHAVE SUL
		gp.obj[2] = new obj_chave(gp);
		gp.obj[2].worldX = 51 * gp.tileSize;
		gp.obj[2].worldY = 80 * gp.tileSize;

		// CHAVE OESTE
		gp.obj[3] = new obj_chave(gp);
		gp.obj[3].worldX = 82 * gp.tileSize;
		gp.obj[3].worldY = 59 * gp.tileSize;

		// BAUS
		gp.obj[4] = new obj_bau(gp);
		gp.obj[4].worldX = 81 * gp.tileSize;
		gp.obj[4].worldY = 23 * gp.tileSize;

		gp.obj[5] = new obj_bau(gp);
		gp.obj[5].worldX = 78 * gp.tileSize;
		gp.obj[5].worldY = 79 * gp.tileSize;

		gp.obj[6] = new obj_bau(gp);
		gp.obj[6].worldX = 17 * gp.tileSize;
		gp.obj[6].worldY = 82 * gp.tileSize;

		gp.obj[7] = new obj_bau(gp);
		gp.obj[7].worldX = 20 * gp.tileSize;
		gp.obj[7].worldY = 25 * gp.tileSize;

		// COGUMELO
		gp.obj[8] = new obj_cogumelo(gp);
		gp.obj[8].worldX = 20 * gp.tileSize;
		gp.obj[8].worldY = 85 * gp.tileSize;

		// ESTRELA
		gp.obj[9] = new obj_estrela(gp);
		gp.obj[9].worldX = 86 * gp.tileSize;
	}

	public void setNPC() {

		gp.npc[0] = new NPC_vovo(gp);
		gp.npc[0].worldX = gp.tileSize * 47;
		gp.npc[0].worldY = gp.tileSize * 45;

	}

	public void setMonstro() {
		
		int i = 0;
		gp.monster[i] = new mon_esqueleto(gp);
		gp.monster[i].worldX = gp.tileSize * 12;
		gp.monster[i].worldY = gp.tileSize * 81;
		i++;
		
		gp.monster[i] = new mon_esqueleto(gp);
		gp.monster[i].worldX = gp.tileSize * 15;
		gp.monster[i].worldY = gp.tileSize * 81;
		i++;
		
		gp.monster[i] = new mon_esqueleto(gp);
		gp.monster[i].worldX = gp.tileSize * 17;
		gp.monster[i].worldY = gp.tileSize * 81;
		i++;
		
		gp.monster[i] = new mon_esqueleto(gp);
		gp.monster[i].worldX = gp.tileSize * 19;
		gp.monster[i].worldY = gp.tileSize * 81;
		i++;
		
		gp.monster[i] = new mon_esqueleto(gp);
		gp.monster[i].worldX = gp.tileSize * 22;
		gp.monster[i].worldY = gp.tileSize * 81;
		i++;
		
		gp.monster[i] = new mon_esqueleto(gp);
		gp.monster[i].worldX = gp.tileSize * 21;
		gp.monster[i].worldY = gp.tileSize * 81;
		i++;
		
		gp.monster[i] = new mon_esqueleto(gp);
		gp.monster[i].worldX = gp.tileSize * 24;
		gp.monster[i].worldY = gp.tileSize * 81;
		i++;
		
		gp.monster[i] = new mon_esqueleto(gp);
		gp.monster[i].worldX = gp.tileSize * 24;
		gp.monster[i].worldY = gp.tileSize * 80;
		i++;

	}
}
