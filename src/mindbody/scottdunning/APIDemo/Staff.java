package mindbody.scottdunning.APIDemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;

public class Staff extends ListActivity{

    private String NAMESPACE, METHOD, URL, sourcename, username, key, password, siteID;
    private String [] name, ids, first, last;
    private ArrayList<String> listNames = new ArrayList<String>(),
            listIds = new ArrayList<String>(), firstNames = new ArrayList<String>(),
            lastNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff);

        NAMESPACE = this.getResources().getString(R.string.namespace);
        METHOD = this.getResources().getString(R.string.method1);
        URL = this.getResources().getString(R.string.url1);
        sourcename = this.getResources().getString(R.string.sourcename);
        username = this.getResources().getString(R.string.username);
        key = this.getResources().getString(R.string.key);
        password = this.getResources().getString(R.string.password);
        siteID = this.getResources().getString(R.string.siteID);

        GetStaff runner = new GetStaff();
        String sleepTime = String.valueOf(1000);
        runner.execute(sleepTime);

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String [] namesList = new String[listNames.size()];
        namesList = listNames.toArray(namesList);

        String [] idsList = new String[listIds.size()];
        idsList = listIds.toArray(namesList);
        String selectedId = idsList[position];

        String [] firstList = new String[firstNames.size()];
        firstList = firstNames.toArray(namesList);
        String selectedFirst = firstList[position];

        String [] lastList = new String[lastNames.size()];
        lastList = lastNames.toArray(namesList);
        String selectedLast = lastList[position];

        String staffUserName = selectedFirst + "." + selectedLast;
        selectedFirst = selectedFirst.toLowerCase();
        selectedLast = selectedLast.toLowerCase();
        char fst = selectedFirst.charAt(0);
        char lst = selectedLast.charAt(0);
        String staffPassword = String.valueOf(fst) + String.valueOf(lst) + selectedId;

        Intent intent = new Intent(this, Appointments.class);
        intent.putExtra("Name", staffUserName);
        intent.putExtra("Pass", staffPassword);
        startActivity(intent);
        finish();
    }

    public class GetStaff extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            publishProgress("Working....");

            try{
                SoapRequest(URL, NAMESPACE, METHOD);


            } catch (Exception e){
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setListAdapter(new LIAdapter(Staff.this, listNames));

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

    public void SoapRequest(String URL, String NAMESPACE, String METHOD){

        SoapObject Request = new SoapObject();
        SoapObject SourceCredentials = new SoapObject();
        SoapObject SiteIDs = new SoapObject();
        SoapObject StaffCredentials = new SoapObject();
        SoapObject Filters = new SoapObject();

        SourceCredentials.addProperty("SourceName", sourcename);
        SourceCredentials.addProperty("Password", key);
        SiteIDs.addProperty("int", siteID);
        SourceCredentials.addProperty("SiteIDs", SiteIDs);

        StaffCredentials.addProperty("Username", username);
        StaffCredentials.addProperty("Password", password);
        StaffCredentials.addProperty("SiteIDs", SiteIDs);


        Filters.addProperty("StaffFilter", "StaffViewable");
        Filters.addProperty("StaffFilter", "AppointmentInstructor");

        Request.addProperty("SourceCredentials", SourceCredentials);

        String SOAP_ACTION = NAMESPACE + "/" + METHOD;
        SoapObject request = new SoapObject(NAMESPACE, METHOD);
        request.addProperty("Request", Request);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);
        HttpTransportSE transportSE = new HttpTransportSE(URL);
        transportSE.debug = true;

        try{
            transportSE.call(SOAP_ACTION, envelope);

            SoapObject response = (SoapObject) envelope.getResponse();
            SoapObject staffMembers = (SoapObject) response.getProperty("StaffMembers");

            name = new String[staffMembers.getPropertyCount()];
            ids = new String[staffMembers.getPropertyCount()];
            first = new String[staffMembers.getPropertyCount()];
            last = new String[staffMembers.getPropertyCount()];

            for(int i = 0; i < staffMembers.getPropertyCount(); i++){
                SoapObject staff = (SoapObject) staffMembers.getProperty(i);
                name[i] = staff.getProperty("Name").toString();
                ids[i] = staff.getProperty("ID").toString();
                first[i] = staff.getProperty("FirstName").toString();
                last[i] = staff.getProperty("LastName").toString();
            }


            for(int i = 0; i < staffMembers.getPropertyCount(); i++){
                if(Integer.parseInt(ids[i]) > 0){
                    listNames.add(name[i]);
                    listIds.add(ids[i]);
                    firstNames.add(first[i]);
                    lastNames.add(last[i]);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
