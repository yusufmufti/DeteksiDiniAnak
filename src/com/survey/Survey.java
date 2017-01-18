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

public class Survey extends ActionBarActivity   implements OnClickListener, OnCheckedChangeListener{
	
	/*
	 * Deklarasi variabel
	 */


    private TextView kegiatan;
    private TextView kategori;
    private ImageView picture;
    private Button lanjut;
    
    RadioButton ya, tidak;
    RadioGroup radio;
    String[] exe;
	int[] nilai = new int[10];
	int n = 0;
	int benar = 0, salah = 0;
	List<Object> list;
	String[] s = new String[6];

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

		setContentView(R.layout.survey);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7f8c8d")));
		
		
		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();
		
		
		/*
		 * Inisiasi variabel komponen tampilan
		 */
		
			picture 	= (ImageView)findViewById(R.id.image);
			kegiatan		= (TextView)findViewById(R.id.kegiatan);
			kategori		= (TextView)findViewById(R.id.kategori);
			lanjut		= (Button)findViewById(R.id.next);
			lanjut.setOnClickListener(this);
			
			Initialize();
		
		
	
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.save) {
			
			Save();
		}
		if(id==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	

	
	
	/*
	 * Fungsi untuk menyimpan dan mengupdate data
	 */
	public void Save(){
	
		
		
		if(answer.length()>0 ){
			
			
			 final List<NameValuePair> params = new ArrayList<NameValuePair>();
		        
		        params.add(new BasicNameValuePair(Tag.s1, "1"));
		        params.add(new BasicNameValuePair(Tag.s2, "1"));
		        params.add(new BasicNameValuePair(Tag.s3, "1"));
		        params.add(new BasicNameValuePair(Tag.s4, "1"));
		        params.add(new BasicNameValuePair(Tag.s5, "1"));
		        params.add(new BasicNameValuePair(Tag.s6, "1"));
		        params.add(new BasicNameValuePair(Tag.s7, "1"));
		        params.add(new BasicNameValuePair(Tag.s8, "1"));
		        params.add(new BasicNameValuePair(Tag.s9, "1"));
		        params.add(new BasicNameValuePair(Tag.s10, "1"));
		         
		       

		        new AsyncTask<ConnectServer,Long, Boolean >() {
		        	
		        	protected void onPreExecute() {
		        		G.n("Uploading..", getApplicationContext());
		        	};
		        	
		            protected Boolean doInBackground(ConnectServer... apiConnectors) {
		                return apiConnectors[0].uploadImageToserver(params);
		            }
		            
		            protected void onPostExecute(Boolean result) {
		            	if(result==true){
		            		G.n("Uploading Finish",getApplicationContext());
		            		
		            		Intent i = new Intent(getApplicationContext(), Result.class);
		            		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            		startActivity(i);
		            	}else{
		            		G.n("Uploading Failed",getApplicationContext());
		            	}
		            };
		        }.execute(new ConnectServer());
			
			
		}else{
			G.n("Complete the data please", getApplicationContext());
		}
	}
	
	private void Initialize() {
		ya = (RadioButton) findViewById(R.id.ya);
		tidak = (RadioButton) findViewById(R.id.tidak);
		
		radio = (RadioGroup) findViewById(R.id.jawaban);
		radio.setOnCheckedChangeListener(this);

		int umur = Integer.parseInt(loginPreferences.getString("umur", ""));
		
		if (umur<4){
			exe = getResources().getStringArray(R.array.soal);
			
		}else if(umur <7){
			exe = getResources().getStringArray(R.array.soal6bulan);
			
		}else{
			exe = getResources().getStringArray(R.array.soal6bulan);
			
		}
		
		list = new ArrayList<Object>(10);
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}

		showExe(Integer.valueOf(list.get(n).toString()));
	}

	
	private void showExe(int r) {
		s = exe[r].split("#");
		kegiatan.setText(s[0]);
		ya.setText(s[1]);
		tidak.setText(s[2]);
		kategori.setText("\nKategori : "+s[4]);
		Log.e("s5", s[5]);
		
		if(!s[5].equals("null")){
		
			//int id = getResources().getIdentifier("yourpackagename:drawable/" + s[5], null, null);
			
			String uri = "@drawable/"+s[5];  // where myresource (without the extension) is the file

			int imageResource = getResources().getIdentifier(uri, null, getPackageName());

			
			Drawable res = getResources().getDrawable(imageResource);
			picture.setImageDrawable(res);
			
			
			//picture.setImageResource(id);
			picture.setVisibility(View.VISIBLE);
		}else{
			picture.setVisibility(View.GONE);
		}
		n++;
		
		
		
	}
	

	public void countAnswer() {
	
		String jawab = "";
		next2 = false;
		
		if (ya.isChecked()) {
			jawab = ya.getText().toString();
			next2 = true;
		} else if (tidak.isChecked()) {
			jawab = tidak.getText().toString();
			next2 = true;
		} else {
			next2 = false;
		}

		if ((n <= 10) & (next2)) {
			if (jawab.equalsIgnoreCase(s[3])) {
				nilai[n - 1] = 1;
				benar++;
			} else {
				nilai[n - 1] = 0;
				salah++;
			}
		}

		if ((n < 10)) {
			
			if (next2) {
				showExe(Integer.valueOf(list.get(n).toString()));
			} else {
				
				Toast.makeText(this, "Belum pilih jawaban", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			tampilNilai();
			
			if (n <= 10) {
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
		
		loginPrefsEditor.putString("kumulatif", String.valueOf(kumulatif));
		loginPrefsEditor.commit();
		
		
		Intent i = new Intent(getApplicationContext(), Result.class);
		startActivity(i);
		finish();
		
		

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub

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


