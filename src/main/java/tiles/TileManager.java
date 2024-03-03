package tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {
		this.gp = gp;

		tile = new Tile[100];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
		loadMap("/maps/world02.txt");
	}

	public void getTileImage() {

		setup(7, "000", false);
		setup(6, "009", false);
		setup(17, "007", false);
		setup(22, "008", false);

		// AGUA
		setup(3, "001", true);
		setup(19, "004", true);
		setup(32, "006", true);
		setup(9, "005", true);

		// POÇOES
		setup(5, "013", false);

		// TOCO - ARVORE
		setup(24, "010", true);

		// COGUMELO
		setup(20, "011", true);

		// CHÃO
		setup(34, "014", false);

		// PAREDES
		setup(30, "012", true);
		setup(28, "002", true);

		// BAU
		setup(0, "100", false);

		// ARVORE
		setup(12, "040", true);
		setup(29, "041", true);
		setup(2, "042", true);
		setup(13, "043", true);
		setup(23, "044", true);
		setup(26, "045", true);

		// CABEÇÃO
		setup(8, "051", true);
		setup(1, "050", true);
		setup(10, "052", true);
		setup(4, "053", true);
		setup(11, "054", true);
		setup(21, "055", true);
	}

	public void setup(int index, String imageName, boolean collision) {

		UtilityTool uTool = new UtilityTool();

		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String line = br.readLine();

				while (col < gp.maxWorldCol) {

					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}

				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();

		} catch (Exception e) {

		}
	}

	public void draw(Graphics2D g2) {

		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[worldCol][worldRow];

			// CAMERA DO MAPA SEGUIR O PERSONAGEM - WORLD MAP
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
					&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
					&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
					&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}

			worldCol++;
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}

}
