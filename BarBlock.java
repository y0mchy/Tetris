/**
 * 
 * BarBlockの設定クラス
 * 
 */

public class BarBlock extends Block {
	/**
	 * BarBlockの形状・色を指定
	 * 
	 * @param field フィールドへの参照
	 */
    public BarBlock(Field field) {
        super(field);

        // □□□□
        // □■□□
        // □■□□
        // □■□□
        block[1][1] = 1;
        block[2][1] = 1;
        block[3][1] = 1;

        imageNo = Block.BAR;
    }
}
