/**
 * 
 * SquareBlockの設定クラス
 * 
 */

public class SquareBlock extends Block {
	/**
	 * SquareBlockの形状・色を指定
	 * 
	 * @param field フィールドへの参照
	 */
    public SquareBlock(Field field) {
        super(field);

        // □□□□
        // □■□□
        // □□■□
        // □□□□
        block[1][1] = 1;
        block[2][2] = 1;

        imageNo = Block.SQUARE;
    }

}
