
package com.mafdy.udacity_bakingapp.espresso;

import android.support.annotation.Nullable;


import java.util.concurrent.atomic.AtomicBoolean;
import android.support.test.espresso.IdlingResource;


public class MyIdlingResource implements IdlingResource {

    @Nullable
    private volatile android.support.test.espresso.IdlingResource.ResourceCallback mCallback;

    // Idleness is controlled with this boolean.
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(android.support.test.espresso.IdlingResource.ResourceCallback callback) {
        mCallback = callback;
    }

    /**
     * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}.
     * @param isIdleNow false if there are pending operations, true if idle.
     */
    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}