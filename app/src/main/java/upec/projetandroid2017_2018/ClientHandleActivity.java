package upec.projetandroid2017_2018;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class ClientHandleActivity extends NotifyActivity {


    public static BluetoothSocket blueSocket;
    private static final int CHOICE = 0;
    Dessin dessin ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_handler);
        dessin = (Dessin) findViewById(R.id.dessin);
        dessin.setActivity(this);

        HandleClientReader handle =new HandleClientReader(this);
        handle.start();
    }
    public void sendData(String s){
        byte[] buffer;
        buffer = s.getBytes();
        try{
            blueSocket.getOutputStream().write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == CHOICE) {
                if (resultCode == RESULT_OK) {
                    dessin.setEpaisseur(data.getIntExtra(PaletteActivity.EPAISSEUR, 5));
                    dessin.setCouleur(data.getIntExtra(PaletteActivity.COULEUR, Color.BLACK));
                }
        }
    }

    public void onClick(View view) {
       if(view.getId() == R.id.parametres){
                Intent intent=new Intent(this, PaletteActivity.class);
                intent.putExtra(PaletteActivity.EPAISSEUR,dessin.getEpaisseur());
                intent.putExtra(PaletteActivity.COULEUR,dessin.getCouleur());
                startActivityForResult(intent, CHOICE);
        }
    }
}