package fr.bmartel.android.notti;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bertrand Martel
 */
public class ScanItemArrayAdapter extends ArrayAdapter<BluetoothDevice> {

    List<BluetoothDevice> scanningList = new ArrayList<>();

    private static LayoutInflater inflater = null;

    public ScanItemArrayAdapter(Context context, int textViewResourceId,
                                List<BluetoothDevice> objects) {
        super(context, textViewResourceId, objects);

        this.scanningList = objects;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.listview_item, null);
                holder = new ViewHolder();

                holder.deviceAddress = (TextView) vi.findViewById(R.id.text1);
                holder.deviceName = (TextView) vi.findViewById(R.id.text2);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.deviceAddress.setText(scanningList.get(position).getAddress());
            holder.deviceName.setText(scanningList.get(position).getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vi;
    }

    public List<BluetoothDevice> getDeviceList() {
        return scanningList;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public int getCount() {
        return scanningList.size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public static class ViewHolder {
        public TextView deviceAddress;
        public TextView deviceName;
    }

}