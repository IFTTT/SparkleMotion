# JazzHands-Android
A ViewPager animator that animates Views within pages as well as views across pages.

Animations within JazzHands will run based off of the scrolling of the ViewPager, thus provides a interactive effect on the animations. 

## Supported animations
* Basic View animations:
    * Alpha
    * Rotation
    * Scale
    * Translation
* Path animation
* Parallax translation effect
* [Zoom out effect](http://developer.android.com/training/animation/screen-slide.html)

## Usage
### ViewPager animations
JazzHands animates page and page child View through `PageTransformer`.

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
                .setContentView(View)   // Content View of the Decor, must not be null
                .setStartPage(int)      // Visible page start, default Animation.ALL_PAGES
                .setEndPage(int)        // Visible page end, default Animation.ALL_PAGES
                .behindViewPage()       // Set to draw the content View behind the ViewPager
                .slideOut()             // Set to scroll with ViewPager after last visible page
                .build();
			
```