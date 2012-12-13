/*
*	Name: Main Menu (ca.michael.evan.monu_mentalmath / Main Menu.java)
*	Authors: Michael Burnie, Evan Pugh
*	Date (due): 2012/12/14
*	Description: Displays and functions the navigational buttons of Monu-Mental Math.
*/
package ca.michael.evan.monu_mentalmath;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity 
{
	final Context context = this;
	
	//create Button variables
	Button mentalMathButton;
	Button fleetingFiguresButton;
	Button scoresScreenButton;
	Button exitButton;
	Button instructButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        
        //Assign the buttons to their GUI counterpart and give them their own listener to start another activity on click.
        mentalMathButton = (Button) findViewById(R.id.mentalMathButton);
        mentalMathButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				// define a new Intent
				Intent intent = new Intent(context, MentalMath.class);//start the Mental Math activity
				startActivity(intent);
			} // end onClick inner class
		}); // end onClickListener
        
        fleetingFiguresButton = (Button) findViewById(R.id.fleetingFiguresButton);
        fleetingFiguresButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				// define a new Intent
				Intent intent = new Intent(context, FleetingFigures.class);//start the Fleeting Figures activity
				startActivity(intent);
			} // end onClick inner class
		}); // end onClickListener
        
        instructButton = (Button) findViewById(R.id.instructionsButton);
        instructButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v)
			{
				// define a new Intent
				Intent intent = new Intent(context, Instructions.class);//start the Instructions activity
				startActivity(intent);
			} // end onClick inner class
		}); // end onClickListener
        
        scoresScreenButton = (Button) findViewById(R.id.scoreScreenButton);
        scoresScreenButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				// define a new Intent
				Intent intent = new Intent(context, ScoreScreen.class);//start the Score Screen activity
				startActivity(intent);
			} // end onClick inner class
		}); // end onClickListener
        
        exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				//exit to home
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} // end onClick inner class
		}); // end onClickListener
        
        
    }
  
}
