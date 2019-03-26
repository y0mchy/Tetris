import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * メインパネル出力クラス
 *
 */

public class MainPanel extends JPanel implements KeyListener, Runnable {
	/**
	 * パネルサイズ
	 */
	//	public static final int WIDTH = 384;
	//	public static final int HEIGHT = 336;
	//	public static final int WIDTH = 192;
	public static final int WIDTH = (Field.COL+ 28) * Field.TILE_SIZE;
	public static final int HEIGHT = (Field.ROW+13) * Field.TILE_SIZE;

	/**
	 * スコア
	 */
	private static final int BLOCK_DOWN = 10000; // 下キーを押したとき+1
	private static final int BLOCK_ROTATE = 30000; // 回転したとき+1
	private static final int BLOCK_REROTATE = 300001; // 回転したとき+1
	private static final int BLOCK_DROP = 30000; // 回転したとき+1
	private static final int ONE_LINE = 100; // 1行消したとき
	private static final int TWO_LINE = 400; // 2行消したとき
	private static final int THREE_LINE = 1000; // 3行消したとき
	private static final int TETRIS = 2000; // 4行消した（テトリス）とき
	private static final int ESAQTA = 999000000;	// エサクタしたとき

	/**
	 * ゲーム状態変数
	 */
	private static final int START = 1;
	public static final int GAMEPLAY = 2;
	public static final int GAMEOVER = 3;
	public static final int PAUSE = 4;
	public static final int CLEAR = 5;

	/**
	 *  ゲーム状態変数
	 */
	public static int gameState = START;

	/**
	 *  デリート行数が1ならtrue
	 */
	public static boolean del1 = false;

	/**
	 *  落下速度
	 */
	protected int velocity = 200;
	/**
	 * 落下速度最大値
	 */
	protected int VEL_MAX = 800;

	/**
	 *  ゲームオーバー・ポーズ時の画像
	 */
	protected int imageNumber;
	/**
	 *  ゲームオーバー・ポーズ時の画像枚数
	 */
	protected int N = 5;

	/**
	 *  フィールド
	 */
	private Field field;
	/**
	 *  現在のブロック
	 */
	private Block block;
	/**
	 *  次のブロック
	 */
	private Block nextBlock;
	/**
	 * ホールドの有無
	 */
	public static boolean hold = false;
	/**
	 * ホールドしておくブロック
	 */
	private Block holdBlock;

	/**
	 *  ブロック画像
	 */
	private Image blockImage;
	/**
	 *  ゴースト画像
	 */
	private Image ghostImage;
	/**
	 *  ゲームオーバー・ポーズ時の画像
	 */
	private Image gpcImage;
	/**
	 * プレイ中に画面右側に表示する画像
	 */
	private Image sideImage;
	/**
	 *  プレイ中に画面下部に表示する画像
	 */
	private Image belowImage;

	/**
	 *  ゲームループ用スレッド
	 */
	private Thread gameLoop;

	/**
	 * 乱数
	 */
	private Random rand;

	/**
	 *  スコアパネルへの参照
	 */
	private ScorePanel scorePanel;
	/**
	 *  ネクストブロックパネルへの参照
	 */
	private NextBlockPanel nextBlockPanel;

	// サウンド
	private static String[] seNames = {"kachi42", "sugoi", "wc", "urusai"};
	private static String[] bgmNames = {"tetrisb", "op"};

	// 画像
	//	    private static String[] imageNames = {"image/1.jpg", "image/2.jpg"};


	/**
	 * メインパネル出力
	 *
	 * @param scorePanel スコアパネルへの参照
	 * @param nextBlockPanel ネクストブロックパネルへの参照
	 */
	public MainPanel(ScorePanel scorePanel, NextBlockPanel nextBlockPanel) {
		// パネルの推奨サイズを設定、pack()するときに必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// パネルがキー入力を受け付けるようにする
		setFocusable(true);

		this.scorePanel = scorePanel;
		this.nextBlockPanel = nextBlockPanel;

		// ブロックのイメージをロード
		loadImage("image/block.gif", "image/ghost.jpg", "image/side.png", "image/goto1.png");
		//		loadImage2("image/showImage.jpg");

		rand = new Random();
		rand.setSeed(System.currentTimeMillis());

		field = new Field();
		block = createBlock(field);
		nextBlock = createBlock(field);
		nextBlockPanel.set(nextBlock, holdBlock, blockImage);

		addKeyListener(this);

		//				try {
		//					// サウンドをロード
		//					WaveEngine.load("se/kachi42.wav");
		//				} catch (UnsupportedAudioFileException e1) {
		//					e1.printStackTrace();
		//				} catch (IOException e1) {
		//					e1.printStackTrace();
		//				} catch (LineUnavailableException e1) {
		//					e1.printStackTrace();
		//				}
		for (int i=0; i<seNames.length; i++) {
			try {
				// サウンドをロード
				WaveEngine.load("se/" + seNames[i] + ".wav");
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}

		for (int i = 0; i < bgmNames.length; i++) {
			try {
				// BGMをロード
				MidiEngine.load("bgm/"+ bgmNames[i] +".mid");
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ゲームループ開始
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	/**
	 * ゲームループ
	 */
	public void run() {

		while (true) {
			switch (gameState) {
			case START:
				// スタート画面BGM開始
				MidiEngine.play(1);
				break;
			case GAMEPLAY:
				// BGMスタート！
				MidiEngine.play(0);

				if (ScorePanel.MAX) {
					// 褒めてくれる
					WaveEngine.play(0);
					WaveEngine.render();
					//					int syohei = ScorePanel.getScore();
					//					ConnectServlet.transmitScore(syohei);
					
					gameState = CLEAR;
					// 再描画
					repaint();
					break;
				}
				// ブロックを下方向へ移動する
				// ブロックが固定されたらtrueが返される
				boolean isFixed = block.move(Block.DOWN);
				if (isFixed) { // ブロックが固定されたら
					// かちゃって鳴らす
					WaveEngine.play(0);
					// 次のブロックをセット
					block = nextBlock;
					// さらに次のブロックを作成してパネルに表示
					nextBlock = createBlock(field);
					nextBlockPanel.set(nextBlock, holdBlock, blockImage);
				}

				// ブロックがそろった行を消す
				// deleteLineは消した行数
				int deleteLine = field.deleteLine();

					// 消した行数に応じてスコアをプラスする
					if (deleteLine == 1) {
						del1 = true;
						scorePanel.upScore(ONE_LINE);
					} else if (deleteLine > 1) {
						del1 = false;
//						// トイレ音
//						WaveEngine.play(2);
						if (deleteLine == 2) {
							scorePanel.upScore(TWO_LINE);
						} else if (deleteLine == 3) {
							scorePanel.upScore(THREE_LINE);
						} else if (deleteLine == 4) {
							scorePanel.upScore(TETRIS);
						}
					} 
					
				// 行が消されるごとに落ちる速度を上げる
				if (deleteLine > 0 && velocity < VEL_MAX){
					velocity += 100;
				}

				// ゲームオーバーか
				if (field.isStacked()) {
					//					int syohei = ScorePanel.getScore();
					//					ConnectServlet.transmitScore(syohei);

					gameState = GAMEOVER;
					imageNumber = rand.nextInt(N);
				}

				// WAVEファイルのレンダリング
				WaveEngine.render();

				// 再描画
				repaint();

				break;
			default:
				break;
			}

			// ループ間隔
			try {
				Thread.sleep(velocity);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 描画メソッド
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		switch (gameState) {
		case START:
			field.title(g, blockImage);
			break;
		case GAMEPLAY:
			// フィールドを描画
			field.draw(g, blockImage, sideImage, belowImage);
			// ブロックとゴーストを描画
			block.draw(g, blockImage, ghostImage);
			break;
		case CLEAR:
			loadImage2("image/clear.jpg");
			// クリア画像を表示
			field.showImage(g, blockImage, gpcImage);
			break;
		default:
				loadImage2("image/" + imageNumber + ".jpg");
			//			loadImage2("image/gameover.jpg");
			// ポーズ画像を表示
			field.showImage(g, blockImage, gpcImage);
			break;
		}
	}

	//	public void keyTyped(KeyEvent e) {
	//	}

	/**
	 * キー入力時の動作
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (gameState) {
		case START:
			if (key == KeyEvent.VK_ENTER) { // ブロックを左へ移動
				gameState = GAMEPLAY;
				MidiEngine.stop();
			}
			break;
		case GAMEPLAY:
			if (key == KeyEvent.VK_LEFT) { // ブロックを左へ移動
				block.move(Block.LEFT);
			} else if (key == KeyEvent.VK_RIGHT) { // ブロックを右へ移動
				block.move(Block.RIGHT);
			} else if (key == KeyEvent.VK_DOWN) { // ブロックを下へ移動
				block.move(Block.DOWN);
				scorePanel.upScore(BLOCK_DOWN);
			} else if (key == KeyEvent.VK_UP) { // ブロックを回転
				block.turn();
				scorePanel.upScore(BLOCK_ROTATE);
			} else if (key == KeyEvent.VK_SPACE) { // ブロックを逆回転
				block.cturn();
				scorePanel.upScore(BLOCK_REROTATE);
			} else if (key == KeyEvent.VK_SHIFT) { // ブロックが固定されるところまで落とす
				block.move(Block.DROP);
				scorePanel.upScore(BLOCK_DROP);
			} else if (key == KeyEvent.VK_ENTER) { // ブロックが固定されるところまで落とす
				gameState = PAUSE;
				imageNumber = (imageNumber + 1) % N;
				if (imageNumber == 0) {
					scorePanel.upScore(ESAQTA);
				} else if (imageNumber == 2) {
//					// うるさい
//					WaveEngine.play(3);
//					WaveEngine.render();
				}
			} else if (key == KeyEvent.VK_SLASH) { // ブロックを右へ移動
				if (hold) {
					Block tmp = block;
					block = holdBlock;
					block.pos = new Point(4,-4);
					holdBlock = tmp;
					nextBlockPanel.set(nextBlock, holdBlock, blockImage);
				} else {
					holdBlock = block;
					holdBlock.pos = new Point(4,-4);
					block = nextBlock;
					nextBlock = createBlock(field);
					nextBlockPanel.set(nextBlock, holdBlock, blockImage);
					hold = true;
				}
			}
			break;
		case PAUSE:
			if (key == KeyEvent.VK_ENTER) { // ブロックを左へ移動
				gameState = GAMEPLAY;
			} else if (key == KeyEvent.VK_BACK_SPACE) {
				gameState = START;
				MidiEngine.stop();
			}
			break;
		default:
			if (key == KeyEvent.VK_ENTER) { // ブロックを左へ移動
				// スコアをリセット
				scorePanel.setScore(0);
				// フィールドをリセット
				field = new Field();
				block = createBlock(field);
				nextBlock = createBlock(field);
				hold = false;
				nextBlockPanel.set(nextBlock, holdBlock, blockImage);
				gameState = GAMEPLAY;
				break;
			} else if (key == KeyEvent.VK_BACK_SPACE) {
				gameState = START;
				MidiEngine.stop();
			}
		}

		repaint();
	}

	//	public void keyReleased(KeyEvent e) {
	//	}

	/**
	 * ランダムに次のブロックを作成
	 *
	 * @param field フィールドへの参照
	 * @return ランダムに生成されたブロック
	 */
	private Block createBlock(Field field) {
		int blockNo = rand.nextInt(8); // ブロックは0-7の8種類
		switch (blockNo) {
		case Block.BAR :
			return new BarBlock(field);
		case Block.Z_SHAPE :
			return new ZShapeBlock(field);
		case Block.SQUARE :
			return new SquareBlock(field);
		case Block.L_SHAPE :
			return new LShapeBlock(field);
		case Block.REVERSE_Z_SHAPE :
			return new ReverseZShapeBlock(field);
		case Block.T_SHAPE :
			return new TShapeBlock(field);
		case Block.REVERSE_L_SHAPE :
			return new ReverseLShapeBlock(field);
		case Block.TROUGH :
			return new ThroughBlock(field);
		}

		return null;
	}

	/**
	 * ブロックとゴーストと画面右に表示させておく画像をロード
	 *
	 * @param filename1 ブロック画像
	 * @param filename2 ゴースト画像
	 * @param filename3 画面右に表示させておく画像
	 * @param filename4 画面下に表示させておく画像
	 */
	private void loadImage(String filename1, String filename2, String filename3, String filename4) {
		// ブロックとゴーストのイメージを読み込む
		ImageIcon icon = new ImageIcon(getClass().getResource(filename1));
		ImageIcon icon_g = new ImageIcon(getClass().getResource(filename2));
		ImageIcon icon_s = new ImageIcon(getClass().getResource(filename3));
		ImageIcon icon_b = new ImageIcon(getClass().getResource(filename4));
		blockImage = icon.getImage();
		ghostImage = icon_g.getImage();
		sideImage = icon_s.getImage();
		belowImage = icon_b.getImage();
	}

	/**
	 * ゲームオーバー・ポーズ・クリア時の画像をロード
	 *
	 * @param filename 画像ファイル名
	 */
	private void loadImage2(String filename) {
		// ゲームオーバーのイメージを読み込む
		ImageIcon icon = new ImageIcon(getClass().getResource(filename));
		gpcImage = icon.getImage();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	//	@Override
	//	public void keyTyped(KeyEvent e) {
	//		// TODO 自動生成されたメソッド・スタブ
	//
	//	}
	//
	//	@Override
	//	public void keyReleased(KeyEvent e) {
	//		// TODO 自動生成されたメソッド・スタブ
	//
	//	}
}
