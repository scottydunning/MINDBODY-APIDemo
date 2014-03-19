package mindbody.scottdunning.APIDemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Scott on 3/14/14.
 */
public class Appointments extends ListActivity {

    private String NAMESPACE, METHOD, URL, sourcename, key, siteID, staffName, staffPass;
    private String [] SessionType, ClientFName, ClientLName;
    private ArrayList<String> clientNames = new ArrayList<String>(),
            apptType = new ArrayList<String>(), apptDate = new ArrayList<String>(),
            apptStart = new ArrayList<String>(), apptEnd = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments);

        NAMESPACE = this.getResources().getString(R.string.namespace);
        METHOD = this.getResources().getString(R.string.method2);
        URL = this.getResources().getString(R.string.url2);
        sourcename = this.getResources().getString(R.string.sourcename);
        key = this.getResources().getString(R.string.key);
        siteID = this.getResources().getString(R.string.siteID);

        staffName = getIntent().getStringExtra("Name");
        staffPass = getIntent().getStringExtra("Pass");

        GetAppt runner = new GetAppt();
        String sleepTime = String.valueOf(1000);
        runner.execute(sleepTime);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.cancel:
                return true;

            case R.id.homeMenu:
                Intent homeIntent = new Intent(this, Staff.class);
                startActivity(homeIntent);
                finish();
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

    }

    public class GetAppt extends AsyncTask<String, String, String> {
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

            setListAdapter(new ApptLIAdapter(Appointments.this, clientNames, apptType, apptDate,
                    apptStart, apptEnd));

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
        SoapObject StaffIDs = new SoapObject();
        SoapObject Filters = new SoapObject();

        SourceCredentials.addProperty("SourceName", sourcename);
        SourceCredentials.addProperty("Password", key);
        SiteIDs.addProperty("int", siteID);
        SourceCredentials.addProperty("SiteIDs", SiteIDs);

        StaffCredentials.addProperty("Username", staffName);
        StaffCredentials.addProperty("Password", staffPass);
        StaffCredentials.addProperty("SiteIDs", SiteIDs);


        StaffIDs.addProperty("long", 0);

        Filters.addProperty("StaffFilter", "StaffViewable");
        Filters.addProperty("StaffFilter", "AppointmentInstructor");

        Request.addProperty("SourceCredentials", SourceCredentials);
        Request.addProperty("StaffCredentials", StaffCredentials);

        Request.addProperty("StartDate", "2014-01-03");
        Request.addProperty("EndDate", "2014-01-03");


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
            SoapObject Appointments = (SoapObject) response.getProperty("Appointments");
            SessionType = new String[Appointments.getPropertyCount()];
            ClientFName = new String[Appointments.getPropertyCount()];
            ClientLName = new String[Appointments.getPropertyCount()];

            for(int i = 0; i < Appointments.getPropertyCount(); i++){
                SoapObject Appointment = (SoapObject) Appointments.getProperty(i);
                String startSplit = Appointment.getProperty("StartDateTime").toString();
                String [] SplitStart = startSplit.split("T");
                startSplit = SplitStart[1];
                for(int index = 0; index < 3; index++){
                    startSplit = startSplit.substring(0, startSplit.length()-1);
                }
                apptStart.add(startSplit);
                startSplit = SplitStart[0];
                apptDate.add(startSplit);
                String endSplit = Appointment.getProperty("EndDateTime").toString();
                String [] SplitEnd = endSplit.split("T");
                endSplit = SplitEnd[1];
                for(int index = 0; index < 3; index++){
                    endSplit = endSplit.substring(0, endSplit.length()-1);
                }
                apptEnd.add(endSplit);
                SoapObject SessionTypeObj = (SoapObject) Appointment.getProperty("SessionType");
                SessionType[i] = SessionTypeObj.getProperty("Name").toString();
                apptType.add(SessionType[i]);
                SoapObject ClientObj = (SoapObject) Appointment.getProperty("Client");
                ClientFName[i] = ClientObj.getProperty("FirstName").toString();
                ClientLName[i] = ClientObj.getProperty("LastName").toString();
                clientNames.add(ClientFName[i] + " " + ClientLName[i]);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent staffIntent = new Intent(this, Staff.class);
        startActivity(staffIntent);
        finish();
    }

}
