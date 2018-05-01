package upec.projetandroid2017_2018;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by info on 28/03/18.
 */

public class AcceptedThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final String NAME = "SERVEUR";
    private NotifyActivity activity;

    public AcceptedThread(NotifyActivity activity) {
        this.activity = activity;
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }
    public void run() {
        //interompre le thread au bout de 30 sec
        Thread timer = new Thread(){
            int cmpt=120;
            @Override
            public void run() {
                while(cmpt>0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cmpt--;
                }
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        timer.start();
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            if (socket != null) {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getBaseContext(), "Un Client a rejoint la partie! ", Toast.LENGTH_SHORT).show();
                    }
                });
                ServeurActivity.clientList.add(socket);

                //Changer avec Servveur Reception Thread
                ReceptionServeur s = new ReceptionServeur(socket,activity);
                s.start();
            }
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getBaseContext(), "Fin visibilité du serveur ! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}