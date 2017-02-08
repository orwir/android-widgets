# android-widgets

Just another common widgets

[![License](https://raw.githubusercontent.com/novoda/novoda/master/assets/btn_apache_lisence.png)](LICENSE.txt)
[![Build Status](https://travis-ci.org/orwir/android-widgets.svg?branch=master)](https://travis-ci.org/orwir/android-widgets)
[![Download](https://api.bintray.com/packages/orwir/maven/android-widgets/images/download.svg) ](https://bintray.com/orwir/maven/android-widgets/_latestVersion)

### Usage

Add url to Bintray repository in your build.gradle
```groovy
allprojects {
    repositories {
        //... other urls
        maven { url  'http://dl.bintray.com/orwir/maven' }
    }
}
```


#ExpandableLayout
![expandable preview](https://gifyu.com/images/expandable_layout.gif)

Just a FrameLayout with header, expand/collapse functionality and written on RxAndroid and so on.
```groovy
compile 'orwir.android.widget:expandable:latest_version'
```
[Example layout](https://github.com/orwir/android-widgets/blob/master/app/src/main/res/layout/activity_expandable_layout.xml) and 
[enable/disable single expanded func](https://github.com/orwir/android-widgets/blob/master/app/src/main/java/orwir/widget/example/ExpandableLayoutActivity.java#L48)

