package com.example.memoapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> memoList; //メモを格納するリスト
    private ArrayAdapter<String> adapter; //リスト表示用のアダプター

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // メモリストの初期化
        memoList = new ArrayList<>();

        // リストビューとアダプターの設定
        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memoList);
        listView.setAdapter(adapter);

        // メモ追加ボタンのクリックイベント
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> showAddMemoDialog());
    }

    // メモ追加のダイアログ表示
    private void showAddMemoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("new Memo");

        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Add", ((dialog, which) -> {
            String memo = input.getText().toString();
            if (!memo.isEmpty()) {
                addMemo(memo);
            }
        }));
        builder.setNegativeButton("Cancel", ((dialog, which) -> dialog.cancel()));
        builder.show();
    }

    private void addMemo(String memo) {
        memoList.add(memo);
        adapter.notifyDataSetChanged(); //リストビュー更新
    }
}
