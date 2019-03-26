import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * 
 * ブロック出力クラス
 * 
 */

public class Block {
    /**
     *  ブロックのサイズ
     */
    public static final int ROW = 4;
    public static final int COL = 4;

    /**
     *  1マスのサイズ
     */
    private static final int TILE_SIZE = Field.TILE_SIZE;

    /**
     *  移動方向
     */
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int DROP = 3;

    /**
     *  ブロックの名前
     */
    public static final int BAR = 0;
    public static final int Z_SHAPE = 1;
    public static final int SQUARE = 2;
    public static final int L_SHAPE = 3;
    public static final int REVERSE_Z_SHAPE = 4;
    public static final int T_SHAPE = 5;
    public static final int REVERSE_L_SHAPE = 6;
    public static final int TROUGH = 7;
    public static final int WALL = 7;

    /**
     *  ブロックの形を格納
     */
    protected int[][] block = new int[ROW][COL];

    /**
     *  ブロックのイメージ番号
     */
    protected int imageNo;

    /**
     *  位置（単位：マス）
     */
    protected Point pos; // 一番左上の座標(pos)をマスの基準とする

    /**
     *  フィールドへの参照
     */
    protected Field field;

    /**
     * ブロックの初期化
     * @param field フィールドへの参照
     */
    public Block(Field field) {
        this.field = field;

        init();

        imageNo = 6;
        pos = new Point(4, -4);
    }

    /**
     * ブロック配列の初期化
     */
    public void init() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                block[i][j] = 0;
            }
        }
    }

    /**
     * ブロックとゴーストブロックの描画
     *
     * @param g 描画オブジェクト
     * @param blockImage ブロックイメージ
     * @param ghostImage ゴーストイメージ
     */
    public void draw(Graphics g, Image blockImage, Image ghostImage) {
    	Point newPos = new Point(pos.x, pos.y);
        for (int k = 0; k < Field.ROW; k++) {
        	// どこで固定されるか探索 (ゴーストブロック用)
        	newPos.y += 1;
        	if (field.isMovable(newPos, block) == false) {
        		newPos.y -= 1;
        		break;
        	}
        }
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, COL, ROW);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (block[i][j] == 1) {
                	//ゴーストブロックの描画
                	g.drawImage(ghostImage, (pos.x+j) * TILE_SIZE, (newPos.y+i) * TILE_SIZE,
                			(pos.x+j) * TILE_SIZE + TILE_SIZE, (newPos.y+i) * TILE_SIZE + TILE_SIZE,
                				imageNo * TILE_SIZE, 0, imageNo * TILE_SIZE + TILE_SIZE, TILE_SIZE, null);

                	if (MainPanel.del1) {
						// ブロックの描画
                		g.drawImage(blockImage, (pos.x + j) * TILE_SIZE, (pos.y + i) * TILE_SIZE,
								(pos.x + j) * TILE_SIZE + TILE_SIZE, 0,
								imageNo * TILE_SIZE, 0, imageNo * TILE_SIZE + TILE_SIZE, TILE_SIZE - 1, null);

                	} else {
						g.drawImage(blockImage, (pos.x + j) * TILE_SIZE, (pos.y + i) * TILE_SIZE,
								(pos.x + j) * TILE_SIZE + TILE_SIZE, (pos.y + i) * TILE_SIZE + TILE_SIZE - 1,
								imageNo * TILE_SIZE, 0, imageNo * TILE_SIZE + TILE_SIZE, TILE_SIZE - 1, null);
					}
					//                	g.drawImage(blockImage, (pos.x+j) * TILE_SIZE, (pos.y+i) * TILE_SIZE,
//                			(pos.x+j) * TILE_SIZE + TILE_SIZE -1, 0,
//                			imageNo * TILE_SIZE, 0, imageNo * TILE_SIZE + TILE_SIZE, TILE_SIZE-1, null);                	if (pos.y != newPos.y){ // 完全に重なった時は表示しない
//                	}
                }
            }
        }
    }

    /**
     * ネクストブロックパネル内にブロックを描画
     * @param g 描画オブジェクト
     * @param blockImage ブロックイメージ
     */
    public void drawInPanel(Graphics g, Image blockImage) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (block[i][j] == 1) {
                    g.drawImage(blockImage, (j+1) * TILE_SIZE + TILE_SIZE, (i+1) * TILE_SIZE + TILE_SIZE,
                            (j+1)*TILE_SIZE+2*TILE_SIZE, (i+1)*TILE_SIZE+2*TILE_SIZE,
                            imageNo * TILE_SIZE, 0, imageNo * TILE_SIZE + TILE_SIZE, TILE_SIZE, null);
                }
            }
        }
    }

    /**
     * 次のブロックパネル内にブロックを描画
     * @param g 描画オブジェクト
     * @param blockImage ブロックイメージ
     */
    public void drawInPanelHold(Graphics g, Image blockImage) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (block[i][j] == 1) {
                    g.drawImage(blockImage, (j+1) * TILE_SIZE + TILE_SIZE, (i+1) * TILE_SIZE + 10 * TILE_SIZE,
                            (j+1)*TILE_SIZE+2*TILE_SIZE, (i+1)*TILE_SIZE+TILE_SIZE+ 10 * TILE_SIZE,
                            imageNo * TILE_SIZE, 0, imageNo * TILE_SIZE + TILE_SIZE, TILE_SIZE, null);
                }
            }
        }
    }

    /**
     * dirの方向にブロックを移動
     *
     * @param dir 方向
     * @return フィールドに固定されたらtrueを返す
     */
    public boolean move(int dir) {
        switch (dir) {
            case LEFT : // 左方向に移動
                Point newPos = new Point(pos.x - 1, pos.y);
                if (field.isMovable(newPos, block)) { // 衝突しなければ位置を更新
                    pos = newPos;
                }
                break;
            case RIGHT : // 右方向に移動
                newPos = new Point(pos.x + 1, pos.y);
                if (field.isMovable(newPos, block)) {
                    pos = newPos;
                }
                break;
            case DOWN : // 下方向に移動
                newPos = new Point(pos.x, pos.y + 1);
//                if (imageNo == TROUGH){
//                	int a = field.zerosearch(pos.x);
//                	if (a != newPos.y) {
//						break;
//					}
//                }
                if (field.isMovable(newPos, block)) {
                    pos = newPos;
                } else { // 移動できない＝他のブロックとぶつかる＝固定する
                    // ブロックをフィールドに固定する
                    field.fixBlock(pos, block, imageNo);
                    // 固定されたらtrueを返す
                    return true;
                }
                break;
            case DROP : // 固定されるところまで落とす
                for (int i = 0; i < Field.ROW; i++){
                	// どこで固定されるか探索
                	newPos = new Point(pos.x, pos.y + i);
                	if (field.isMovable(newPos, block) == false) {
                		newPos.y -= 1;
                    	pos = newPos;
                    	// 移動できない＝他のブロックとぶつかる＝固定する
                        // ブロックをフィールドに固定する
                        field.fixBlock(pos, block, imageNo);
                        // 固定されたらtrueを返す
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    /**
     * ブロックを回転する
     */
    public void turn() {
        int[][] turnedBlock = new int[ROW][COL];

        // 回転したブロック
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                turnedBlock[j][ROW - 1 - i] = block[i][j];
            }
        }

        // 回転可能か調べる
        if (field.isMovable(pos, turnedBlock)) {
            block = turnedBlock;
        }
    }

    /**
     * ブロックを逆回転する
     */
    public void cturn() {
        int[][] turnedBlock = new int[ROW][COL];

        // 回転したブロック
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
            	turnedBlock[ROW - 1 - j][i] = block[i][j];
            }
        }

        // 回転可能か調べる
        if (field.isMovable(pos, turnedBlock)) {
            block = turnedBlock;
        }
    }
}
