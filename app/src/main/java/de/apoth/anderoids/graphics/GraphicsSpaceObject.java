package de.apoth.anderoids.graphics;

import android.graphics.Canvas;
import android.graphics.Matrix;
import de.apoth.anderoids.resource.Position;

/**
 * This class should be used for every object
 * that gets drawn, but has nothing to do 
 * with the game logic because it doesnt have
 * and effect on the actual game. They can be
 * disabled in the optionsmenu to improve the performance
 * 
 * a generic graphics object states that it must be able to draw this
 * but it doesnt state how
 * @author apoth
 *
 */
public abstract class GraphicsSpaceObject
{
	protected Position pos;


	public GraphicsSpaceObject(Position p)
	{
		this.pos = p;
	}
    /**
     * Use this function to draw objects
     * @param c
     * @param mat
     */
	void draw(Canvas c,Matrix mat)
	{
		float x = this.pos.getX();
		float y = this.pos.getY();
		mat.postTranslate(x,y);
		this.myDrawFunction(c, mat);
		mat.postTranslate(-x,-y);
	}

	/**
	 * This function is the protected function that you must
	 * implement in subclasses. Its job is to actually paint things,
	 * whereas the public function only applies and reverts 
	 * positionchanges to the given matrix, which has to be done
	 * independently of the drawing-algorithm 
	 * @param c
	 * @param mat
	 */
	protected abstract void myDrawFunction(Canvas c, Matrix mat);

	protected void move(Position p) {
		this.pos.add(p);
		
	} 
}



