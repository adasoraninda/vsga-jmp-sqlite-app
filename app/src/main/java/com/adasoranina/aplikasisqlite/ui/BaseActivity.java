package com.adasoranina.aplikasisqlite.ui;

import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected void showMessage(String message) {
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT)
                .show();
    }

    protected void showMessage(@StringRes int messageRes) {
        Toast.makeText(
                this,
                getString(messageRes),
                Toast.LENGTH_SHORT)
                .show();
    }

}
