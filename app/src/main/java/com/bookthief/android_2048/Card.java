package com.bookthief.android_2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by BookThief on 2014/11/12.
 */
public class Card extends FrameLayout {

    /**
     * 卡片视图构造函数
     */
    public Card(Context context) {
        super(context);
        lable = new TextView(getContext());
        lable.setTextSize(32);
        lable.setGravity(Gravity.CENTER);
        lable.setBackgroundColor(0x77ffffff);

        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(lable,lp);
        setNum(0);
    }

    private int num = 0;
    private TextView lable;


    public boolean equals(Card o){
        return getNum() == o.getNum();
    }

    /**
     * getter & setter
     */
    public void setNum(int num) {
        this.num = num;
        if (num <= 0){
            lable.setText("");
        }else {
            lable.setText(num+"");
        }

    }

    public int getNum() {
        return num;
    }
}
