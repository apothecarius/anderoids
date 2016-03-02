package de.apoth.anderoids.gui;

import de.apoth.anderoids.R;
import de.apoth.anderoids.logic.Difficulties;
import de.apoth.anderoids.logic.GameModes;
import de.apoth.anderoids.logic.GameOptionNames;
import de.apoth.anderoids.logic.ShipTypes;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class StartgameActivity extends Activity{

	OnClickListener startButtonListener; 
	OnSeekBarChangeListener difficultyChangeListener;
	ImageButton startGameButton;
	private int selectedDifficulty = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		assert(Difficulties.Medium.ordinal() == selectedDifficulty);
		setContentView(R.layout.activity_startgame);
		
		ImageButton start = (ImageButton)findViewById(R.id.startButton);
		startButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				startGame();}
		};
		start.setOnClickListener(startButtonListener);
		
		SeekBar difficultyBar = (SeekBar)findViewById(R.id.difficultyBar);
		startGameButton = (ImageButton)findViewById(R.id.startButton);
		difficultyBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				//set difficulty
				int diff;
				if(progress == 0)
					diff = R.drawable.start_easy;
				else if(progress == 1)
					diff = R.drawable.start_medium;
				else
					diff = R.drawable.start_hard;
				
				assert(diff == R.drawable.start_easy || 
						diff == R.drawable.start_medium ||
						diff == R.drawable.start_hard);
				startGameButton.setImageResource(diff);
				selectedDifficulty = progress;
				System.out.println(selectedDifficulty);
				
			}
		});
	}
	
	private void startGame()
	{
		Intent selectGameModeIntent = new Intent(StartgameActivity.this,SpaceActivity.class);
		//TODO setup buttons for these options and check them
		selectGameModeIntent.putExtra(GameOptionNames.gameMode, GameModes.Hunt.ordinal());
		selectGameModeIntent.putExtra(GameOptionNames.shipType, ShipTypes.Fighter.ordinal());
		
		selectGameModeIntent.putExtra(GameOptionNames.difficulty, this.selectedDifficulty);
		
		startActivity(selectGameModeIntent);
		this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

/*	@SuppressLint("Assert")
	public void setDifficulty(int diff)
	{
		
	}*/

}
