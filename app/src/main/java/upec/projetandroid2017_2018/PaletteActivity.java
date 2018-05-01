package upec.projetandroid2017_2018;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PaletteActivity extends AppCompatActivity {

    public static final String EPAISSEUR = "upec.projetandroid2017_2018.Epaisseur";
    public static final String COULEUR = "upec.projetandroid2017_2018.Couleur";
    private int couleur = Color.BLACK;
    private int epaisseur = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                try{
                    epaisseur = Integer.parseInt(editText.getText().toString());
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }
                return false;
            }
        });


        Button rouge = (Button) findViewById(R.id.button);
        rouge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCouleur("#FF0000");
            }
        });

        Button marron = (Button) findViewById(R.id.button2);
        marron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCouleur("#AE642D");
            }
        });

        Button vert = (Button) findViewById(R.id.button3);
        vert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCouleur("#096A09");
            }
        });

        Button jaune = (Button) findViewById(R.id.button4);
        jaune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCouleur("#F6DC12");
            }
        });

        Button rose = (Button) findViewById(R.id.button5);
        rose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCouleur("#FF00FF");
            }
        });

        Button bleu = (Button) findViewById(R.id.button6);
        bleu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCouleur("#26C4EC");
            }
        });


        Button validate = (Button) findViewById(R.id.button7);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra(EPAISSEUR, epaisseur);
                intent.putExtra(COULEUR, couleur);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    public void setCouleur(String color){
        couleur = Color.parseColor(color);
    }
}

