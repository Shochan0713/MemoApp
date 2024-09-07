package com.example.memoapp;

import android.content.Intent;
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
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddMemoActivity.class);
            startActivityForResult(intent, 1); // リクエストコードを1に設定
        });

        //　メモのクリックイベント(編集)
        listView.setOnItemClickListener(((parent, view, position, id) -> showEditMemoDialog(position)));

        // メモの長押しイベント(削除)
        listView.setOnItemLongClickListener(((parent, view, position, id) -> {
            showDeleteMemoDialog(position);
            return true;
        }));

        // 共有ボタンのクリックイベント
        Button shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(v -> {
            if (!memoList.isEmpty()) {
                shareMemo(memoList.get(memoList.size() - 1)); // 最後のメモを共有
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String memo = data.getStringExtra("MEMO_TEXT");
            if (memo != null && !memo.isEmpty()) {
                addMemo(memo);
            }
        }
    }



    private void addMemo(String memo) {
        memoList.add(memo);
        adapter.notifyDataSetChanged(); //リストビュー更新
    }

    private void showEditMemoDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Memo");

        final EditText input = new EditText(this);
        input.setText(memoList.get(position)); // 既存のメモを設定
        builder.setView(input);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String memo = input.getText().toString();
            if (!memo.isEmpty()) {
                updateMemo(position, memo);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // メモの更新
    private void updateMemo(int position,String memo) {
        memoList.set(position,memo); //メモを更新
        adapter.notifyDataSetChanged(); // リストビューを更新
    }

    // メモの削除の確認ダイアログ表示
    private void showDeleteMemoDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Memo");
        builder.setMessage("Are you sure you want to delete this memo?");

        builder.setPositiveButton("Delete", (dialog, which) -> deleteMemo(position));

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();

    }

    // メモの削除
    private void deleteMemo(int position) {
        memoList.remove(position);
        adapter.notifyDataSetChanged();
    }

    // メモを共有するメソッド
    private void shareMemo(String memo) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, memo);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}
