package com.ifttt.sparklemotion;

import android.view.View;

/**
 * Created by zhelu on 11/10/15.
 */
interface ProgressGenerator<T> {
    float getProgress(T child, View dependency);
}
