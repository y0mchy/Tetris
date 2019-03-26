/**
 * 
 * ReverseZShapeBlockの設定クラス
 * 
 */

public class ReverseZShapeBlock extends Block {
	/**
	 * ReverseZShapeBlockの形状・色を指定
	 * 
	 * @param field フィールドへの参照
	 */
    public ReverseZShapeBlock(Field field) {
        super(field);

        // □□□□
        // □■□□
        // □■□□
        // ■□■□
        block[1][1] = 1;
        block[2][1] = 1;
        block[3][0] = 1;
        block[3][2] = 1;

        imageNo = Block.REVERSE_Z_SHAPE;
    }
}
