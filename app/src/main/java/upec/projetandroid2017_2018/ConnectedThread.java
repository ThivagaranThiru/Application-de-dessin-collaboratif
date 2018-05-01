package upec.projetandroid2017_2018;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by info on 28/03/18.
 */

public class ConnectedThread extends Thread {
    public static BluetoothSocket blueSocket;
    private final BluetoothDevice blueDevice;
    private Activity activity;
    public static String SERVEUR_DEVICE="SERVEUR_DEVICE_DU_PROJET";

    public ConnectedThread(BluetoothDevice device,Activity activity) {
        this.activity=activity;
        BluetoothSocket tmp = null;
        blueDevice = device;

        // On récupère un objet BluetoothSocket grâce à l'objet BluetoothDevice
        try {
            tmp = blueDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (IOException e) { }
        blueSocket = tmp;
    }
    public void run() {
        try {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getBaseContext(), "Attente de connexion", Toast.LENGTH_SHORT).show();
                }
            });
            // On se connecte. Cet appel est bloquant jusqu'à la réussite ou la levée d'une erreur
            blueSocket.connect();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getBaseContext(), "Connecté Bien Joué !\nVeuillez attendre que les autres utilisateurs se connecte avec le serveur ", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException connectException) {
            // Impossible de se connecter, on ferme la socket
            try {
                blueSocket.close();
            } catch (IOException closeException) { }
            return;
        }
        Intent in = new Intent(activity,ClientHandleActivity.class);
        in.putExtra(SERVEUR_DEVICE,blueDevice);
        activity.startActivity(in);
    }

    // Annule toute connexion en cours et tue le thread
    public void cancel() {
        try {
            blueSocket.close();
        } catch (IOException e) { }
    }
}