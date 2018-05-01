package upec.projetandroid2017_2018;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class ClientActivity extends AppCompatActivity {
    
    private static final int REQUEST_ACTION = 12;
    private BroadcastReceiver bluetoothReceiver;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Client Connection");

        setContentView(R.layout.activity_client);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Vous n'avez pas le Bluetooth\nMode solo Activé", Toast.LENGTH_LONG).show();
            setResult(MultiPlayerActivity.CLIENT);
            finish();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ACTION);
        }
        
        bluetoothReceiver = new MyConnectedDevice(this);
        registerReceiver(bluetoothReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ACTION ) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Vous n'avez pas activé le Bluetooth\nMode solo Activé", Toast.LENGTH_LONG).show();
                setResult(MultiPlayerActivity.CLIENT);
                finish();
            }
        }
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.find:
                mBluetoothAdapter.startDiscovery();
                Toast.makeText(this, "Recherche d'Appareils", Toast.LENGTH_LONG).show();
                break;
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }
        unregisterReceiver(bluetoothReceiver);
    }


    @Override
    protected void onResume() {
        registerReceiver(bluetoothReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        super.onResume();

    }
}