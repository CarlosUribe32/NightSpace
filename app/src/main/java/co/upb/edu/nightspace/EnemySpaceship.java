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
    String how;

    public EnemySpaceship(Context context, String how){
        this.context = context;
        this.how = how;
        if(how.equals("normal"))
            enemySpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo1);
        else{
            enemySpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo2);
        }
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
