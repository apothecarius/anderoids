package de.apoth.anderoids.graphics;

import de.apoth.anderoids.logic.Position;
import android.graphics.Bitmap;

public class Star extends BitmapGraphicObject {

	int starPicIndex;
	float distance;
	public Star(Position position, float dist, int imgIdx)
	{
		this.pos = position;
		//distance should be between .3 and .7
		this.distance = dist;
		this.distance %= .4;
		this.distance += .3;

		this.starPicIndex = imgIdx;
	}
	@Override
	protected Bitmap getImage() {
		return BitmapManager.get().getStarPic(starPicIndex);
	}
	
	@Override
	protected void move(Position p)
	{
		this.pos.addWithFactor(p,distance);
	}
}
