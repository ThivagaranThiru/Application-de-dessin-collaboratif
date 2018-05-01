package upec.projetandroid2017_2018;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by info on 28/03/18.
 */

public class MyConnectedDevice extends BroadcastReceiver {
    private BluetoothDevice device;
    private Activity activity;
    private Context c;

    public MyConnectedDevice(Activity activity){
        this.activity=activity;
        this.c=activity.getBaseContext();
    }
    private int i=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {

            device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if(device!=null){
                i++;
                MiseAjourList();
                Toast.makeText(activity.getBaseContext(), "Appareils trouvés: "+i, Toast.LENGTH_SHORT).show();
                System.out.println("DEVICE NAME " + device.getName());
            }
            else System.out.println("DEVICE trouvé mais egale a Null :  " + device.getName());
        }
    }

    private ArrayList<BluetoothDevice> foundedDevice = new ArrayList<>();
    protected void MiseAjourList(){

        if(!foundedDevice.contains(device)) {
            foundedDevice.add(device);
            Button b =new Button(c);
            b.setText(device.getName());
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectedThread clientConnect = new ConnectedThread(device, activity);
                    clientConnect.start();
                }
            });
            LinearLayout lv =(LinearLayout)activity.findViewById(R.id.linear);
            lv.addView(b);
        }
    }
}