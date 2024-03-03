package objetos;

import entity.Entity;
import main.GamePanel;

public class obj_chave extends Entity {

	public obj_chave(GamePanel gp) {
		super(gp);

		name = "Chave";
		down1 = setup("/objects/chave", gp.tileSize, gp.tileSize);

	}
}
