package mindbody.scottdunning.APIDemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Scott on 3/18/14.
 */
public class ApptLIAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> clientNames, apptType, apptDate, apptStart, apptEnd;

    public ApptLIAdapter(Context context, ArrayList<String> clientNames, ArrayList<String> apptType,
                     ArrayList<String> apptDate, ArrayList<String> apptStart, ArrayList<String> apptEnd) {

        super(context, R.layout.my_list_layout_2, clientNames);
        this.context = context;
        this.clientNames = clientNames;
        this.apptType = apptType;
        this.apptDate = apptDate;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String [] newNames = new String[clientNames.size()];
        newNames = clientNames.toArray(newNames);
        String [] newType = new String[apptType.size()];
        newType = apptType.toArray(newType);
        String [] newDate = new String[apptDate.size()];
        newDate = apptDate.toArray(newDate);
        String [] newStart = new String[apptStart.size()];
        newStart = apptStart.toArray(newStart);
        String [] newEnd = new String[apptEnd.size()];
        newEnd = apptEnd.toArray(newEnd);

        View rowView = inflater.inflate(R.layout.my_list_layout_2, parent, false);
        TextView clientNameTV = (TextView) rowView.findViewById(R.id.clientNameTV);
        TextView typeTV = (TextView) rowView.findViewById(R.id.typeTV);
        TextView dateTV = (TextView) rowView.findViewById(R.id.dateTV);
        TextView timeTV1 = (TextView) rowView.findViewById(R.id.timeTV1);
        TextView timeTV2 = (TextView) rowView.findViewById(R.id.timeTV2);
        clientNameTV.setText(newNames[position]);
        typeTV.setText(newType[position]);
        dateTV.setText(newDate[position]);
        timeTV1.setText(newStart[position]);
        timeTV2.setText((newEnd[position]));

        return rowView;
    }

}