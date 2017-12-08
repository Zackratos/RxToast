package com.github.zackratos.rxtoast;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import io.reactivex.Observable;

/**
 *
 * Created by Zackratos on 2017/12/8.
 */

public class RxToast {

    private static RxToast rxToast;

    private Toast toast;

    private Context context;

    private RxToast(Context context) {
        this.context = context.getApplicationContext();
    }

    public static RxToast getInstance(Context context) {
        if (rxToast == null) {
            synchronized (RxToast.class) {
                if (rxToast == null) {
                    rxToast = new RxToast(context);
                }
            }
        }
        return rxToast;
    }

    private void initToast(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
    }

    private void initToast(@StringRes int stringRes) {
        if (toast == null) {
            toast = Toast.makeText(context, stringRes, Toast.LENGTH_SHORT);
        } else {
            toast.setText(stringRes);
        }
    }

    public Observable<CharSequence> show(CharSequence text) {
        initToast(text);
        return new ToastObservable(toast, text);
    }

    public Observable<CharSequence> show(@StringRes int stringRes) {
        initToast(stringRes);
        return new ToastObservable(toast, context.getString(stringRes));
    }
}
