package upec.projetandroid2017_2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MultiPlayerActivity extends AppCompatActivity {

    public static final int SERVER = 0;
    public static final int CLIENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);

        Button create = (Button) findViewById(R.id.button);
        Button join = (Button) findViewById(R.id.button1);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(startServerActivity(),SERVER);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(startClientActivity(),CLIENT);
            }
            });
        }

        public Intent startServerActivity(){
            return new Intent(this,ServeurActivity.class);
        }

        public Intent startClientActivity(){
            return new Intent(this,ClientActivity.class);
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch(requestCode){
                case SERVER:
                    if(resultCode == SERVER){
                        this.finish();
                    }
                    break;
                case CLIENT:
                    if(resultCode == CLIENT){
                        this.finish();
                    }
                    break;
            }
        }
}
