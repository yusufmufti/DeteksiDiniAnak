package com.survey;

import com.utilities.G;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/*
 * Halaman login 
 * Pengguna : Admin
 */
public class Dashboard extends ActionBarActivity{
	
	private Button btnKsps;
	private Button btnPendengaran;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7f8c8d")));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		

		btnKsps=(Button)findViewById(R.id.btnKSPS);
		btnPendengaran=(Button)findViewById(R.id.btnPendengaran);
		
		btnKsps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(), Survey.class);
					startActivity(i);
					
				
			}
		});
		
		btnPendengaran.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(), SurveyPendengaran.class);
					startActivity(i);
					
				
			}
		});
		
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
