package com.example.memoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        EditText memoInput = findViewById(R.id.memoInput);
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton); // キャンセルボタンを取得

        saveButton.setOnClickListener(v -> {
            String memo = memoInput.getText().toString().trim(); // 空白をトリム
            if (memo.isEmpty()) {
                // メモが空白の場合のエラーメッセージを表示
                Toast.makeText(AddMemoActivity.this, "Memo cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("MEMO_TEXT", memo);
                setResult(RESULT_OK, resultIntent);
                finish(); // 現在のアクティビティを終了して、前のアクティビティに戻る
            }
        });

        // キャンセルボタンのクリックリスナーを設定
        cancelButton.setOnClickListener(v -> finish());
    }
}
