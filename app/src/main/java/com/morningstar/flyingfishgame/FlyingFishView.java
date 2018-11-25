/*
 * Created by Sujoy Datta. Copyright (c) 2018. All rights reserved.
 *
 * To the person who is reading this..
 * When you finally understand how this works, please do explain it to me too at sujoydatta26@gmail.com
 * P.S.: In case you are planning to use this without mentioning me, you will be met with mean judgemental looks and sarcastic comments.
 */

package com.morningstar.flyingfishgame;

import android.content.Context;
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
    private int yellowXAxis, yellowYAxis, yellowSpeed;
    private int redXAxis, redYAxis, redSpeed;

    private int canvasHeight;
    private int canvasWidth;

    private boolean hasTouched = false;
    private int score;

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

        yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);
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

        fishSpeed = fishSpeed + 2;

        if (hasTouched) {
            canvas.drawBitmap(fishBitmap[1], fishXAxis, fishYAxis, null);
            hasTouched = false;
        } else {
            canvas.drawBitmap(fishBitmap[0], fishXAxis, fishYAxis, null);
        }

        yellowSpeed = 15;

        if (yellowXAxis < 0) {
            yellowXAxis = canvasWidth + 20;
            yellowYAxis = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        yellowXAxis = yellowXAxis - yellowSpeed;

        if (isBallHit(yellowXAxis, yellowYAxis)) {
            score += 10;
            yellowYAxis = -100;
        }

        canvas.drawCircle(yellowXAxis, yellowYAxis, 20, yellowPaint);

        canvas.drawText("Score: " + score, 30, 60, scorePaint);
        canvas.drawBitmap(life[0], canvasWidth - 300, 10, null);
        canvas.drawBitmap(life[0], canvasWidth - 200, 10, null);
        canvas.drawBitmap(life[0], canvasWidth - 100, 10, null);
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
