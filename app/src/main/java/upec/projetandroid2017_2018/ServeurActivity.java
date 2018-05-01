package upec.projetandroid2017_2018;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class ServeurActivity extends  NotifyActivity {

    private static final int REQUETE_BT_ACTION = 1234;
    private static final int REQUETE_BT_VISIBILITY = 4321;
    private static final int REQUETE_CHOICE_POINT = 77;
    private final int TimetoLive =120;
    private BluetoothAdapter mBluetoothAdapter;

    public static ArrayList<BluetoothSocket> clientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Serveur Paint");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Vous n'avez pas le Bluetooth\nMode solo Activé", Toast.LENGTH_LONG).show();
            setResult(MultiPlayerActivity.SERVER);
            finish();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUETE_BT_ACTION);
        }else{
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, TimetoLive);
            startActivityForResult(discoverableIntent, REQUETE_BT_VISIBILITY);
        }
        Dessin dessin = (Dessin) findViewById(R.id.dessin);
        dessin.setActivity(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

            case REQUETE_BT_ACTION :
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Vous n'avez pas activé le Bluetooth", Toast.LENGTH_LONG).show();
                    setResult(MultiPlayerActivity.SERVER);
                    finish();

                }
                else {
                    if(resultCode==RESULT_OK){
                        Intent discoverableIntent = new
                                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, TimetoLive);

                        startActivityForResult(discoverableIntent,REQUETE_BT_VISIBILITY);
                    }
                }
                break;
            case REQUETE_BT_VISIBILITY:
                if(resultCode!=0) {
                    Toast.makeText(this, "Attente de client pour "+TimetoLive +" sec", Toast.LENGTH_LONG).show();
                    AcceptedThread acceptThread = new AcceptedThread(this);
                    acceptThread.start();
                }
                else {
                    Toast.makeText(this, "Vous n'avez pas activé la visibilité", Toast.LENGTH_LONG).show();
                    mBluetoothAdapter.disable();
                    setResult(MultiPlayerActivity.SERVER);
                    finish();
                }
                break;

            case REQUETE_CHOICE_POINT:
                if(resultCode==RESULT_OK) {
                    Dessin dessin = (Dessin) findViewById(R.id.dessin);
                    dessin.setEpaisseur(data.getIntExtra(PaletteActivity.EPAISSEUR, 5));
                    dessin.setCouleur(data.getIntExtra(PaletteActivity.COULEUR, Color.BLACK));
                }
                break;
        }
    }
    public void sendData(String s){
        if(clientList.size()>0){
            for(BluetoothSocket b : clientList){
                try {
                    byte[] buffer;
                    buffer = s.getBytes();
                    b.getOutputStream().write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.parametres:
                Intent intent=new Intent(this, PaletteActivity.class);
                Dessin dessin = (Dessin) findViewById(R.id.dessin);
                startActivityForResult(intent,REQUETE_CHOICE_POINT);
                break;
        }
    }
}