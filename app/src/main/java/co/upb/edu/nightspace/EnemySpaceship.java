package co.upb.edu.nightspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;

public class EnemySpaceship {
    Context context;
    Bitmap enemySpaceship;
    int ex, ey, enemyVelocity;
    Random random;

    public EnemySpaceship(Context context){
        this.context = context;
        enemySpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo1);
        random = new Random();
        ex = 200+random.nextInt(400);
        ey=0;
        enemyVelocity = 14+ random.nextInt(10);
    }

    public Bitmap getEnemySpaceship(){
        return enemySpaceship;
    }

    public int getEnemySpaceshipWidth(){
        return enemySpaceship.getWidth();
    }

    public int getEnemySpaceshipHeight(){
        return enemySpaceship.getHeight();
    }

}
