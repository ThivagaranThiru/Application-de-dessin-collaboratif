package upec.projetandroid2017_2018;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by info on 28/03/18.
 */

public class HandleClientReader extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private NotifyActivity activity;
    private String message;

    public HandleClientReader(NotifyActivity activity) {
        this.activity=activity;
        mmSocket = ConnectedThread.blueSocket;
       ClientHandleActivity.blueSocket = ConnectedThread.blueSocket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = mmSocket.getInputStream();
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) { }
        mmInStream = tmpIn;
    }
    public void run() {

        System.out.println("Debut Ecoute du socketpour message");
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes;
        while (true) {
            try {
                bytes = mmInStream.read(buffer);
                if(bytes!=0) {
                    System.out.println("Debut de reception");
                    message = new String(buffer,0,bytes);
                    System.out.println("MESSAGE : " + message);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Dessin dv = (Dessin) activity.findViewById(R.id.dessin);
                            dv.addPoint(Point.createPoint(message));
                        }
                    });
                    System.out.println("Fin de message");
                }
            } catch (IOException e) {
                break;
            }
        }
    }
}