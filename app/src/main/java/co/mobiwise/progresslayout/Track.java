/*
* Copyright (C) 2015 Mert Şimşek
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package co.mobiwise.progresslayout;

public class Track {

  private int trackId;
  private String songName;
  private String singerName;
  private int durationInSec;
  private boolean isPlaying = false;

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
