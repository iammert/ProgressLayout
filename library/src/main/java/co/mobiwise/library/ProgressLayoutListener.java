package co.mobiwise.library;

public interface ProgressLayoutListener {
  void onProgressCompleted();
  void onProgressChanged(int seconds);
}
