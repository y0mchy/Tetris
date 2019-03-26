import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;

/**
 * 
 * スコアパネル出力クラス
 * 
 */

public class ScorePanel extends JPanel {
	/**
	 *  パネルサイズ
	 */
	public static final int WIDTH = 6 * Field.TILE_SIZE;
	public static final int HEIGHT = 1 * Field.TILE_SIZE;

	/**
	 *  スコア
	 */
	private static int score;
	
	/**
	 * スコアが最大ならばtrue
	 */
	public static boolean MAX = false;

	/**
	 * パネルサイズを指定
	 */
	public ScorePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	/**
	 * スコアを描画
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		// スコアを描画
		g.setColor(Color.YELLOW);
		DecimalFormat formatter = new DecimalFormat("000000");
		// フォントを作成
		Font font = new Font("Ariel", Font.BOLD, 16);
		g.setFont(font);
		if (MAX) {
			g.drawString(formatter.format(999999999), 7, 12);
		} else if (score > 99999999){
			g.drawString(formatter.format(score), 7, 12);
		} else if (score > 9999999){
			g.drawString(formatter.format(score), 12, 12);
		} else if (score > 999999){
			g.drawString(formatter.format(score), 16, 12);
		} else {
			g.drawString(formatter.format(score), 24, 12);
		}
	}

	/**
	 * スコアをセットする
	 *
	 * @param setscore スコア
	 */
	public void setScore(int setscore) {
		score = setscore;
		if (score == 0){
			MAX = false;
		} else if (score > 999999999){
			score = 999999999;
		}
		repaint();
	}

	/**
	 * スコアをプラスする
	 *
	 * @param d プラス分
	 */
	public void upScore(int d) {
		setScore(getScore() + d);
		if (getScore() >= 999999999){
			MAX = true;
		}
		repaint();
	}

	/**
	 * スコアを返す
	 * 
	 * @return score スコア
	 */
	public static int getScore() {
		return score;
	}
}
