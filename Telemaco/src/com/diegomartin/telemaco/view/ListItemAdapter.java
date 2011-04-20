package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.model.IListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter<IListItem> {
	private ArrayList<IListItem> items;
	private Context context;
	
	public ListItemAdapter(Context context, int textViewResourceId, ArrayList<IListItem> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_item, null);
		}
		IListItem obj = items.get(position);
		if (obj != null) {
			TextView ttitulo = (TextView) v.findViewById(R.id.name);
			TextView tautor = (TextView) v.findViewById(R.id.description);
			if (ttitulo != null) ttitulo.setText(obj.getName());
			if (tautor != null) tautor.setText(obj.getDescription());
		}
		return v;
	}
}
