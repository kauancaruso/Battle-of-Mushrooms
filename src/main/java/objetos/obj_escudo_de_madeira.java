package objetos;

import entity.Entity;
import main.GamePanel;

public class obj_escudo_de_madeira extends Entity{

	public obj_escudo_de_madeira(GamePanel gp) {
		super(gp);

		name = "Escudo de Madeira";
		down1 = setup("/objects/escudo", gp.tileSize, gp.tileSize);
		defenseValue = 2;
	}

}
