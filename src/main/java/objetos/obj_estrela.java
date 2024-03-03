package objetos;

import entity.Entity;
import main.GamePanel;

public class obj_estrela extends Entity {

	public obj_estrela(GamePanel gp) {
		super(gp);

		name = "Estrela";
		setup("/objects/estrela", gp.tileSize, gp.tileSize);
	}
}
