package com.survey;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class About extends ActionBarActivity {

/*
 * Halaman About
 * Pengguna : User
 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if(id==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
