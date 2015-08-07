package de.apoth.anderoids.gui;

import de.apoth.anderoids.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends Activity {

	OnClickListener confButtonListener;
	OnClickListener playButtonListener;
	OnClickListener scoreButtonListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		ImageButton conf = (ImageButton)findViewById(R.id.configButton);
		confButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadConfigLayout();}
		};
		conf.setOnClickListener(confButtonListener);
		
		ImageButton play = (ImageButton)findViewById(R.id.playButton);
		playButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadPlayLayout();}
		};
		play.setOnClickListener(playButtonListener);

		ImageButton score = (ImageButton)findViewById(R.id.scoreButton);
		scoreButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadScoreLayout();}
		};
		score.setOnClickListener(scoreButtonListener);
	}
	
	private void loadConfigLayout() {
		Intent selectGameModeIntent = new Intent(MenuActivity.this,ConfigActivity.class);
		startActivity(selectGameModeIntent);
	}

	private void loadPlayLayout() {
		Intent selectGameModeIntent = new Intent(MenuActivity.this,StartgameActivity.class);
		startActivity(selectGameModeIntent);
		
	}

	private void loadScoreLayout() {
		Intent selectGameModeIntent = new Intent(MenuActivity.this,ScoreActivity.class);
		startActivity(selectGameModeIntent);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
