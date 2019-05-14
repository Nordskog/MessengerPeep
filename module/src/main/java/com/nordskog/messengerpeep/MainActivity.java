package com.nordskog.messengerpeep;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Method;

/**
 * Created by Roughy on 3/23/2017.
 */

public class MainActivity extends Activity {


	private static final String DEVICE_OUT_WIRED_HEADPHONE_NAME  = "DEVICE_OUT_WIRED_HEADPHONE";
	private static final int DEVICE_OUT_WIRED_HEADPHONE  = 0x8;

	private static void minTestCase()
	{
		Log.i("AudioSystemTest", "Test case method called");

		try
		{
			Class audioSystemClass = Class.forName("android.media.AudioSystem");

			Method AudioSystemClass_setDeviceConnectionState =
					audioSystemClass.getMethod("setDeviceConnectionState", int.class, int.class, String.class, String.class);

			//Enable DEVICE_OUT_WIRED_HEADPHONE, forcing audio to headphones even if not detected.
			int response = (int)AudioSystemClass_setDeviceConnectionState.invoke(audioSystemClass, DEVICE_OUT_WIRED_HEADPHONE, 1, "", DEVICE_OUT_WIRED_HEADPHONE_NAME);

			Log.i("AudioSystemTest","Ret value: "+response);
		}
		catch (Exception ex)
		{
			Log.e("AudioSystemTest", ex.getMessage());
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create a GLSurfaceView instance and set it
		// as the ContentView for this Activity.
		setContentView(R.layout.main);

		Button b = (Button)findViewById(R.id.button);
		b.setOnClickListener( (View view) -> minTestCase());
	}
}