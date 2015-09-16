package co.mobiwise.progresslayout;

/**
 * Created by mertsimsek on 16/09/15.
 */
public class Track {

    private int trackId;
    private String songName;
    private String singerName;
    private int durationInSec;
    private boolean isPlaying = false;

    public Track() {
    }

    public Track(int trackId, String songName, String singerName, int durationInSec) {
        this.trackId = trackId;
        this.songName = songName;
        this.singerName = singerName;
        this.durationInSec = durationInSec;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getDurationInSec() {
        return durationInSec;
    }

    public void setDurationInSec(int durationInSec) {
        this.durationInSec = durationInSec;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
