/**
 * Color icons made by https://www.flaticon.com/authors/freepik
 */

package com.example.chloeandcarolinesawesomeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;

public class PaintingActivity extends AppCompatActivity {
    private PaintView paintView;
    private FingerPath fingerPath;
    private SharedPreferences sharedPreferences;
    String TAG = "PAINTINGACTIVITY";
    int NOTE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting);
        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Intent intent = getIntent();
        if(intent.getByteArrayExtra("Bitmap") != null){
            Log.d(TAG, "in if: ");

            byte[] myArr = intent.getByteArrayExtra("Bitmap");
            Inflater inflater = new Inflater();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            Bitmap bmp = BitmapFactory.decodeByteArray(myArr, 0, myArr.length, options);

            paintView.init(metrics, bmp);

        }else {
            paintView.init(metrics);
        }
        setTitle("Draw!");

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
            case R.id.color_red:
                paintView.changeColor(Color.GREEN);
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
            case R.id.saveButton:
                Intent intent = new Intent();
                Bitmap b = paintView.getmBitmap();
                // Compress the Bitmap into a byteStream
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 50, byteStream);
                intent.putExtra("Bitmap", byteStream.toByteArray());
                setResult(Activity.RESULT_OK, intent);
                PaintingActivity.this.finish();
                return true;

        }

        return super.onOptionsItemSelected(item);

    }



}
