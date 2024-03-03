package objetos;

import entity.Entity;
import main.GamePanel;

public class obj_coracao extends Entity {

	public obj_coracao(GamePanel gp) {
		super(gp);

		name = "Coracao";
		image = setup("/objects/coracao-full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/coracao-metade", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/coracao-vazio", gp.tileSize, gp.tileSize);

	}

}
