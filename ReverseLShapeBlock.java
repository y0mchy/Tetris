/**
 * 
 * ReverseLShapeBlockの設定クラス
 * 
 */

public class ReverseLShapeBlock extends Block {
	/**
	 * ReverseLShapeBlockBlockの形状・色を指定
	 * 
	 * @param field フィールドへの参照
	 */
    public ReverseLShapeBlock(Field field) {
        super(field);

        // □□□□
        // □■■□
        // □■□□
        // □□□□
        block[1][1] = 1;
        block[1][2] = 1;
        block[2][1] = 1;

        imageNo = Block.REVERSE_L_SHAPE;
    }
}
