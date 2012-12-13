/*
*	Name: SplashScreen (ca.michael.evan.monu_mentalmath / SplashScreen.java)
*	Authors: Michael Burnie, Evan Pugh
*	Date (due): 2012/12/14
*	Description: The splash screen is shown for 4 seconds using a custom made 
*				 image and a slide in and out effect.
*/
package ca.michael.evan.monu_mentalmath;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

public class SplashScreen extends Activity
{
	private static final int SPLASHSCREEN_DISP_TIME = 4000;//4 second delay on Splash Screen
	
	/**
	 * This method is called when the activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				Intent mainActivityIntent = new Intent(SplashScreen.this, MainMenu.class);
				SplashScreen.this.startActivity(mainActivityIntent);//start this activity
				
				// end the splash screen activity so it can't be seen again
				SplashScreen.this.finish();
				
				// slide the main menu in, and fade the splash screen out
				overridePendingTransition(R.anim.slidein, R.anim.slideout);
			}
		}, SPLASHSCREEN_DISP_TIME);
	}
}
