package net.sledzdev.shoppinglist.manager;

import android.app.Activity;
import android.view.View;

import com.google.common.util.concurrent.FutureCallback;

/**
 * Created by Mariusz on 23.01.14.
 */
public abstract class OnUiThreadFutureCallback<T> implements FutureCallback<T> {
    Activity activity;
    View view;

    protected OnUiThreadFutureCallback(Activity activity) {
        this.activity = activity;
    }

    protected OnUiThreadFutureCallback(View view) {
        this.view = view;
    }

    @Override
    public void onSuccess(final T t) {
        post(new Runnable() {
            @Override
            public void run() {
                onSuccessUiThread(t);
            }
        });
    }

    protected abstract void onSuccessUiThread(T t);

    protected void post(Runnable runnable) {
        if (view != null) {
            view.post(runnable);
        } else if (activity != null) {
            activity.runOnUiThread(runnable);
        } else {
            throw new IllegalArgumentException("Activity or View must be set.");
        }
    }
}
