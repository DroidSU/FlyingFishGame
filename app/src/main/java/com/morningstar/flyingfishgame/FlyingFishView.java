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
import android.view.View;

class FlyingFishView extends View {
    private Bitmap fishBitmap;
    private Bitmap backgroundImage;
    private Paint score = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);
        fishBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        score.setColor(Color.WHITE);
        score.setTextSize(70f);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(backgroundImage, 0, 0, null);
        canvas.drawBitmap(fishBitmap, 0, 600, null);
        canvas.drawText("Score: ", 30, 60, score);

        canvas.drawBitmap(life[0], 580, 10, null);
        canvas.drawBitmap(life[0], 680, 10, null);
        canvas.drawBitmap(life[0], 780, 10, null);
    }
}
