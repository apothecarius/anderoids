package de.apoth.anderoids.gui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import de.apoth.anderoids.R;

public class ConfigActivity extends Activity {
	
	SharedPreferences.Editor cfgEdit;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		SharedPreferences configs = getPreferences(MODE_PRIVATE);
		cfgEdit = configs.edit();
		
		SeekBar seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
		
		seekBar1.setProgress(configs.getInt("dummyProgress", 0));
		
		
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			@TargetApi(Build.VERSION_CODES.GINGERBREAD)
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				// TODO Auto-generated method stub
				cfgEdit.putInt("dummyProgress", progress);
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
				{
					cfgEdit.apply();
				}
				else
				{
					cfgEdit.commit();
				}
			}
		});
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		
		return true;
		
	}

}
