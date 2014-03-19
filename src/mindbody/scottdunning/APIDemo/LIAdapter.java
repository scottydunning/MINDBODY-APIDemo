package mindbody.scottdunning.APIDemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Scott on 3/17/14.
 */
public class LIAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> names;

    public LIAdapter(Context context, ArrayList<String> names) {
        super(context, R.layout.my_list_layout, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String [] newNames = new String[names.size()];
        newNames = names.toArray(newNames);

        View rowView = inflater.inflate(R.layout.my_list_layout, parent, false);
        TextView leftTV = (TextView) rowView.findViewById(R.id.myListLayoutTV);
        //TextView rightTV = (TextView) rowView.findViewById(R.id.myListLayoutTV2);
        leftTV.setText(newNames[position]);
        //rightTV.setText("");

        return rowView;
    }

}