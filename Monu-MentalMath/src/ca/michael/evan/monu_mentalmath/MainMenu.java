package ca.michael.evan.monu_mentalmath;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity 
{
	final Context context = this;
	
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
        
        mentalMathButton = (Button) findViewById(R.id.mentalMathButton);
        mentalMathButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				// define a new Intent
				Intent intent = new Intent(context, MentalMath.class);
				startActivity(intent);
			} // end onClick inner class
		}); // end onClickListener
        
        fleetingFiguresButton = (Button) findViewById(R.id.fleetingFiguresButton);
        fleetingFiguresButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				// define a new Intent
				Intent intent = new Intent(context, FleetingFigures.class);
				startActivity(intent);
			} // end onClick inner class
		}); // end onClickListener
        
        instructButton = (Button) findViewById(R.id.instructionsButton);
        instructButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v)
			{
				// define a new Intent
				Intent intent = new Intent(context, Instructions.class);
				startActivity(intent);
			} // end onClick inner class
		}); // end onClickListener
        
        scoresScreenButton = (Button) findViewById(R.id.scoreScreenButton);
        scoresScreenButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				// define a new Intent
				Intent intent = new Intent(context, ScoreScreen.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
}
