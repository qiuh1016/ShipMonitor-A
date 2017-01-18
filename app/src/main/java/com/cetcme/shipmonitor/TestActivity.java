package com.cetcme.shipmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.victor.loading.book.BookLoading;
import com.victor.loading.newton.NewtonCradleLoading;
import com.victor.loading.rotate.RotateLoading;

public class TestActivity extends AppCompatActivity {

    NewtonCradleLoading mNewtonCradleLoading;
    BookLoading mBookLoading;
    RotateLoading mRotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mNewtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        mNewtonCradleLoading.start();

        mBookLoading = (BookLoading) findViewById(R.id.bookloading);
        mBookLoading.start();

        mRotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
        mRotateLoading.start();
    }
}
