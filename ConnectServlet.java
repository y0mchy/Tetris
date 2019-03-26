import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

	/**
	 * アプレットのスコアをサーブレットに送る
	 * 
	 * @param score サーブレットに送信するスコア
	 */
public class ConnectServlet {
	  public static void transmitScore(int score) {
		  try {
              URL url = new URL("http://133.68.17.104:8080/yahage2/servlet/ConnectApplet");
              HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
              ucon.setRequestMethod("POST");
              ucon.setDoOutput(true);
              ucon.setDoInput(true);
              ucon.setUseCaches(false);

              System.out.println("送信準備");
              DataOutputStream dos = new DataOutputStream( ucon.getOutputStream());
              dos.writeInt(score);
              dos.flush();
              dos.close();

              ucon.connect(); //データ送信

              //サーブレットから返信を受け取る
              DataInputStream dis = new DataInputStream( ucon.getInputStream());
              String result = dis.readUTF();
              System.out.println(result);
              dis.close();

              ucon.disconnect();

          } catch ( Exception e){
              e.printStackTrace();
          }
	  }
}