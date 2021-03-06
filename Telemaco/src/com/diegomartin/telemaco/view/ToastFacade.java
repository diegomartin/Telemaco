package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;

import android.content.Context;
import android.widget.Toast;

public class ToastFacade {
	public static void show(Context c, String txt){
		Toast t = Toast.makeText(c, txt, Toast.LENGTH_LONG);
		t.show();
	}

	public static void show(Context c, Exception e) {
		show(c, c.getString(R.string.error) + ' ' + e.toString());
	}
}
