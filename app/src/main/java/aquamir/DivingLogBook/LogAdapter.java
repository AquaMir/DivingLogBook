package aquamir.DivingLogBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by aquamir on 2015-11-03.
 */
public class LogAdapter extends ArrayAdapter<DiveLog> {
    private ArrayList<DiveLog> datas;
    private Context context;

    public LogAdapter(Context context, int resource, ArrayList<DiveLog> items) {
        super(context, resource, items);
        this.datas = items;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_layout, null);
        }
        DiveLog data = datas.get(position);
        if (data != null) {
            TextView id = (TextView) v.findViewById(R.id.listId);
            TextView point = (TextView) v.findViewById(R.id.listPoint);
            TextView country = (TextView) v.findViewById(R.id.listCountry);
            if (id != null) {
                Integer val = datas.size() - position;
                id.setText(val.toString());
            }
            if (point != null) {
                point.setText(data.getPoint());
            }
            if (country != null) {
                country.setText(data.getCountry());
            }
        }
        return v;
    }
}
