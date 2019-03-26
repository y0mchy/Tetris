/**
 * 
 * ThroughBlockの設定クラス
 * 
 */

public class ThroughBlock extends Block {
	/**
	 * ThroughBlockの形状・色を指定
	 * 
	 * @param field フィールドへの参照
	 */
    public ThroughBlock(Field field) {
        super(field);

        // □□□□
        // □□□□
        // □■□□
        // □□□□
        block[2][1] = 1;

        imageNo = Block.T_SHAPE;
    }
}
