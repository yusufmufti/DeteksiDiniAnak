package com.survey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
	/*
	 * Halaman cover awal aplikasi
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		// Thread for displaying Splash Screen
		Thread splash_screen = new Thread(){
			
			public void run(){
				try{
					sleep(5000);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					startActivity(new Intent(getApplicationContext(),Login.class));
					finish();
				}
			}
		};
		splash_screen.start();
	}

	
}
