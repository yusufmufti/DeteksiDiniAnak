package com.survey;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.utilities.ConnectServer;
import com.utilities.G;
import com.utilities.Tag;



/*
 * Halaman Admin bagian Menambah dan Mengedit Data
 * Pengguna : Admin
 */

public class SurveyPendengaran extends ActionBarActivity   implements OnClickListener, OnCheckedChangeListener{
	
	/*
	 * Deklarasi variabel
	 */


    private TextView kegiatan;
    private Button lanjut;
    
    RadioButton ya, tidak;
    RadioGroup radio;
    String[] exe;
	int[] nilai = new int[10];
	int n = 0;
	int benar = 0, salah = 0;
	List<Object> list;
	String[] s = new String[4];

	int awal;
	Boolean next2;
	String answer="";
	
	
	// membuat variabel penyimpan password , secret question dan secret answer
			private SharedPreferences loginPreferences;
			private SharedPreferences.Editor loginPrefsEditor;
		
   
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.survey_pendengaran);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7f8c8d")));
		
		
		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();
		
		
		/*
		 * Inisiasi variabel komponen tampilan
		 */
		
			kegiatan		= (TextView)findViewById(R.id.kegiatan);
			lanjut		= (Button)findViewById(R.id.next);
			lanjut.setOnClickListener(this);
			
			Initialize();
		
		
	
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if(id==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	

	
	
	
	
	private void Initialize() {
		ya = (RadioButton) findViewById(R.id.ya);
		tidak = (RadioButton) findViewById(R.id.tidak);
		
		radio = (RadioGroup) findViewById(R.id.jawaban);
		radio.setOnCheckedChangeListener(this);

		int umur = Integer.parseInt(loginPreferences.getString("umur", ""));
		
		if (umur<6){
			loginPrefsEditor.putString("jumlahPertanyaanPendengaran", String.valueOf("3"));
			loginPrefsEditor.putInt("jumlahSoal", 3);
			loginPrefsEditor.commit();
			
			exe = getResources().getStringArray(R.array.pendengar6bulan);
			
		}else{
			loginPrefsEditor.putString("jumlahPertanyaanPendengaran", String.valueOf("4"));
			loginPrefsEditor.putInt("jumlahSoal", 4);
			loginPrefsEditor.commit();
			
			exe = getResources().getStringArray(R.array.pendengar9bulan);
			
		}
		
		int jumlahSoal = loginPreferences.getInt("jumlahSoal", 0);
		list = new ArrayList<Object>(jumlahSoal);
		for (int i = 0; i < jumlahSoal; i++) {
			list.add(i);
		}

		showExe(Integer.valueOf(list.get(n).toString()));
	}

	private void showExe(int r) {
		s = exe[r].split("#");
		kegiatan.setText(s[0]);
		ya.setText(s[1]);
		tidak.setText(s[2]);
		n++;
	}

	public void countAnswer() {
		int jumlahSoal = loginPreferences.getInt("jumlahSoal", 0);
		
		String jawab = "";
		next2 = false;
		
		if (ya.isChecked()) {
			jawab = "ya";
			G.l("Ya- dipilih");
			next2 = true;
			
		} else if (tidak.isChecked()) {
			jawab = "tidak";
			G.l("Tidak- dipilih");
			next2 = true;
			
		} else {
			next2 = false;
		}

		if ((n <= jumlahSoal) && (next2)) {
			
			if (jawab.equals("ya")) {
				nilai[n - 1] = 1;
				benar++;
				G.l("Benar");
			} else if(jawab.equals("tidak")){
				nilai[n - 1] = 0;
				salah++;
				G.l("Salah");
			}
			
			G.l("Jawab : "+jawab +", kunci : "+s[3]+", benar : "+benar+", salah : "+salah);
		}

		if ((n < jumlahSoal)) {
			
			if (next2) {
				showExe(Integer.valueOf(list.get(n).toString()));
			} else {
				
				Toast.makeText(this, "Belum pilih jawaban", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			tampilNilai();
			
			if (n <= jumlahSoal) {
				n++;
			}else{
				
			}
		}
		radio.clearCheck();

	}

	

	void tampilNilai() {
		// TODO Auto-generated method stub
		int kumulatif = 0;

		for (int i = 0; i < nilai.length; i++) {
			kumulatif += nilai[i];
			
		}
		
		loginPrefsEditor.putString("kumulatifPendengaran", String.valueOf(kumulatif));
		loginPrefsEditor.commit();
		
		
		
		Intent i = new Intent(getApplicationContext(), ResultPendengaran.class);
		startActivity(i);
		
		finish();
		
		

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.next:
			
			countAnswer();
			
			break;
		

		default:
			break;
		}	
	}

	
	
	
	
	
	
	

	
}


