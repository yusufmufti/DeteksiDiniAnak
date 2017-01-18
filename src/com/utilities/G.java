package com.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class G {
/*
 * Untuk membuat log ketika testing
 */
	public G(){
		
	}
	
	public static void l(String message){
		Log.e("Deteksi Dini- 15 Maret", message);
	}
	public static void n(String message, Context ctx){
		Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
	}
}
