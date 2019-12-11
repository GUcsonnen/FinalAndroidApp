package com.example.chloeandcarolinesawesomeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int drawingNumber = 1;
    static final int NOTE_CODE = 2;
    List<byte[]> items = new ArrayList<>();
    private ArrayAdapter<byte[]> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Draw!");
        final ListView listView = new ListView(this);
        setContentView(listView);
        arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_activated_1,
                items);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PaintingActivity.class);
                intent.putExtra("Bitmap", items.get(i));
                startActivityForResult(intent, NOTE_CODE);
            }
        });
    }


    public String newName(){
        String name = "My Masterspiece: " + drawingNumber;
        drawingNumber++;
        return name;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // get a reference to the MenuInflater
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addMenuItem:
                startPaintingActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startPaintingActivity() {
        Intent intent = new Intent(this, PaintingActivity.class);
        this.startActivityForResult(intent, NOTE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NOTE_CODE && resultCode == Activity.RESULT_OK){
            try {
                String name = newName();
                items.add(data.getByteArrayExtra("Bitmap"));

                arrayAdapter.notifyDataSetChanged();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}


