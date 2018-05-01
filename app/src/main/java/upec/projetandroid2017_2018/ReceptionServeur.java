package upec.projetandroid2017_2018;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by info on 28/03/18.
 */

public class ReceptionServeur extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private NotifyActivity activity;
    private String message;

    public ReceptionServeur(BluetoothSocket socket, NotifyActivity activity) {
        this.activity = activity;
        this.mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = mmSocket.getInputStream();
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) { }
        mmInStream = tmpIn;
        mmOutStream=tmpOut;
    }
    public void run() {

        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes;
        while (true) {
            try {
                bytes = mmInStream.read(buffer);
                if(bytes!=0) {
                    System.out.println("Debut de reception");
                    message = new String(buffer,0,bytes);
                    //Faire le treatement de du message recu et le renvoyer au autre
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Dessin dessin = (Dessin) activity.findViewById(R.id.dessin);
                            dessin.addPoint(Point.createPoint(message));
                        }
                    });
                    activity.sendData(message);

                    System.out.println("MESSAGE : " + message);
                    System.out.println("Fin de message");
                }
            } catch (IOException e) {
                break;
            }
        }
    }
}