# ProgressLayout
Custom Progress Layout for Android

[![Join the chat at https://gitter.im/iammert/ProgressLayout](https://img.shields.io/badge/GITTER-join%20chat-green.svg)](https://gitter.im/iammert/ProgressLayout)

[Here is the youtube link](https://www.youtube.com/watch?v=emDViR9g5H4)

![](https://raw.githubusercontent.com/iammert/ProgressLayout/master/art/progress_layout_art.png "")

# XML Definition

```xml
 <co.mobiwise.library.ProgressLayout
        android:id="@+id/progressLayout"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        app:maxProgress="100"
        app:autoProgress="true"
        app:emptyColor="#152430"
        android:layout_centerVertical="true"/>
```

# Attributes to use
```xml
<co.mobiwise.library.ProgressLayout
...
app:maxProgress="100"
app:autoProgress="true"
app:emptyColor="#152430"
app:loadedColor="#11FFFFFF"
...
/>
```

# Use
```java
ProgressLayout progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);
//Start it
progressLayout.start();
//pause it
progressLayout.stop();
//cancel it
progressLayout.cancel();
```

# Methods
```java
progressLayout.setMaxProgress(120);
progressLayout.setCurrentProgress(64);
boolean isPlaying = progressLayout.isPlaying();
//If you dont want to auto progress and handle it yourself
progressLayout.setAutoProgress(false);
```

# Listener
```java
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
```

# Design

I inspired from [this design](https://www.materialup.com/posts/android-player-playlist) which is designed by [Anatoly Nesterov](https://twitter.com/@Monadiform)

License
--------


    Copyright 2015 Mert Şimşek.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


