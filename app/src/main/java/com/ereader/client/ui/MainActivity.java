package com.ereader.client.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.ereader.client.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}
}
