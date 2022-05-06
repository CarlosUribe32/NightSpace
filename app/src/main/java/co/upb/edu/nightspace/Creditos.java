package co.upb.edu.nightspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Creditos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        TextView textCreditos = findViewById(R.id.textCreditos);
        textCreditos.setText( Html.fromHtml( getString(R.string.Creditos) ) );

        Button volver = findViewById(R.id.btnVolver);
        volver.setOnClickListener(listener);

    }
    private View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Creditos.this, StartUp.class);
            startActivity(intent);
        }
    };


}