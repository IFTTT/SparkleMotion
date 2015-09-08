[![Open Source at IFTTT](http://ifttt.github.io/images/open-source-ifttt.svg)](http://ifttt.github.io)

![Sparkle Motion](./art/sparklemotion-title.png)

Sparkle Motion is a ViewPager animator that animates Views within pages as well as views across pages.

![Sparkle Motion](./art/sparklemotion.gif)

## Overview
Sparkle Motion is an animation library dedicated to animate ViewPager elements. It uses ViewPager's [PageTransformer](http://developer.android.com/reference/android/support/v4/view/ViewPager.PageTransformer.html) to control the progress of the animations, so that the animated Views respond to the scrolling, and thus provides an interactive effect.

Sparkle Motion also supports cross page animations, meaning that you can animate Views outisde of ViewPager based on the ViewPager's scrolling, thus achieve cross-page animations. This is done through `Decor` and `SparkleViewPagerLayout`. By using them, you can specify Views that you want to animate through multiple pages, and the animations on them will be controlled by the OnPageChangeListener.


## Usage
Add Sparkle Motion as dependency via Gradle:

```
compile 'com.ifttt:sparklemotion:1.0'
```

## JazzHands and RazzleDazzle
Looking for libraries to build awesome keyframe animations like Sparkle Motion on iOS? Check out [`JazzHands`](https://github.com/IFTTT/JazzHands) and [`RazzleDazzle`](https://github.com/IFTTT/RazzleDazzle).

## ViewPager Animations
To add an animation to a View using Sparkle Motion,

```java
ViewPager viewPager = (ViewPager) findViewById(/* view_pager_id */);
AlphaAnimation alphaAnimation = new AlphaAnimation(Page.allPages(), 0f, 1f);
SparkleMotion.with(viewPager)
		 .animate(mAnimation)
		 .on(R.id.view_id)
```

where `R.id.view_id` is the id of the View inside the page Views. `Page.allPages()` tells Sparkle Motion that this animation should be run for all pages that has the View `R.id.view_id` (See [below](#page) for details about Page). You can also use `Animation.ANIMATION_ID_PAGE` instead of specific View id to apply this animation to the page View itself.

## Cross Page Animations 
Animations that require to be animated across different pages needs to be run on `Decor`, which is an element within `SparkleViewPagerLayout`. 

### SparkleViewPagerLayout
`SparkleViewPagerLayout` is a custom FrameLayout that controls `Decor` objects to play ViewPager cross page animations. **To use this layout with SparkleMotion, you need to supply a ViewPager as a child View**. This can be done in layout xml or through `addView()` method. 

Note that you should only have one ViewPager child in this layout.

Example for layout xml,

```xml
<com.ifttt.sparklemotion.SparkleViewPagerLayout
        android:id="@+id/view_pager_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v4.view.ViewPager
            android:id="@+id/view_pager"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

</com.ifttt.sparklemotion.SparkleViewPagerLayout>

```

### Decor
A `Decor` is a component that holds information about a View that should be controlled by the ViewPager and animates when there is at least one `Animation` associated.


Important attributes of a `Decor`:

* `contentView`: required element of a `Decor`, a View that should be used to animate across pages; 
* `startPage`: indicates starting from which page the `Decor` should be presented; 
* `endPage`: indicates on which page the `Decor` should be removed from the `SparkleViewPagerLayout`;
* `layoutBehindViewPage`: whether or not the `Decor` should be drawn behind the ViewPager within `SparkleViewPagerLayout`.
* `slideOut`: indicates this Decor will be scrolled along with the ViewPager at the end of its range, instead of setting visibility to `GONE`.

When there are more than one `Decor` in the layout, the drawing order of the content Views are based on the order that they are added through `SparkleMotion` or `SparkleViewPagerLayout.addDecor(Decor decor)`.

To build a `Decor`, simply use `Decor.Builder`.

To assign an animation to Decor, in your Activity, for example, 

```java
SparkleViewPagerLayout viewPagerLayout = (SparkleViewPagerLayout) findViewById(/* view_pager_id */);
View contentView = new View(this);

AlphaAnimation alphaAnimation = new AlphaAnimation(Page.allPages(), 0f, 1f);

Decor decor = new Decor.Builder()
		 .setContentView(contentView)
		 .build();
		 
SparkleMotion.with(viewPagerLayout)
		 .animate(alphaAnimation)
		 .on(decor);
```

a `Decor` will then be added to your `SparkleViewPagerLayout`, which will run the `alphaAnimation` during ViewPager scrolling.

As mentioned above, a `Decor.Builder` supports following methods,

```java
Decor decor = new Decor.Builder()
                .setContentView(View) // Content View of the Decor, must not be null
                
                .setPage(Page)        // Set the Page attribute for this Decor, default to Page.allPages()
                                
                .behindViewPage()     // Set to draw the content View behind the ViewPager
                
                .slideOut()           // Set to scroll with ViewPager after last visible page
                .build();
			
```

## Supported animations
* Basic View animations:
    * **Alpha**: animates the alpha property of the target Views.
    * **Rotation**: animates the rotation property of the target Views.
    * **Scale**: animates the scale X and/or Y properties of the target Views.
    * **Translation**: animates the translation X and/or Y properties of the target Views.
* **Path animation**: animates the target Views' translation X and Y so that it follows a [path](http://developer.android.com/reference/android/graphics/Path.html).
* **Parallax translation effect**: animates the target Views' translation X to the opposite direction of the ViewPager scrolling to achieve a paralax effect.

## Custom animations
Sparkle Motion also supports customized animations through extending `Animation` class. There are 3 methods in `Animation` class that you might be interested:

* `onAnimate(View v, float offset, float offsetInPixel)`: main method to override to provide customized animation. The `offset` value is ranged within [-1, 1]. **Note that for Decor animations, `offsetInPixel` will always be 0 as they are not part of the ViewPager and are not scrolled along with the ViewPager by default.** 
* `onAnimateOffScreenLeft(View v, float offset, float offsetInPixel)` (optional): this method will be called when `offset` < -1, which means the page is currently to the left of the screen.
* `onAnimateOffScreenRight(View v, float offset, float offsetInPixel)`(optional): this method will be called when `offset` > 1, which means the page is currently to the right of the screen.

The other two parameters, View `v` is the target View to be animated, `offsetInPixel` is the **entire page View's** scrolling offset in pixel, which might or might not be the same as `View.getWidth() * offset`.

<a name="page"></a>
## Page 
Both `Animation` and `Decor` have an attribute that ties to the index of the pages, controlling whether or not the animation should run or the Decor should be shown. In Sparkle Motion we use a class `Page` to control such attribute. 

`Page` provides 3 methods to return a Page object that controls the page attribute:

* `allPages()`: indicates the animation should be run for all pages, or the Decor should be shown across all pages.
* `singlePage(int)`: indicates the animation should be run on a specific page or the Decor should be shown on a specific page.
* `pageRange(int, int)`: indicates the animation should be run on a range of pages or the Decor should be shown on a range of pages.

## Sparkle Motion and PageTransformer
You can use Sparkle Motion instead of PageTransformer to play regular ViewPager page animations. One example is the `ZoomOutAnimation`, which takes the [PageTransformer implementation](http://developer.android.com/training/animation/screen-slide.html#pagetransformer) and implement as an Animation class. Simply apply this to the entire page to achieve the same effect.

If you need to have a custom PageTrasnformer for your ViewPager while using Sparkle Motion, you need to call `SparkleMotionCompat.setPageTransformer(ViewPager, boolean, PageTransformer)` to set your PageTransformer.


## Contributing

1. Fork it ( https://github.com/[my-github-username]/SparkleMotion/fork )
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create a new Pull Request

## License

`Sparkle Motion` is available under the MIT license. See the LICENSE file for more info.

Copyright 2015 IFTTT Inc.
