package objetos;

import entity.Entity;
import main.GamePanel;

public class obj_bau extends Entity {

	public obj_bau(GamePanel gp) {
		super(gp);

		name = "Bau";
		down1 = setup("/objects/bauf", gp.tileSize, gp.tileSize);
		collision = true;

		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
