package uet.oop.bomberman.utils;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameMediaPlayer {
  public static final String STAGE_START = "/background_music/stage_start.mp3";
  public static final String STAGE_THEME = "/background_music/stage_theme.mp3";
  public static final String STAGE_COMPLETE = "/background_music/stage_complete.mp3";
  public static final String LIFE_LOST = "/background_music/life_lost.mp3";
  public static final String GAME_OVER = "/background_music/game_over.mp3";
  public static final String ENDING = "/background_music/ending.mp3";

  public static final AudioClip explosion =
      new AudioClip(GameMediaPlayer.class.getResource("/sound_effects/explosion.wav").toString());
  public static final AudioClip walk =
          new AudioClip(GameMediaPlayer.class.getResource("/sound_effects/walk.wav").toString());
  public static final AudioClip powerUp =
          new AudioClip(GameMediaPlayer.class.getResource("/sound_effects/powerup.wav").toString());

  private static MediaPlayer mediaPlayer = null;

  //  public static final AudioClip explosion = new AudioClip();

  private static void releasePlayer() {
    if (mediaPlayer != null) {
      mediaPlayer.dispose();
      mediaPlayer = null;
    }
  }

  public static void playBackgroundMusic(String path, boolean repeatForever) {
    if (mediaPlayer != null) {
      releasePlayer();
    }

    Media mp3 = new Media(GameMediaPlayer.class.getResource(path).toString());
    mediaPlayer = new MediaPlayer(mp3);

    if (repeatForever) {
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    mediaPlayer.play();
  }

  public static void stopBackgroundMusic() {
    releasePlayer();
  }
}
