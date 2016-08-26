[TOC]

# Banner

![img](https://jitpack.io/v/aotian16/Banner.svg)

Android unlimited and auto scroll banner based on [Fresco](https://github.com/facebook/fresco).

# Image

![gif](https://github.com/aotian16/Blog/blob/master/Study/Dev/Android/android%E7%9A%84ViewPager%E5%AE%9E%E7%8E%B0%E5%8A%A0%E8%BD%BD%E7%BD%91%E7%BB%9C%E5%9B%BE%E7%89%87%E5%B9%B6%E8%87%AA%E5%8A%A8%E8%BD%AE%E6%92%AD/viewPager.gif?raw=true)

# How to use

### 0 Import

#### 0.1 By jitpack.io

```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

```
	dependencies {
	        compile 'com.github.aotian16:Banner:v1.0.2'
	}
```

#### 0.2 Or Download this project and import as a module

### 1 Init fresco in Application

```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this); // init Fresco
    }
}
```

### 2 Add net permission in AndroidManifest

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

### 3 Add BannerView in Layout

```xml
<com.qefee.pj.banner.view.BannerView
        android:id="@+id/bannerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

### 4 Run BnnerView in java

```java
public class MainActivity extends AppCompatActivity {
    /**
     * log tag for MainActivity
     */
    private static final String TAG = "MainActivity";

    BannerView bannerView;

    String[] imageUris = {
            "https://pic4.zhimg.com/03b2d57be62b30f158f48f388c8f3f33_b.png",
            "https://pic1.zhimg.com/4373a4f045e5e9ae16ebd6a624bf6228_b.png",
            "https://pic2.zhimg.com/0364e17a1561f48793993d8bf1cdc785_b.png",
            "https://pic2.zhimg.com/55fa74ff3eba164ed1db2037df1a8311_b.png",
            "https://pic4.zhimg.com/5dc30569c06e7c6266c9809f6eb80a7b_b.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerView = (BannerView) findViewById(R.id.bannerView);

        bannerView.init(imageUris.length, new SimpleDraweeViewHandler() {
            @Override
            public void handle(int index, SimpleDraweeView view) {
              // custom SimpleDraweeView here
                view.getHierarchy().setPlaceholderImage(R.mipmap.ic_launcher); // placeholder image
                view.setImageURI(imageUris[index]);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        bannerView.startAutoScroll(); // auto scroll when resume
    }

    @Override
    protected void onPause() {
        super.onPause();

        bannerView.stopAutoScroll(); // stop auto scroll when pause
    }
}
```

# Version

| No.  | Version | Detail                  |
| :--: | :-----: | ----------------------- |
|  1   |  1.0.0  | First version           |
|  2   |  1.0.1  | fix bug                 |
|  3   |  1.0.2  | support custom view     |
|  4   |  1.0.3  | update to gradle 2.14.1 |

# [LICENSE](https://github.com/aotian16/Banner/blob/master/LICENSE)

MIT