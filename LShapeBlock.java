/**
 * 
 * LShapeBlockの設定クラス
 * 
 */

public class LShapeBlock extends Block {
	/**
	 * LShapeBlockの形状・色を指定
	 * 
	 * @param field フィールドへの参照
	 */
    public LShapeBlock(Field field) {
        super(field);

        // □□□□
        // □■■□
        // □□■□
        // □□□□
        block[1][1] = 1;
        block[1][2] = 1;
        block[2][2] = 1;

        imageNo = Block.L_SHAPE;
    }
}
