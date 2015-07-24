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
ViewPager animations are the animations that animates views within pages, either the entire View or child View.

To add an animation to a View within `JazzHandsViewPager`, 

```
JazzHands.with(mJazzHandsViewPager)
		 .animate(mAnimation)
		 .on(yourViewId)
```

### Cross page animations 
Animations that require to be animated across different pages needs to be run on `Decor`, which is an element within `JazzHandsViewPagerLayout`. A `Decor` is a component that holds information about a View that should be controlled by the ViewPager and animates when there is at least one `Animation` associated. 

Important attributes of a `Decor`:

* `contentView`: required element of a `Decor`, a View that should be used to animate across pages; 
* `startPage`: indicates starting from which page the `Decor` should be presented; 
* `endPage`: indicates on which page the `Decor` should be removed from the `JazzHandsViewPagerLayout`;
* `layoutBehindViewPage`: whether or not the `Decor` should be drawn behind the ViewPager within `JazzHandsViewPagerLayout`.

When there are more than one `Decor` in the layout, the drawing order of the content Views are based on the order that they are added through `JazzHands` or `JazzHandsViewPagerLayout#addDecor(Decor decor)`. 

To build a `Decor`, simply use `Decor#Builder`.

To assign an animation to Decor, 

```
Decor decor = new Decor.Builder()
		 .build();
JazzHands.with(mJazzHandsViewPagerLayout)
		 .animate(mAnimation)
		 .on(decor);
```