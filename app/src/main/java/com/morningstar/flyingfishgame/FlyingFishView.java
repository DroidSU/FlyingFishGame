/*
 * Created by Sujoy Datta. Copyright (c) 2018. All rights reserved.
 *
 * To the person who is reading this..
 * When you finally understand how this works, please do explain it to me too at sujoydatta26@gmail.com
 * P.S.: In case you are planning to use this without mentioning me, you will be met with mean judgemental looks and sarcastic comments.
 */

package com.morningstar.flyingfishgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

class FlyingFishView extends View {
    private Bitmap fishBitmap[] = new Bitmap[2];
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    private int fishXAxis = 30;
    private int fishYAxis;
    private int fishSpeed;

    private Paint yellowPaint;
    private Paint redPaint;
    private Paint greenPaint;

    private int yellowXAxis, yellowYAxis, yellowSpeed;
    private int redXAxis, redYAxis, redSpeed;
    private int greenXAxis, greenYAxis, greenSpeed;

    private int canvasHeight;
    private int canvasWidth;

    private boolean hasTouched = false;
    private int score;
    private int life_count;

    public FlyingFishView(Context context) {
        super(context);
        fishBitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fishBitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70f);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishYAxis = 550;
        life_count = 4;

        yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Get the height and width of the canvas
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        canvas.drawBitmap(backgroundImage, 0, 0, null);

        int minFishY = fishBitmap[0].getHeight();
        int maxFishY = canvasHeight - (minFishY * 3);
        fishYAxis = fishYAxis + fishSpeed;
        if (fishYAxis < minFishY) {
            fishYAxis = minFishY;
        }
        if (fishYAxis > maxFishY) {
            fishYAxis = maxFishY;
        }

        fishSpeed += 2;

        if (hasTouched) {
            canvas.drawBitmap(fishBitmap[1], fishXAxis, fishYAxis, null);
            hasTouched = false;
        } else {
            canvas.drawBitmap(fishBitmap[0], fishXAxis, fishYAxis, null);
        }

        setUpYellowBall(canvas, minFishY, maxFishY);
        setUpGreenball(canvas, minFishY, maxFishY);
        setUpRedBall(canvas, minFishY, maxFishY);

        canvas.drawText("Score: " + score, 30, 60, scorePaint);

        for (int i = 1; i <= 3; i++) {
            int x = canvasWidth - (i * 100);
            int y = 10;
            if (i <= life_count) {
                canvas.drawBitmap(life[0], x, y, null);
            } else
                canvas.drawBitmap(life[1], x, y, null);
        }
    }

    private void setUpRedBall(Canvas canvas, int minFishY, int maxFishY) {
        redSpeed = 30;
        if (redXAxis < 0) {
            redXAxis = canvasWidth + 5;
            redYAxis = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }

        redXAxis -= redSpeed;
        if (isBallHit(redXAxis, redYAxis)) {
            redXAxis -= 100;
//            score-=20;
            life_count -= 1;

            if ((life_count + 1) == 0) {
                @SuppressLint("DrawAllocation") Intent intent = new Intent(getContext(), GameOverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        }
        canvas.drawCircle(redXAxis, redYAxis, 20, redPaint);
    }

    private void setUpGreenball(Canvas canvas, int minFishY, int maxFishY) {
        greenSpeed = 30;
        if (greenXAxis < 0) {
            greenXAxis = canvasWidth + 5;
            greenYAxis = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }

        greenXAxis -= greenSpeed;
        if (isBallHit(greenXAxis, greenYAxis)) {
            greenXAxis -= 100;
            score += 20;
        }
        canvas.drawCircle(greenXAxis, greenYAxis, 20, greenPaint);
    }

    private void setUpYellowBall(Canvas canvas, int minFishY, int maxFishY) {
        yellowSpeed = 15;
        if (yellowXAxis < 0) {
            yellowXAxis = canvasWidth + 5;
            yellowYAxis = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        yellowXAxis -= yellowSpeed;
        if (isBallHit(yellowXAxis, yellowYAxis)) {
            score += 10;
            yellowXAxis = -100;
        }
        canvas.drawCircle(yellowXAxis, yellowYAxis, 20, yellowPaint);
    }

    private boolean isBallHit(int x, int y) {
        return fishXAxis < x && x < (fishXAxis + fishBitmap[0].getWidth()) && fishYAxis < y && y < (fishYAxis + fishBitmap[0].getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hasTouched = true;
            fishSpeed = -22;
        }
        return hasTouched;
    }
}
