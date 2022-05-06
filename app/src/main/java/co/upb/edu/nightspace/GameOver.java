package co.upb.edu.nightspace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        int points = getIntent().getExtras().getInt("points");
        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setText("" + points);

        Context context = this;
        SharedPreferences sharedprefs = getSharedPreferences("puntajeMaximo", context.MODE_PRIVATE);
        String valor = sharedprefs.getString("Max", "Vacio");
        if(valor.equals("Vacio")){
            SharedPreferences.Editor editor = sharedprefs.edit();
            editor.putString("Max", String.valueOf(points));
            editor.commit();
        }
        else if(Integer.parseInt(valor)<points){
            SharedPreferences.Editor editor = sharedprefs.edit();
            editor.putString("Max", String.valueOf(points));
            editor.commit();
        }
        valor = sharedprefs.getString("Max", "Vacio");
        TextView maximo = (TextView) findViewById(R.id.tvMax);
        maximo.setText(valor);
    }


    public void exit(View view) {
        Intent intent = new Intent(GameOver.this, StartUp.class);
        startActivity(intent);
        finish();
    }
}
