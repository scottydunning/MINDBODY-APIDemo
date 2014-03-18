package mindbody.scottdunning.APIDemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Scott on 3/14/14.
 */
public class About extends Activity {

    private TextView emailTV, webTV, vTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        vTV = (TextView) findViewById(R.id.vTV);

        try{
            String vName = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
            vTV.setText("v" + vName);
        }catch(Exception e){
            e.printStackTrace();
        }

        emailTV = (TextView) findViewById(R.id.emailTV);
        emailTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = getResources().getString(R.string.contactEmail);
                String app_name = getResources().getString(R.string.app_name);
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, app_name);
                startActivity(Intent.createChooser(emailIntent, "Send an email in:"));
            }
        });

        webTV = (TextView) findViewById(R.id.webTV);
        webTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String webLink = getResources().getString(R.string.contactWebLink);
                Uri uri = Uri.parse(webLink);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(uri);
                startActivity(Intent.createChooser(browserIntent, "Open with:"));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.removeItem(R.id.about);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.homeMenu:
                finish();
                return true;

            case R.id.cancel:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}