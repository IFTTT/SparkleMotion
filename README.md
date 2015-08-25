# JazzHands-Android
A ViewPager animator that animates Views within pages as well as views across pages.


## Overview
JazzHands is an animation library dedicated to animate ViewPager elements. It uses ViewPager's [PageTransformer](http://developer.android.com/reference/android/support/v4/view/ViewPager.PageTransformer.html) to control the progress of the animations, so that the animated Views respond to the scrolling, and thus provides an interactive effect.

JazzHands also supports cross page animations, meaning that you can animate Views across different pages in ViewPager. This is done through `Decor` and `JazzHandsViewPagerLayout`. By using them, you can specify Views that you want to animate through multiple pages, and the animations on them will also be controlled by the PageTransformer.


## Usage

### ViewPager animations
To add an animation to a View within `JazzHandsViewPager`, 

```java
ViewPager viewPager = (ViewPager) findViewById(/* view_pager_id */);
AlphaAnimation alphaAnimation = new AlphaAnimation(Animation.ALL_PAGES, 0f, 1f);
JazzHands.with(viewPager)
		 .animate(mAnimation)
		 .on(Animation.ANIMATION_ID_PAGE)
```

where `Animation.ALL_PAGES` indicates that the `AlphaAnimation` will be run on all pages within the ViewPager, and `Animation.ANIMATION_ID_PAGE` indicates this animation will be applied to the page View itself.

### Cross page animations 
Animations that require to be animated across different pages needs to be run on `Decor`, which is an element within `JazzHandsViewPagerLayout`. A `Decor` is a component that holds information about a View that should be controlled by the ViewPager and animates when there is at least one `Animation` associated. 

Important attributes of a `Decor`:

* `contentView`: required element of a `Decor`, a View that should be used to animate across pages; 
* `startPage`: indicates starting from which page the `Decor` should be presented; 
* `endPage`: indicates on which page the `Decor` should be removed from the `JazzHandsViewPagerLayout`;
* `layoutBehindViewPage`: whether or not the `Decor` should be drawn behind the ViewPager within `JazzHandsViewPagerLayout`.

When there are more than one `Decor` in the layout, the drawing order of the content Views are based on the order that they are added through `JazzHands` or `JazzHandsViewPagerLayout#addDecor(Decor decor)`. 

To build a `Decor`, simply use `Decor#Builder`.

To assign an animation to Decor, in your Activity, for example, 

```java
JazzHandsViewPagerLayout viewPager = (JazzHandsViewPagerLayout) findViewById(/* view_pager_id */);
View contentView = new View(this);

AlphaAnimation alphaAnimation = new AlphaAnimation(Animation.ALL_PAGES, 0f, 1f);

Decor decor = new Decor.Builder()
		 .setContentView(contentView)
		 .build();
		 
JazzHands.with(viewPager)
		 .animate(alphaAnimation)
		 .on(decor);
```

a `Decor` will then be added to your `JazzHandsViewPagerLayout`, which will run the `alphaAnimation` during ViewPager scrolling.

A `Decor.Builder` supports following methods,

```java
Decor decor = new Decor.Builder()
                .setContentView(View) // Content View of the Decor, must not be null
                
                .setStartPage(int)    // Visible page start, default Animation.ALL_PAGES
                
                .setEndPage(int)      // Visible page end, default Animation.ALL_PAGES
                
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
* **[Zoom out effect](http://developer.android.com/training/animation/screen-slide.html)**: animates the target Views' alpha, scale and translation to achieve a zoom out effect when the page is scrolled. 

## Custom animations
JazzHands also supports customized animations through extending `Animation` class. There are 3 methods in `Animation` class that you might be interested:

* `onAnimate(View v, float offset, float offsetInPixel)`: main method to override to provide customized animation. The `offset` value is ranged within [-1, 1]. 
* `onAnimateOffScreenLeft(View v, float offset, float offsetInPixel)` (optional): this method will be called when `offset` < -1, which means the page is currently to the left of the screen.
* `onAnimateOffScreenRight(View v, float offset, float offsetInPixel)`(optional): this method will be called when `offset` > 1, which means the page is currently to the right of the screen.

The other two parameters, View `v` is the target View to be animated, `offsetInPixel` is the **entire page View's** scrolling offset in pixel, which might or might not be the same as `View.getWidth() * offset`.

## JazzHands and PageTransformer
If you need to have a custom PageTrasnformer for your ViewPager while using JazzHands, you need to call `JazzHandsCompat.setPageTransformer(ViewPager, boolean, PageTransformer)` to set your PageTransformer.


## Contributing

1. Fork it ( https://github.com/[my-github-username]/JazzHands-Android/fork )
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create a new Pull Request

## License

`JazzHands-Android` is available under the MIT license. See the LICENSE file for more info.

Copyright 2015 IFTTT Inc.
