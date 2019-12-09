package com.example.chloeandcarolinesawesomeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private PaintView paintView;
    int drawingNumber = 1;
    static final int NOTE_CODE = 1;
    List<String> items = new ArrayList<>();
    String TAG = "MainActivity";
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ListView listView = new ListView(this);

        setContentView(listView);

        // create an array adapter
        arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_activated_1,
                items);
        listView.setAdapter(arrayAdapter);
        // set the listview to support multiple selections
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // set the multi choice listener
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                int numChecked = listView.getCheckedItemCount();
                actionMode.setTitle(numChecked + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.contextual_action_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                // don't need this for PA7
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                // executes when the user clicks a CAM menu item
                // task: switch on menu item id... try to show (log or toast) the
                // indexes of the items that are checked
                switch (menuItem.getItemId()) {
                    case R.id.deleteMenuItem:
                        // hint for PA7... use ids...
                        String temp = listView.getCheckedItemPositions().toString();
                        Toast.makeText(MainActivity.this, temp, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, PaintingActivity.class);
                        startActivity(intent);
                        actionMode.finish();
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                // don't need this for PA7
            }
        });


        // make sure your items show up in the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PaintingActivity.class);
                startActivity(intent);
            }
        });
        //startEditItemActivity();
    }


    public String newName(){
        String name = "My Masterpiece: " + drawingNumber;
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

    // override a callback that executes whenever an options menu action is clicked

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addMenuItem:
                startPaintingActivity();
                return true; // we have consumed/handled this click event
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
            Log.d(TAG, "in on activity if");

            String[] message = data.getStringArrayExtra("canvas");
            String myNewName = newName();
            items.add(myNewName);
            Log.d(TAG, "in on activity if " + myNewName);

            arrayAdapter.notifyDataSetChanged();

        }
    }
}


