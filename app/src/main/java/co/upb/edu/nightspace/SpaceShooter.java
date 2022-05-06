package co.upb.edu.nightspace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpaceShooter extends View {
    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    long UPDATE_MILLIS = 30;
    static int screenWidth, screenHeight;
    int points = 0;
    int life = 3;
    int time = 0;
    Paint scorePaint;
    int TEXT_SIZE = 80;
    boolean paused = false;
    OurSpaceship ourSpaceship;
    EnemySpaceship enemySpaceship;
    Random random;
    ArrayList<Shot> enemyShots, ourShots;
    Explosion explosion;
    ArrayList<Explosion> explosions;
    ArrayList<EnemySpaceship> enemyList;
    Map<EnemySpaceship, ArrayList<Shot>> mapenemyShots = new HashMap<>();
    Map<EnemySpaceship, Boolean> mapenemyShotAction= new HashMap<>();
    boolean enemyShotAction = false;
    boolean restLife = true;
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    public SpaceShooter(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
//        enemyShots = new ArrayList<>();
        ourShots = new ArrayList<>();
        explosions = new ArrayList<>();
        ourSpaceship = new OurSpaceship(context);
//        enemySpaceship = new EnemySpaceship(context);
        enemyList = new ArrayList<>();
        handler = new Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.spacebackground);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Pt: " + points, 0, TEXT_SIZE, scorePaint);
        for(int i=life; i>=1; i--){
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
        }

        if(life == 0){
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
        if(time%1000==0){
            enemySpaceship = new EnemySpaceship(context);
            enemyList.add(enemySpaceship);
            enemyShots = new ArrayList<>();
            mapenemyShots.put(enemySpaceship, enemyShots);
            enemyShotAction = false;
            mapenemyShotAction.put(enemySpaceship, enemyShotAction);
        }
        for (EnemySpaceship enemy:enemyList) {
            enemyShots = mapenemyShots.get(enemy);
            mapenemyShots.remove(enemy);
            enemyShotAction = mapenemyShotAction.get(enemy);
            mapenemyShotAction.remove(enemy);

            enemy.ex += enemy.enemyVelocity;

            if(enemy.ex + enemy.getEnemySpaceshipWidth() >= screenWidth){
                enemy.enemyVelocity *= -1;
            }

            if(enemy.ex <=0){
                enemy.enemyVelocity *= -1;
            }

            if(enemyShotAction == false){
                if(enemy.ex >= 200 + random.nextInt(400)){
                    Shot enemyShot = new Shot(context, enemy.ex + enemy.getEnemySpaceshipWidth() / 2, enemy.ey );
                    enemyShots.add(enemyShot);

                    enemyShotAction = true;
                }
                if(enemy.ex >= 400 + random.nextInt(800)){
                    Shot enemyShot = new Shot(context, enemy.ex + enemy.getEnemySpaceshipWidth() / 2, enemy.ey );
                    enemyShots.add(enemyShot);

                    enemyShotAction = true;
                }
                else{
                    Shot enemyShot = new Shot(context, enemy.ex + enemy.getEnemySpaceshipWidth() / 2, enemy.ey );
                    enemyShots.add(enemyShot);

                    enemyShotAction = true;
                }
            }
            mapenemyShots.put(enemy, enemyShots);
            mapenemyShotAction.put(enemy, enemyShotAction);
        }

//        enemySpaceship.ex += enemySpaceship.enemyVelocity;
//
//        if(enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() >= screenWidth){
//            enemySpaceship.enemyVelocity *= -1;
//        }
//
//        if(enemySpaceship.ex <=0){
//            enemySpaceship.enemyVelocity *= -1;
//        }
//
//        if(enemyShotAction == false){
//            if(enemySpaceship.ex >= 200 + random.nextInt(400)){
//                Shot enemyShot = new Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey );
//                enemyShots.add(enemyShot);
//
//                enemyShotAction = true;
//            }
//            if(enemySpaceship.ex >= 400 + random.nextInt(800)){
//                Shot enemyShot = new Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey );
//                enemyShots.add(enemyShot);
//
//                enemyShotAction = true;
//            }
//            else{
//                Shot enemyShot = new Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey );
//                enemyShots.add(enemyShot);
//
//                enemyShotAction = true;
//            }
//        }

        for (EnemySpaceship enemy:enemyList) {
            canvas.drawBitmap(enemy.getEnemySpaceship(), enemy.ex, enemy.ey, null);
        }
//        canvas.drawBitmap(enemySpaceship.getEnemySpaceship(), enemySpaceship.ex, enemySpaceship.ey, null);
        time++;
        if(ourSpaceship.ox > screenWidth - ourSpaceship.getOurSpaceshipWidth()){
            ourSpaceship.ox = screenWidth - ourSpaceship.getOurSpaceshipWidth();
        }else if(ourSpaceship.ox < 0){
            ourSpaceship.ox = 0;
        }

        canvas.drawBitmap(ourSpaceship.getOurSpaceship(), ourSpaceship.ox, ourSpaceship.oy, null);

        for (EnemySpaceship enemy:enemyList) {
            enemyShots = mapenemyShots.get(enemy);
            mapenemyShots.remove(enemy);
            enemyShotAction = mapenemyShotAction.get(enemy);
            mapenemyShotAction.remove(enemy);

            for(int i=0; i < enemyShots.size(); i++){
                enemyShots.get(i).shy += 15;
                canvas.drawBitmap(enemyShots.get(i).getShot(), enemyShots.get(i).shx, enemyShots.get(i).shy, null);
                if((enemyShots.get(i).shx >= ourSpaceship.ox)
                        && enemyShots.get(i).shx <= ourSpaceship.ox + ourSpaceship.getOurSpaceshipWidth()
                        && enemyShots.get(i).shy >= ourSpaceship.oy
                        && enemyShots.get(i).shy <= screenHeight){
                    if(restLife){
                        life--;
                        restLife = false;
                    }

                    enemyShots.remove(i);
                    explosion = new Explosion(context, ourSpaceship.ox, ourSpaceship.oy);
                    explosions.add(explosion);
                }else if(enemyShots.get(i).shy >= screenHeight){
                    enemyShots.remove(i);
                }
                if(enemyShots.size() < 1){
                    enemyShotAction = false;
                    restLife = true;
                }
            }
            mapenemyShots.put(enemy, enemyShots);
            mapenemyShotAction.put(enemy, enemyShotAction);
        }

//        for(int i=0; i < enemyShots.size(); i++){
//            enemyShots.get(i).shy += 15;
//            canvas.drawBitmap(enemyShots.get(i).getShot(), enemyShots.get(i).shx, enemyShots.get(i).shy, null);
//            if((enemyShots.get(i).shx >= ourSpaceship.ox)
//                    && enemyShots.get(i).shx <= ourSpaceship.ox + ourSpaceship.getOurSpaceshipWidth()
//                    && enemyShots.get(i).shy >= ourSpaceship.oy
//                    && enemyShots.get(i).shy <= screenHeight){
//                if(restLife){
//                    life--;
//                    restLife = false;
//                }
//
//                enemyShots.remove(i);
//                explosion = new Explosion(context, ourSpaceship.ox, ourSpaceship.oy);
//                explosions.add(explosion);
//            }else if(enemyShots.get(i).shy >= screenHeight){
//                enemyShots.remove(i);
//            }
//            if(enemyShots.size() < 1){
//                enemyShotAction = false;
//                restLife = true;
//            }
//        }

        for(int i=0; i < ourShots.size(); i++){
            ourShots.get(i).shy -= 15;
            canvas.drawBitmap(ourShots.get(i).getShot(), ourShots.get(i).shx, ourShots.get(i).shy, null);
//            if((ourShots.get(i).shx >= enemySpaceship.ex)
//                    && ourShots.get(i).shx <= enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth()
//                    && ourShots.get(i).shy <= enemySpaceship.getEnemySpaceshipWidth()
//                    && ourShots.get(i).shy >= enemySpaceship.ey){
//                points++;
//                ourShots.remove(i);
//                explosion = new Explosion(context, enemySpaceship.ex, enemySpaceship.ey);
//                explosions.add(explosion);
//            }
//            else if(ourShots.get(i).shy <=0){
//                ourShots.remove(i);
//            }
            for (EnemySpaceship enemy:enemyList) {
                if((ourShots.get(i).shx >= enemy.ex)
                        && ourShots.get(i).shx <= enemy.ex + enemy.getEnemySpaceshipWidth()
                        && ourShots.get(i).shy <= enemy.getEnemySpaceshipWidth()
                        && ourShots.get(i).shy >= enemy.ey){
                    points++;
                    ourShots.remove(i);
                    explosion = new Explosion(context, enemy.ex, enemy.ey);
                    explosions.add(explosion);
                    break;
                }
                else if(ourShots.get(i).shy <=0){
                    ourShots.remove(i);
                    break;
                }
            }

        }

        for(int i=0; i < explosions.size(); i++){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).eX, explosions.get(i).eY, null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame > 8){
                explosions.remove(i);
            }
        }

        if(!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();

        if(event.getAction() == MotionEvent.ACTION_UP){
            if(ourShots.size() < 1){
                Shot ourShot = new Shot(context, ourSpaceship.ox + ourSpaceship.getOurSpaceshipWidth() / 2, ourSpaceship.oy);
                ourShots.add(ourShot);
            }
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            ourSpaceship.ox = touchX;
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            ourSpaceship.ox = touchX;
        }

        return true;
    }
}
