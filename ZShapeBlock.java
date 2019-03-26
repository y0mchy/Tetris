/**
 * 
 * ZShapeBlockの設定クラス
 * 
 */

public class ZShapeBlock extends Block {
	/**
	 * ZShapeBlockの形状・色を指定
	 * 
	 * @param field フィールドへの参照
	 */
    public ZShapeBlock(Field field) {
        super(field);

        // □□□□
        // □□■□
        // □■□□
        // □■□□
        block[1][2] = 1;
        block[2][1] = 1;
        block[3][1] = 1;

        imageNo = Block.Z_SHAPE;
    }
}
