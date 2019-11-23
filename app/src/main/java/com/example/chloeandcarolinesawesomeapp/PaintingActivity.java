/**
 * Color icons made by https://www.flaticon.com/authors/freepik
 */

package com.example.chloeandcarolinesawesomeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PaintingActivity extends AppCompatActivity {
    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting);
        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.paint_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.normal:
                paintView.normal();
                return true;
            case R.id.emboss:
                paintView.emboss();
                return true;
            case R.id.blur:
                paintView.blur();
                return true;
            case R.id.clear:
                paintView.clear();
                return true;
            case R.id.color_red:
                paintView.changeColor(Color.RED);
                return true;
            case R.id.color_blue:
                paintView.changeColor(Color.BLUE);
                return true;
            case R.id.color_black:
                paintView.changeColor(Color.BLACK);
                return true;
            case R.id.eraser:
                paintView.changeColor(Color.WHITE);
                paintView.normal();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
