package de.apoth.anderoids.graphics;

import de.apoth.anderoids.resource.Position;
import de.apoth.anderoids.logic.ShipTypes;
import android.graphics.Bitmap;

public class GraphicSpaceship extends BitmapGraphicObject {

	private ShipTypes myType;
	private final float maxAngle = 1.0f;
	private float angle;
	public GraphicSpaceship(ShipTypes type)
	{
		super(new Position());
		this.myType = type;
		this.angle = 0;
	}
	@Override
	protected Bitmap getImage() {
		return BitmapManager.get().getSpaceshipPic(this.myType);
	}
	public boolean setAngle(float a)
	{
		//if(a < maxAngle || a > 0)
		//	return false;
		a %= 1.0;
		this.angle = a;
		return true;
	}
	public float getAngle(){return this.angle;}

}
