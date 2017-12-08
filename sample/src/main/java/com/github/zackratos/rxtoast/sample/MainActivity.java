package com.github.zackratos.rxtoast.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.github.zackratos.rxtoast.RxToast;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    private Button showButton;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showButton = findViewById(R.id.button);
//        bindButton();
        bindButton2();
    }

    private void bindButton() {
        RxView.clicks(showButton).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                RxToast.getInstance(MainActivity.this).show("click " + (count++)).subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {

                    }
                });
            }
        });
    }

    private void bindButton2() {
        RxView.clicks(showButton).flatMap(new Function<Object, ObservableSource<CharSequence>>() {
            @Override
            public ObservableSource<CharSequence> apply(Object o) throws Exception {
                return RxToast.getInstance(MainActivity.this).show("click " + (count++));
            }
        }).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {

            }
        });
    }
}
