import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * 
 * ネクストブロックパネル出力クラス
 * 
 */

public class NextBlockPanel extends JPanel {
	/**
	 * パネルサイズ
	 */
	public static final int WIDTH = ScorePanel.WIDTH;
	public static final int HEIGHT = MainPanel.HEIGHT - ScorePanel.HEIGHT;

	/**
	 * 1マスのサイズ
	 */
	private final int TILE_SIZE = Field.TILE_SIZE;

	/**
	 * ネクストブロックへの参照
	 */
	private Block nextBlock;
	
	/**
	 * ホールドブロックへの参照
	 */
	private Block holdBlock;
	
	/**
	 * ブロックイメージ
	 */
	private Image blockImage;

	/**
	 * パネルサイズの指定
	 */
	public NextBlockPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	/**
	 * ネクストブロックとホールドブロックを描画
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// 次のブロックを描画
		if (nextBlock != null && ScorePanel.MAX != true) {
			nextBlock.drawInPanel(g, blockImage);
			// フォントを作成
			Font font1 = new Font("Ariel", Font.BOLD, 16);
			g.setFont(font1);
			g.setColor(Color.YELLOW);
			g.drawString("Next", 2*TILE_SIZE+TILE_SIZE/4, 2*TILE_SIZE+TILE_SIZE/8);
			// フォントを作成
			Font font2 = new Font("Ariel", Font.BOLD, 16);
			g.setFont(font2);
			g.setColor(Color.YELLOW);
			g.drawString("Hold", 2*TILE_SIZE+TILE_SIZE/4, 11*TILE_SIZE+TILE_SIZE/8);
			if (MainPanel.hold) {
				holdBlock.drawInPanelHold(g, blockImage);
			}
		}

		for (int y = 0; y < HEIGHT/TILE_SIZE; y++) {
			for (int x = 0; x < WIDTH/TILE_SIZE; x++) {
				if ((x < 1 || y < 1 || (6 < y && y < 10) || 15 < y ) || ScorePanel.MAX) {
					g.drawImage(blockImage, x * TILE_SIZE, y * TILE_SIZE, x
							* TILE_SIZE + TILE_SIZE, y * TILE_SIZE + TILE_SIZE,
							Block.WALL * TILE_SIZE, 0, Block.WALL
							* TILE_SIZE + TILE_SIZE, TILE_SIZE, null);
				}
			}
		}
	}

	/**
	 * 次のブロックをセット
	 *
	 * @param nextBlock ネクストブロック
	 * @param holdBlock ホールドブロック
	 * @param blockImage ブロックイメージ
	 */
	public void set(Block nextBlock, Block holdBlock, Image blockImage) {
		this.nextBlock = nextBlock;
		this.holdBlock = holdBlock;
		this.blockImage = blockImage;
		repaint();
	}
//	public void set(Block nextBlock, Image blockImage) {
//		this.nextBlock = nextBlock;
//		this.blockImage = blockImage;
//		repaint();
//	}
}
