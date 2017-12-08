package com.github.zackratos.rxtoast;

import android.os.Looper;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 *
 * Created by Zackratos on 2017/12/8.
 */

final class ToastObservable extends Observable<CharSequence> {

    private Toast toast;

    private CharSequence text;

    ToastObservable(Toast toast, CharSequence text) {
        this.toast = toast;
        this.text = text;
    }

    @Override
    protected void subscribeActual(Observer<? super CharSequence> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        observer.onSubscribe(new Disposable(toast));
        observer.onNext(text);
        toast.show();
    }

    private boolean checkMainThread(Observer<? super CharSequence> observer) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            observer.onError(new IllegalStateException(
                    "Expected to be called on the main thread but was " + Thread.currentThread().getName()));
            return false;
        }
        return true;
    }

    private static final class Disposable extends MainThreadDisposable {

        private Toast toast;

        Disposable(Toast toast) {
            this.toast = toast;
        }

        @Override
        protected void onDispose() {
            toast.cancel();
        }
    }
}
