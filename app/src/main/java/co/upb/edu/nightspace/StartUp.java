package co.upb.edu.nightspace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartUp extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.startup);
    }

    public void startGame(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    public void startCreditos(View view){
        startActivity(new Intent(this, Creditos.class));
        finish();
    }
}
