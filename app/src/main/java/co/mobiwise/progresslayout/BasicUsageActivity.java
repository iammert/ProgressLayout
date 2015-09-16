package co.mobiwise.progresslayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import co.mobiwise.library.ProgressLayout;

/**
 * Created by mertsimsek on 16/09/15.
 */
public class BasicUsageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usage);

        ProgressLayout progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);
        progressLayout.setProgressLayoutListener(new ProgressLayout.ProgressLayoutListener() {
            @Override
            public void onProgressCompleted() {
                //TODO completed
            }

            @Override
            public void onProgressChanged(int seconds) {
                //TODO progress seconds changed.
            }
        });
        progressLayout.start();
    }
}
