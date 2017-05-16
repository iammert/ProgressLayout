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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import co.mobiwise.library.ProgressLayout;
import co.mobiwise.library.ProgressLayoutListener;

public class BasicUsageActivity extends AppCompatActivity {
    private final String TAG = "BasicUsageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usage);
        final ProgressLayout progressLayout = ButterKnife.findById(this, R.id.progressLayout);
        progressLayout.setProgressLayoutListener(new ProgressLayoutListener() {
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
