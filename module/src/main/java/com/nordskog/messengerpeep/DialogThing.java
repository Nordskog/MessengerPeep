package com.nordskog.messengerpeep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

public class DialogThing
{



	public static void showDialogGetString(Context ctxt, String title, OnDialogResultListener listener)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ctxt);
		builder.setTitle(title);

		final EditText input = new EditText(ctxt);

		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		//buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.onResult( input.getText().toString() );
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();	// I don't think this is necessary
			}
		});

		builder.show();
	}

	public interface OnDialogResultListener
	{
		void onResult(String result);
	}

	public static void DoThing( Context context, String title )
	{
		showDialogGetString(context, title, result -> Log.i("ASdfa", "ASDfsdsf") );
	}


}
