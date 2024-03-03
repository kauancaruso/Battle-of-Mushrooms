package objetos;

import entity.Entity;
import main.GamePanel;

public class obj_espada_normal extends Entity {

	public obj_espada_normal(GamePanel gp) {
		super(gp);

		name = "Espada Normal";
		down1 = setup("/objects/espada", gp.tileSize, gp.tileSize);
		attackValue = 1;
	}

	
}
