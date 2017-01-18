package com.survey;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.utilities.ConnectServer;
import com.utilities.G;
import com.utilities.Tag;

/*
 * Halaman Admin bagian Menambah dan Mengedit Data
 * Pengguna : Admin
 */

public class InfoAnak extends ActionBarActivity {

	/*
	 * Deklarasi variabel
	 */

	private EditText e_nama;
	private static EditText e_tgllahir;
	private static EditText e_tglperiksa;
	private static EditText e_umur;

	private String sNama;
	private String sTglLahir;
	private String sTglPeriksa;
	private String sUmur;

	private Button lanjut;

	static Calendar Lahir;
	private DatePickerDialog dialog = null;
	static int year, month, day;
	static Calendar c;
	// membuat variabel penyimpan password , secret question dan secret answer
		private SharedPreferences loginPreferences;
		private SharedPreferences.Editor loginPrefsEditor;
	    

		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.info_anak);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7f8c8d")));
		
		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();
		
		
		/*
		 * Inisiasi variabel komponen tampilan
		 */

		e_nama = (EditText) findViewById(R.id.nama);
		e_tgllahir = (EditText) findViewById(R.id.tglLahir);
		e_tglperiksa = (EditText) findViewById(R.id.tglPeriksa);
		e_umur = (EditText) findViewById(R.id.umur);
		lanjut = (Button) findViewById(R.id.next);

		c = Calendar.getInstance();
		
		setCurrentDateOnView() ;

		lanjut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Dashboard.class);
				
				// menyimpan preference
				loginPrefsEditor.putString("nama", e_nama.getText().toString());
				loginPrefsEditor.putString("umur", e_umur.getText().toString());
				
				loginPrefsEditor.commit();
				
				startActivity(i);

			}
		});

		e_tgllahir.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				showDatePickerDialog(v);

			}
		});

	}

	/*
	 * Mengambil isi inputan ke dalam variabel string
	 */

	public void TakeDataFromEditText() {
		sNama = e_nama.getText().toString();
		sTglLahir = e_tgllahir.getText().toString();
		sTglPeriksa = e_tglperiksa.getText().toString();
		sUmur = e_umur.getText().toString();

	}

	/*
	 * Fungsi untuk menyimpan dan mengupdate data
	 */
	public void Save() {

		/*
		 * Untuk Tambah data baru semua parameter dibutuhkan kecuali ID Untuk
		 * Update data menggunakan gambar baru maka semua parameter dibutuhkan,
		 * ID, dan EDIT =1 Untuk Update data menggunakan gambar lama maka semua
		 * parameter dibutuhkan, ID, dan EDIT =0
		 */

		if (e_nama.length() > 0 && e_tgllahir.length() > 0
				&& e_tglperiksa.length() > 0 && e_umur.length() > 0) {

			TakeDataFromEditText();

			final List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair(Tag.NAMA, sNama));
			params.add(new BasicNameValuePair(Tag.TGLLAHIR, sTglLahir));
			params.add(new BasicNameValuePair(Tag.TGLPERIKSA, sTglPeriksa));
			params.add(new BasicNameValuePair(Tag.UMUR, sUmur));

			new AsyncTask<ConnectServer, Long, Boolean>() {

				protected void onPreExecute() {
					G.n("Uploading..", getApplicationContext());
				};

				protected Boolean doInBackground(ConnectServer... apiConnectors) {
					return apiConnectors[0].uploadImageToserver(params);
				}

				protected void onPostExecute(Boolean result) {
					if (result == true) {
						G.n("Uploading Finish", getApplicationContext());

						Intent i = new Intent(getApplicationContext(),
								Survey.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					} else {
						G.n("Uploading Failed", getApplicationContext());
					}
				};
			}.execute(new ConnectServer());

		} else {
			G.n("Complete the data please", getApplicationContext());
		}
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year2, int month2, int day2) {
			// Do something with the date chosen by the user

			e_tgllahir.setText(new StringBuilder().append(day2).append("-")
					.append(month2 + 1).append("-").append(year2).append(" "));
			
			
			Date startDate = new Date(year2,month2,day2);
			Date endDate = new Date(year,month,day);
			
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(startDate);
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(endDate);

			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
			
			e_umur.setText(String.valueOf(diffMonth));
			
		}
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
		
	}

	public void setCurrentDateOnView() {

		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		e_tglperiksa.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(day).append("-").append(month + 1).append("-")
				.append(year).append(" "));

	}
	
	

}
