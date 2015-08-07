package de.apoth.anderoids.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * 
 * @author apoth
 *
 */
public abstract class BitmapGraphicObject extends GraphicsSpaceObject{
	 protected abstract Bitmap getImage();
	 
	 protected void myDrawFunction(Canvas c, Matrix mat)
	 {
		 Bitmap p = this.getImage();
		 int x = p.getWidth()/2, y = p.getHeight()/2;
		 mat.preTranslate(-x,-y);
		 c.drawBitmap(p, mat, null);
		 mat.preTranslate(x,y);
	 }
}
