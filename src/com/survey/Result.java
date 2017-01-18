package com.survey;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoadingListener;
import com.utilities.Tag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Halaman Informasi detail tentang Lokasi tempat ibadah
 * Pengguna : User
 */

public class Result extends ActionBarActivity {
	
	
	/*
	 * Deklarasi variabel
	 */
	private TextView hasil;
	private TextView nama;
	private TextView umur;
	
	// membuat variabel penyimpan password , secret question dan secret answer
			private SharedPreferences loginPreferences;
			private SharedPreferences.Editor loginPrefsEditor;
		
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		//Remove title bar
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.result);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7f8c8d")));
		
		/*
		 * Inisiasi variabel tampilan
		 */
		nama			= (TextView)findViewById(R.id.nama);
		umur			= (TextView)findViewById(R.id.umur);
		hasil			= (TextView)findViewById(R.id.hasil);
		
		nama.setText(loginPreferences.getString("nama", ""));
		umur.setText(loginPreferences.getString("umur", "")+" bulan");
		hasil.setText(loginPreferences.getString("kumulatif", ""));
		
		int nilai = Integer.parseInt(loginPreferences.getString("kumulatif", ""));
		
		if(nilai>8){
			hasil.setText("Jawaban Ya : "+nilai +" , Tidak : "+(10-nilai) +"\n\n"+getString(R.string.hasil1));
		}else if (nilai >6){
			hasil.setText("Jawaban Ya : "+nilai +" , Tidak : "+(10-nilai)+"\n\n"+getString(R.string.hasil2));
		}else{
			hasil.setText("Jawaban Ya : "+nilai +" , Tidak : "+(10-nilai)+"\n\n"+getString(R.string.hasil3));
		}
	
		
		

	}


	/*
	 * Membuat menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	

}
