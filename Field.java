import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * 
 * ゲームフィールド出力クラス
 * 
 */

public class Field {
	/**
	 *  フィールドのサイズ（単位：マス）
	 */
	public static final int COL = 12;
	public static final int ROW = 21;

	/**
	 *  マスのサイズ
	 */
	public static final int TILE_SIZE = 16;

	/**
	 *  フィールド
	 */
	private int[][] field;
	/**
	 *  フィールドのイメージ
	 */
	private int[][] fieldImage;

	/**
	 * フィールドを初期化する
	 */
	public Field() {
		field = new int[ROW][COL];
		fieldImage = new int[ROW][COL];

		// フィールドを初期化
		init();
	}

	/**
	 * フィールド・フィールドイメージ配列を初期化する
	 */
	public void init() {
		for (int y = 0; y < ROW; y++) {
			for (int x = 0; x < COL; x++) {
				// 壁をつくる
				if (x == 0 || x == COL - 1) {
					field[y][x] = 1;
					fieldImage[y][x] = Block.WALL;
				} else if (y == ROW - 1) {
					field[y][x] = 1;
					fieldImage[y][x] = Block.WALL;
				} else {
					field[y][x] = 0;
				}
			}
		}
	}

	/**
	 * タイトル画面を描画
	 * 
	 * @param g 描画オブジェクト
	 * @param blockImage ブロックイメージ
	 * @param sideImage 画面右に表示させておく画像
	 */
	public void title(Graphics g, Image blockImage) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT);

		g.setColor(Color.LIGHT_GRAY);



		for (int y = 0; y < MainPanel.HEIGHT/TILE_SIZE; y++) {
			for (int x = 0; x < MainPanel.WIDTH/TILE_SIZE; x++) {
//				if (x < COL && y < ROW) {
//					if (field[y][x] == 1) {
//
//
//					g.drawImage(blockImage, x * TILE_SIZE, y * TILE_SIZE, x
//							* TILE_SIZE + TILE_SIZE, y * TILE_SIZE + TILE_SIZE,
//							fieldImage[y][x] * TILE_SIZE, 0, fieldImage[y][x]
//									* TILE_SIZE + TILE_SIZE, TILE_SIZE, null);
//					}
//				}else
					if (x < 1) {
					g.drawImage(blockImage, x * TILE_SIZE, y * TILE_SIZE, x
							* TILE_SIZE + TILE_SIZE, y * TILE_SIZE + TILE_SIZE,
							Block.WALL * TILE_SIZE, 0, Block.WALL
							* TILE_SIZE + TILE_SIZE, TILE_SIZE, null);

				}
			}
		}

//		g.drawImage(sideImage, 1 * TILE_SIZE, (ROW + 1) * TILE_SIZE,
//				(COL- 2)*TILE_SIZE, 13 * TILE_SIZE, null);

		// フォントを作成
		Font font1 = new Font("Ariel", Font.BOLD, 102);
		g.setFont(font1);
		g.setColor(Color.RED);
		g.drawString("Enterおしてみて", 10, 352);
	}

	/**
	 * フィールドを描画
	 * 
	 * @param g 描画オブジェクト
	 * @param blockImage ブロックイメージ
	 * @param sideImage 画面右に表示させておく画像
	 */
	public void draw(Graphics g, Image blockImage, Image sideImage, Image belowImage) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT);

		g.setColor(Color.LIGHT_GRAY);
		for (int y = 0; y < MainPanel.HEIGHT/TILE_SIZE; y++) {
			for (int x = 0; x < MainPanel.WIDTH/TILE_SIZE; x++) {
				if (x < COL && y < ROW) {
					if (field[y][x] == 1) {


					g.drawImage(blockImage, x * TILE_SIZE, y * TILE_SIZE, x
							* TILE_SIZE + TILE_SIZE, y * TILE_SIZE + TILE_SIZE,
							fieldImage[y][x] * TILE_SIZE, 0, fieldImage[y][x]
									* TILE_SIZE + TILE_SIZE, TILE_SIZE, null);
					}
				}else if (x < 1 || COL - 2 < x || y == ROW) {
					g.drawImage(blockImage, x * TILE_SIZE, y * TILE_SIZE, x
							* TILE_SIZE + TILE_SIZE, y * TILE_SIZE + TILE_SIZE,
							Block.WALL * TILE_SIZE, 0, Block.WALL
							* TILE_SIZE + TILE_SIZE, TILE_SIZE, null);

				}
			}
		}
		g.drawImage(belowImage, 1 * TILE_SIZE, MainPanel.HEIGHT - 12 * TILE_SIZE,
				(COL- 2)*TILE_SIZE, 12 * TILE_SIZE, null);
		g.drawImage(sideImage, (COL+0) * TILE_SIZE, 1 * TILE_SIZE,
				MainPanel.WIDTH - (COL + 10)*TILE_SIZE, MainPanel.HEIGHT - 2 * TILE_SIZE, null);
	}

	/**
	 * フィールドを描画 (ゲームオーバー・ポーズ・クリア時)
	 * 
	 * @param g 描画オブジェクト
	 * @param blockImage ブロックイメージ
	 * @param gpcImage 出力画像
	 */
	public void showImage(Graphics g, Image blockImage, Image gpcImage) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT);

		g.setColor(Color.LIGHT_GRAY);
		for (int y = 0; y < MainPanel.HEIGHT/TILE_SIZE; y++) {
			for (int x = 0; x < MainPanel.WIDTH/TILE_SIZE; x++) {
				if ((x < 1 ) || (x > MainPanel.WIDTH/TILE_SIZE - 2)
						|| (y < 1) || (y > MainPanel.HEIGHT/TILE_SIZE - 2)) {

					g.drawImage(blockImage, x * TILE_SIZE, y * TILE_SIZE, x
							* TILE_SIZE + TILE_SIZE, y * TILE_SIZE + TILE_SIZE,
							Block.WALL * TILE_SIZE, 0, Block.WALL
							* TILE_SIZE + TILE_SIZE, TILE_SIZE, null);

				}            }
		}
		g.drawImage(gpcImage, 2 * TILE_SIZE, 2 * TILE_SIZE,
				MainPanel.WIDTH - 4 * TILE_SIZE, MainPanel.HEIGHT - 4 * TILE_SIZE, null);
		switch (MainPanel.gameState) {
		case MainPanel.PAUSE:
			// フォントを作成
			Font font1 = new Font("Ariel", Font.BOLD, 202);
			g.setFont(font1);
			g.setColor(Color.RED);
			g.drawString("止", -20, 352);
			break;
		case MainPanel.GAMEOVER:
			// フォントを作成
			Font font2 = new Font("Ariel", Font.BOLD, 302);
			g.setFont(font2);
			g.setColor(Color.RED);
			g.drawString("終", 20, 422);
			break;
		case MainPanel.CLEAR:
			// フォントを作成
			Font font3 = new Font("Ariel", Font.BOLD, 102);
			g.setFont(font3);
			g.setColor(Color.RED);
			g.drawString("すっごーー", -10, 482);
			g.drawString("　　ーーーーい", -10, 552);
			break;
		}
		//		g.drawImage(gpcImage, TILE_SIZE, ROW * TILE_SIZE /3, COL * TILE_SIZE - TILE_SIZE * 2 - 1, ROW * TILE_SIZE /3, null);
	}

	/**
	 * ブロックを移動できるか調べる
	 *
	 * @param newPos ブロックの移動先座標
	 * @param block ブロック配列
	 * @return 移動できたらtrue
	 */
	public boolean isMovable(Point newPos, int[][] block) {
		// block=1のマスすべてについて衝突しているか調べる
		// どれか1マスでも衝突してたら移動できない
		for (int i = 0; i < Block.ROW; i++) {
			for (int j = 0; j < Block.COL; j++) {
				if (block[i][j] == 1) { // 4x4内でブロックのあるマスのみ調べる
					if (newPos.y + i < 0) { // そのマスが画面の上端外のとき
						// ブロックのあるマスが壁のある0列目以下または
						// COL-1列目以上に移動しようとしている場合は移動できない
						if (newPos.x + j <= 0 || newPos.x + j >= COL - 1) {
							return false;
						}
					} else if (field[newPos.y + i][newPos.x + j] == 1) {
						// フィールド内で
						// 移動先にすでにブロック（壁含む）がある場合は移動できない
						return false;
					}
				}
			}
		}

		return true;
	}

	//    /**
	//     * どこまで落ちれるかを探索
	//     *
	//     * @param a
	//     *            ブロックposのｘ座標
	//     * @return
	//     *            field[y][a]=0となる最も大きいyの値
	//     */
	//    public int zerosearch(int a) {
	//    	int tmp = 0;
	//        for (int i = 0; i < ROW; i++) {
	//            if (field[i][a] == 0){
	//            	tmp = i;
	//            }
	//        }
	//        return (tmp-1);
	//    }

	/**
	 * 落ちきったブロックをボードに固定する
	 *
	 * @param pos ブロックの位置
	 * @param block ブロック配列
	 */
	public void fixBlock(Point pos, int[][] block, int imageNo) {
		for (int i = 0; i < Block.ROW; i++) {
			for (int j = 0; j < Block.COL; j++) {
				if (block[i][j] == 1) {
					if (pos.y + i < 0) continue;
					field[pos.y + i][pos.x + j] = 1; // フィールドをブロックで埋める
					fieldImage[pos.y + i][pos.x + j] = imageNo;
				}
			}
		}
	}

	/**
	 * ブロックがそろった行を消去
	 *
	 * @return deleteLine 消した行数
	 */
	public int deleteLine() {
		int deleteLine = 0;  // 消した行数

		for (int y = 0; y < ROW - 1; y++) {
			int count = 0;
			for (int x = 1; x < COL - 1; x++) {
				// ブロックがある列の数を数える
				if (field[y][x] == 1) {
					count++;
				}
			}
			// そろった行が見つかった！
			if (count == Field.COL - 2) {
				deleteLine++;
				// その行を消去
				for (int x = 1; x < COL - 1; x++) {
					field[y][x] = 0;
				}
				// それより上の行を落とす
				for (int ty = y; ty > 0; ty--) {
					for (int tx = 1; tx < COL - 1; tx++) {
						field[ty][tx] = field[ty - 1][tx];
						fieldImage[ty][tx] = fieldImage[ty - 1][tx];
					}
				}
			}
		}

		return deleteLine;
	}

	/**
	 * ブロックが積み上がっているか
	 * 
	 * @return 最上行まで積み上がっていたらtrue
	 */
	public boolean isStacked() {
		for (int x=1; x<COL-1; x++) {
			if (field[0][x] == 1) {
				return true;
			}
		}
		return false;
	}
}
