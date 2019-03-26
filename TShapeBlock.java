/**
 * 
 * TShapeBlockの設定クラス
 * 
 */

public class TShapeBlock extends Block {
	/**
	 * TShapeBlockの形状・色を指定
	 * 
	 * @param field フィールドへの参照
	 */
    public TShapeBlock(Field field) {
        super(field);

        // □□□□
        // □■□□
        // ■■■□
        // □■□□
        block[1][1] = 1;
        block[2][0] = 1;
        block[2][1] = 1;
        block[2][2] = 1;
        block[3][1] = 1;

        imageNo = Block.T_SHAPE;
    }
}
