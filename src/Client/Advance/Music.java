package Client.Advance;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Music extends Thread {
    public final static int LOOP = 0;
    public final static int ONCE = 1;

    Player player;
    String music;
    int station;//播放一次或者单曲循环

    /**
     * 音乐播放类
     * @param file 音乐文件名
     * @param station 播放状态，循环或者播放一次
     */
    public Music(String file,int station) {
        this.music = System.getProperty("user.dir") + "/music/" + file;
        this.station = station;
    }

    public void run() {

        try {
            play();//播放
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }

        if(station == LOOP) {//如果单曲循环
            while (true) {
                if(player.isComplete()) {
                    System.out.println("play audio!");
                    try {
                        play();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void play() throws FileNotFoundException, JavaLayerException {
        BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
        player = new Player(buffer);
        player.play();
    }

//    public static void main(String[] args) {
//
//        Music p = new Music("ready.mp3",ONCE);
//        p.start();
//    }
}

