package com.bookthief.android_2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyActivity extends Activity {

    public MyActivity(){
        myActivity = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        tvScore =(TextView) findViewById(R.id.tvScore);
//        restartButton=(Button)findViewById(R.id.btn);
//        restartButton.setOnClickListener(listener);
    }
//    private View.OnClickListener listener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//        }
//    };
    /**
     * 清空分数
     */
    public void cleaScore(){
        score = 0;
        showScore();

    }

    /**
     * 加分
     */
    public void addScore(int s){
        score += s;
        showScore();
    }

    /**
     * 显示分数
     */
    public void showScore(){
        tvScore.setText(score + "");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    /**
     * 计分
     */
    private int score = 0;
    private TextView tvScore;
    private static MyActivity myActivity = null;
    private Button restartButton;

    public Button getRestartButton() {
        return restartButton;
    }

    public int getScore() {
        return score;
    }

    public static MyActivity getMyActivity() {
        return myActivity;
    }
}
