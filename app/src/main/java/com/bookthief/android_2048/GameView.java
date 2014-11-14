package com.bookthief.android_2048;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by BookThief on 2014/11/5.
 */
public class GameView extends GridLayout{
    /**
     * 游戏主视图构造函数
     */
    @TargetApi(Build.VERSION_CODES.L)
    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }
    public GameView(Context context) {
        super(context);
        initGameView();
    }

    /**
     *
     */
    private  Card[][] cardMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
    /**
     * 主视图初始化
     */
    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        //手势判断 Start
        setOnTouchListener(new View.OnTouchListener(){
            private float startx,starty,offsetx,offsety;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startx = event.getX();
                        starty = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetx = event.getX() - startx;
                        offsety = event.getY() - starty;
                        if(Math.abs(offsetx) >Math.abs(offsety)) {
                            if (offsetx < -5) {
                                SwipeLeft();
                            } else if (offsetx > 5) {
                                SwipeRight();
                            }
                        }else {
                            if (offsety < -5) {
                                SwipeUp();
                            }else if (offsety > 5) {
                                SwipeDown();
                            }

                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        //手势判断 End
        }

        /**
         * 随屏幕大小调整游戏视图
         */
        protected void onSizeChanged(int w,int h,int oldw,int oldh){
            super.onSizeChanged(w,h,oldw,oldh);
            int cardWidth = (Math.min(w,h) - 10) / 4;

            addCards(cardWidth,cardWidth);
            startGame();
        }

        /**
         * 卡片视图
         */
        private void  addCards(int cardWidth,int cardHeight){
            Card c;

            for (int col= 0;col < 4;col++){
                for (int row = 0;row < 4;row++){
                    c = new Card(getContext());
                    c.setNum(0);
                    addView(c,cardWidth,cardHeight);

                    cardMap[row][col] = c;

                }
            }
        }

        /**
         *  开启游戏
         */
        private void startGame(){
            MyActivity.getMyActivity().cleaScore();
            for (int col= 0;col < 4;col++){
                for (int row = 0;row < 4;row++){
                   cardMap[row][col].setNum(0);

                }
            }
            addRandomNum();
            addRandomNum();
        }

        /**
         *  检查游戏是否结束
         */
        private  boolean checkComplete(){
            for (int row = 0;row < 4;row++){
                for (int col = 0;col < 4;col++)
                    if (cardMap[col][row].getNum() == 0 ||
                        (col > 0 && cardMap[col][row].equals(cardMap[col-1][row])) ||
                        (col < 3 && cardMap[col][row].equals(cardMap[col+1][row])) ||
                        (row > 0 && cardMap[col][row].equals(cardMap[col][row-1])) ||
                        (row < 3 && cardMap[col][row].equals(cardMap[col][row+1]))){
                        return false;
                    }
            }
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束,您的得分是 "+ MyActivity.getMyActivity().getScore()+" ！").setPositiveButton("重来", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                    // TODO Auto-generated method stub
                }
            }).show();
            return true;
        }

        /**
         *  生成随机数
         */
        private void addRandomNum(){

            emptyPoints.clear();
            for (int col = 0;col < 4 ;col++){
                for (int row = 0;row < 4 ;row++){
                    if (cardMap[row][col].getNum()<=0){
                        emptyPoints.add(new Point(row,col));
                    }

                }
            }

            Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
            cardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);


        }

    /**
     * 两元素之间是否有障碍
     */
    private boolean noBlockHorizontal(int row,int col1,int col2){
        for (int i = col1 + 1;i < col2;i++)
            if (cardMap[i][row].getNum() != 0)
                return false;
        return true;
    }

    private boolean noBlockVertical(int col,int row1,int row2){
        for (int i = row1 + 1;i < row2;i++)
            if (cardMap[col][i].getNum() != 0)
                return false;
        return true;
    }
    /**
     * 是否能够移动
     */
    private boolean canMoveLeft(){
     for (int row = 0;row < 4;row++){
         for (int col = 1;col < 4;col++){
             if(cardMap[col][row].getNum() > 0)
                 if(cardMap[col-1][row].getNum()==0 ||cardMap[col-1][row].getNum()==cardMap[col][row].getNum())
                     return true;
         }
     }
     return false;
    }
    private boolean canMoveRight(){
        for (int row = 0;row < 4;row++){
            for (int col = 2;col >= 0;col--){
                if(cardMap[col][row].getNum() > 0)
                    if(cardMap[col+1][row].getNum()==0 ||cardMap[col+1][row].getNum()==cardMap[col][row].getNum())
                        return true;
            }
        }
        return false;
    }
    private boolean canMoveUp(){
        for (int col = 0;col < 4;col++){
            for (int row = 1;row < 4;row++){
                if(cardMap[col][row].getNum() > 0)
                    if(cardMap[col][row-1].getNum()==0 ||cardMap[col][row-1].getNum()==cardMap[col][row].getNum())
                        return true;
            }
        }
        return false;
    }
    private boolean canMoveDown(){
        for (int col = 0;col < 4;col++){
            for (int row = 2;row >= 0;row--){
                if(cardMap[col][row].getNum() > 0)
                    if(cardMap[col][row+1].getNum()==0 ||cardMap[col][row+1].getNum()==cardMap[col][row].getNum())
                        return true;
            }
        }
        return false;
    }

    /**
     * 手势操作
     */

    private void SwipeLeft(){
        if (canMoveLeft()) {
            for (int row = 0; row < 4; row++) {
                for (int col = 1; col < 4; col++) {
                    if (cardMap[col][row].getNum() >= 0)
                        for (int hor = 0; hor < col; hor++) {
                            if (cardMap[hor][row].getNum() == 0 && noBlockHorizontal(row, hor, col)) {
                                cardMap[hor][row].setNum(cardMap[col][row].getNum());
                                cardMap[col][row].setNum(0);
                                break;
                            } else if (cardMap[hor][row].getNum() == cardMap[col][row].getNum() && noBlockHorizontal(row, hor, col)) {
                                cardMap[hor][row].setNum(cardMap[col][row].getNum() * 2);
                                cardMap[col][row].setNum(0);
                                MyActivity.getMyActivity().addScore(cardMap[hor][row].getNum());
                                break;
                            }
                        }
                }
            }
            addRandomNum();
        }
        checkComplete();
    }
    private void SwipeRight(){
        if (canMoveRight()) {
            for (int row = 0; row < 4; row++) {
                for (int col = 2; col >= 0; col--) {
                    if (cardMap[col][row].getNum() >= 0)
                        for (int hor = 3; hor > col; hor--) {
                            if (cardMap[hor][row].getNum() == 0 && noBlockHorizontal(row, col, hor)) {
                                cardMap[hor][row].setNum(cardMap[col][row].getNum());
                                cardMap[col][row].setNum(0);
                                break;
                            } else if (cardMap[hor][row].getNum() == cardMap[col][row].getNum() && noBlockHorizontal(row, col, hor)) {
                                cardMap[hor][row].setNum(cardMap[col][row].getNum() * 2);
                                cardMap[col][row].setNum(0);
                                MyActivity.getMyActivity().addScore(cardMap[hor][row].getNum());
                                break;
                            }
                        }
                }
            }
            addRandomNum();
        }
        checkComplete();

    }
    private void SwipeUp(){
        if (canMoveUp()) {
            for (int row = 1; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    if (cardMap[col][row].getNum() >= 0)
                        for (int ver = 0; ver < row; ver++) {
                            if (cardMap[col][ver].getNum() == 0 && noBlockVertical(col, ver, row)) {
                                cardMap[col][ver].setNum(cardMap[col][row].getNum());
                                cardMap[col][row].setNum(0);
                                break;
                            } else if (cardMap[col][ver].getNum() == cardMap[col][row].getNum() && noBlockVertical(col, ver, row)) {
                                cardMap[col][ver].setNum(cardMap[col][row].getNum() * 2);
                                cardMap[col][row].setNum(0);
                                MyActivity.getMyActivity().addScore(cardMap[col][ver].getNum());
                                break;
                            }
                        }
                }
            }
            addRandomNum();
        }
        checkComplete();
    }
    private void SwipeDown(){
        if (canMoveDown()) {
            for (int row = 2; row >= 0; row--) {
                for (int col = 0; col < 4; col++) {
                    if (cardMap[col][row].getNum() >= 0)
                        for (int ver = 3; ver > row; ver--) {
                            if (cardMap[col][ver].getNum() == 0 && noBlockVertical(col, row, ver)) {
                                cardMap[col][ver].setNum(cardMap[col][row].getNum());
                                cardMap[col][row].setNum(0);
                                break;
                            } else if (cardMap[col][ver].getNum() == cardMap[col][row].getNum() && noBlockVertical(col, row, ver)) {
                                cardMap[col][ver].setNum(cardMap[col][row].getNum() * 2);
                                cardMap[col][row].setNum(0);
                                MyActivity.getMyActivity().addScore(cardMap[col][ver].getNum());
                                break;
                            }
                        }
                }
            }
            addRandomNum();
        }
        checkComplete();
    }

        /**
         *  手势操作(极客学院提供  有逻辑问题)
        private void SwipeLeft(){
            for (int y = 0;y < 4;y++){
                for (int x = 0;x < 4;x++){
                    int temp = x+1;

                    for (int x1 = temp;x1 < 4;x1++){
                        if (cardMap[x1][y].getNum() > 0){
                            if (cardMap[x][y].getNum() <= 0){
                                cardMap[x][y].setNum(cardMap[x1][y].getNum());
                                cardMap[x1][y].setNum(0);
                                x--;
                                break;
                            } else if(cardMap[x][y].equals(cardMap[x1][y])){

                                cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                                cardMap[x1][y].setNum(0);
                                break;
                            }

                        }


                    }

                }
            }
            addRandomNum();
        }
        private void SwipeRight(){
            for (int y = 0;y < 4;y++){
                for (int x = 3;x >= 0;x--){

                    for (int x1 = x - 1;x1 >= 0;x1--){
                        if (cardMap[x1][y].getNum() > 0){
                            if (cardMap[x][y].getNum() <= 0){
                                cardMap[x][y].setNum(cardMap[x1][y].getNum());
                                cardMap[x1][y].setNum(0);
                                x++;
                                break;
                            } else if(cardMap[x][y].equals(cardMap[x1][y])){
                                cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                                cardMap[x1][y].setNum(0);
                                break;
                            }

                        }


                    }

                }
            }
            addRandomNum();
        }
        private void SwipeUp(){
            for (int x = 0;x < 4;x++){
                for (int y = 0;y < 4;y++){

                    for (int y1 = y +1;y1 < 4;y1++){
                        if (cardMap[x][y1].getNum() > 0){
                            if (cardMap[x][y].getNum() <= 0){
                                cardMap[x][y].setNum(cardMap[x ][y1].getNum());
                                cardMap[x][y1].setNum(0);
                                y--;
                                break;
                            } else if(cardMap[x][y].equals(cardMap[x ][y1])){
                                cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                                cardMap[x ][y1].setNum(0);
                                break;
                            }

                        }


                    }

                }
            }
            addRandomNum();
        }
        private void SwipeDown(){
            for (int x = 0;x < 4;x++){
                for (int y = 3;y >= 0;y--){

                    for (int y1 = y - 1;y1 >= 0;y1--){
                        if (cardMap[x][y1].getNum() > 0){
                            if (cardMap[x][y].getNum() <= 0){
                                cardMap[x][y].setNum(cardMap[x ][y1].getNum());
                                cardMap[x][y1].setNum(0);
                                y++;
                                break;
                            } else if(cardMap[x][y].equals(cardMap[x ][y1])){
                                cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                                cardMap[x ][y1].setNum(0);
                                break;
                            }

                        }


                    }

                }
            }
            addRandomNum();
        }
        */


    }
