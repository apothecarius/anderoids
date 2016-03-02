package de.apoth.anderoids.graphics;

import de.apoth.anderoids.resource.Position;
import android.graphics.Bitmap;

public class Star extends ParallaxBitmapGraphicObject {

	int starPicIndex;
	public Star(Position position, float dist, int imgIdx)
	{
		super(position, dist);
		//distance should be between .3 and .7

		this.starPicIndex = imgIdx;
	}
	@Override
	protected Bitmap getImage() {
		return BitmapManager.get().getStarPic(starPicIndex);
	}
	
}
