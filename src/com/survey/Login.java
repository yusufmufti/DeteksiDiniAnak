package com.survey;

import com.utilities.G;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
/*
 * Halaman login 
 * Pengguna : Admin
 */
public class Login extends ActionBarActivity{
	private EditText editPassword;
	private EditText editUsername;
	
	private Button btnMasuk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_login);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7f8c8d")));
		

		editUsername=(EditText)findViewById(R.id.editUsername);
		editPassword=(EditText)findViewById(R.id.editPassword);
		btnMasuk=(Button)findViewById(R.id.btnMasuk);
		
		btnMasuk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (editUsername.getText().toString().equalsIgnoreCase("admin") && editPassword.getText().toString().equals("12345")){
					G.n("Anda berhasil login", getApplicationContext());
					Intent i = new Intent(getApplicationContext(), InfoAnak.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
					finish();
				}
			}
		});
		
	}
	
	
	
	
	
	

}
