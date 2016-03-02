package de.apoth.anderoids.graphics;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import de.apoth.anderoids.resource.Position;

/**
 * Objects with this superclass will be drawn with a fracture of their position.
 * This gives an illusion of depth.
 *
 * Created by apoth on 1/19/16.
 */
public abstract class ParallaxBitmapGraphicObject extends BitmapGraphicObject {

    private float distance;

    /**
     * Use this function to draw objects
     *
     * @param p
     */
    public ParallaxBitmapGraphicObject(Position p, float d) {
        super(p);
        this.distance = d;
    }



    @Override
    protected void move(Position p) {
        this.pos.addWithFactor(p,this.distance);
    }

}
