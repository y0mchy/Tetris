import java.applet.*;	//wavファイルの再生に使用

public class SoundTestWav {

    private AudioClip clip;

    public SoundTestWav(){
        //音源の読み込み
        clip = Applet.newAudioClip(getClass().getResource("se/kemono.wav"));
		    clip.play();	//音源の再生
    }

    public static void main(String[] args) {
        new SoundTestWav();
    }
}
