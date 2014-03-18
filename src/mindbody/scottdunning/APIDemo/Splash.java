package mindbody.scottdunning.APIDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Scott on 3/14/14.
 */
public class Splash extends Activity {

    private TextView versionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        versionTV = (TextView) findViewById(R.id.versionTV);

        try{
            String vName = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
            versionTV.setText("v" + vName);
        }catch(Exception e){
            e.printStackTrace();
        }

        Thread splashThread = new Thread(){
            public void run() {
                try{
                    int waited = 0;
                    while(waited < 3000){
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e){

                } finally {
                    finish();
                    Intent i = new Intent();
                    i.setClassName("mindbody.scottdunning.APIDemo",
                            "mindbody.scottdunning.APIDemo.Staff");
                    startActivity(i);
                }
            }
        };
        splashThread.start();
    }

}