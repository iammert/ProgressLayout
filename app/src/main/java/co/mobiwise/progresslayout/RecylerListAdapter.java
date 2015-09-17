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

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.mobiwise.library.ProgressLayoutListener;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import co.mobiwise.library.ProgressLayout;

public class RecylerListAdapter extends RecyclerView.Adapter<RecylerListAdapter.ViewHolder> {

  /**
   * Track list
   */
  private List<Track> trackList;

  /**
   * need to keep current playing.
   */
  private Track currentTrack;

  /**
   * Current duration
   */
  private int currentDuration = 0;

  /**
   * is currently playing
   */
  private boolean isPlaying = false;

  private static final int SECOND_MS = 1000;

  /**
   * needed handler for recyclerview
   */
  private Handler mHandler = new Handler();

  /**
   * count up second
   */
  private final Runnable mRunnable = new Runnable() {
    @Override public void run() {
      currentDuration += 1;
      mHandler.postDelayed(mRunnable, SECOND_MS);
    }
  };

  /**
   * set track list
   */
  public void setTrackList(List<Track> trackList) {
    this.trackList = trackList;
    notifyDataSetChanged();
  }

  /**
   * Create View holder
   */
  @Override public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view =
        LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
  }

  /**
   * I did little bit trick on this method
   * Normally ProgressLayout library already has runnable to update
   * current second and current progress. But with ViewHolder pattern
   * when we scroll down or up, viewholder objects which we created before
   * are using again. That is why i keep some data to check whether item is playing
   * or not.
   */
  @Override public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

    final Track track = trackList.get(i);

    viewHolder.textViewDuration.setText(calculateSongDuration(track.getDurationInSec()));
    viewHolder.textViewSong.setText(track.getSongName());
    viewHolder.textViewSinger.setText(track.getSingerName());
    viewHolder.imageViewAction.setBackgroundResource(R.drawable.play);
    viewHolder.progressLayout.setMaxProgress(track.getDurationInSec());

    if (currentTrack != null && currentTrack == track) {
      viewHolder.imageViewAction.setBackgroundResource(
          isPlaying ? R.drawable.pause : R.drawable.play);
      viewHolder.progressLayout.setCurrentProgress(currentDuration);
      if (isPlaying) viewHolder.progressLayout.start();
    } else {
      viewHolder.progressLayout.cancel();
    }

    viewHolder.imageViewAction.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        if (track != currentTrack) {
          currentTrack = track;
          mHandler.removeCallbacks(mRunnable);
          currentDuration = 0;
        }

        if (!viewHolder.progressLayout.isPlaying()) {
          isPlaying = true;
          viewHolder.progressLayout.start();
          mHandler.postDelayed(mRunnable, 0);
          viewHolder.imageViewAction.setBackgroundResource(R.drawable.pause);
          notifyDataSetChanged();
        } else {
          isPlaying = false;
          viewHolder.progressLayout.stop();
          mHandler.removeCallbacks(mRunnable);
          viewHolder.imageViewAction.setBackgroundResource(R.drawable.play);
          notifyDataSetChanged();
        }
      }
    });
    viewHolder.progressLayout.setProgressLayoutListener(new ProgressLayoutListener() {
      @Override public void onProgressCompleted() {
        viewHolder.imageViewAction.setBackgroundResource(R.drawable.play);
      }

      @Override public void onProgressChanged(int seconds) {
        viewHolder.textViewDuration.setText(calculateSongDuration(seconds));
      }
    });
  }

  /**
   * List count
   */
  @Override public int getItemCount() {
    return trackList.size();
  }

  /**
   * Calculate in string (hh:mm)
   */
  private String calculateSongDuration(int seconds) {
    return new StringBuilder(String.valueOf(seconds / 60))
        .append(":")
        .append(String.valueOf(seconds % 60))
        .toString();
  }

  /**
   * ViewHolder
   */
  public static class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.imageviewAction) ImageView imageViewAction;
    @Bind(R.id.progressLayout) ProgressLayout progressLayout;
    @Bind(R.id.textviewSong) TextView textViewSong;
    @Bind(R.id.textviewSinger) TextView textViewSinger;
    @Bind(R.id.textviewDuration) TextView textViewDuration;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

  }
}
