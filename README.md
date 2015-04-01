# JazzHands-Android
A ViewPager animator that animates Views within pages.

## Supported animations
* Basic View animations:
    * Alpha
    * Rotation
    * Scale
    * Translation
* Path animation
* Parallax translation effect

### Limitation
On Android 4.2 and below (`SDK_IND` < 18), if the parent view is moved off-screen, the child views will not be drawn even if they are still on screen by changing the translation properties. Therefore, cross page `PathAnimation` and `TranslationAnimation` will not work.

A workaround (very hacky) is to try to keep the parent view on screen by a very small amount (e.g. 1px). To do that, you can add a `PageTransformer` to `JazzHandsViewPager`:

```
if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            viewPager.setPageTransformer(new ViewPager.PageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    int pageWidth = page.getWidth();

                    if (position <= -1) {
                        page.setTranslationX(-pageWidth * (position + (int) Math.abs(position)) + 1);
                    }
                }
            });
        }
```

**Note:** you need take the page View's `translationX` change into account on your cross page `TranslationAnimation` and `PathAnimation`.

We'll be extremely happy if someone can point out what we have done wrong and how we can fix this issue.

## TODO
* Fix pre-Jelly Bean MR2 animation issue when parent view is off screen.
* Find a better interpretation of the methods in `Animation` and `JazzHandsViewPager`.
* Find a better data structure for storing animations in `JazzHandsAnimationPresenter`.