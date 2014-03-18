package mindbody.scottdunning.APIDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Scott on 3/14/14.
 */
public class Appointments extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.removeItem(R.id.homeMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.cancel:
                return true;

            case R.id.about:
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
