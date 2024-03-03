package objetos;

import entity.Entity;
import main.GamePanel;

public class obj_cogumelo extends Entity {

	public obj_cogumelo(GamePanel gp) {
		super(gp);

		name = "Cogumelo";
		down1 = setup("/objects/cogumelo", gp.tileSize, gp.tileSize);
	}
}
