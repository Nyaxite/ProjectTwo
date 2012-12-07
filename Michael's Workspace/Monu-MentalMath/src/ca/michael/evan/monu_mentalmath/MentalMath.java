package ca.michael.evan.monu_mentalmath;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MentalMath extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mental_math);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mental_math, menu);
		return true;
	}

}
