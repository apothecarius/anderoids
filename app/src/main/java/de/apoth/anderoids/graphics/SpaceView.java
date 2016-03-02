package de.apoth.anderoids.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.apoth.anderoids.logic.GameLogic;
import de.apoth.anderoids.logic.GameModes;
import de.apoth.anderoids.resource.Position;
import de.apoth.anderoids.logic.ShipTypes;
import de.apoth.anderoids.logic.events.Event;
import de.apoth.anderoids.logic.events.MovePerspectiveEvent;
import de.apoth.anderoids.logic.input.AccelerometerSystem;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;;

public class SpaceView extends SurfaceView implements SurfaceHolder.Callback
{
	SpaceThread myThread;
	private static Random randy = new Random();
	long passedTime;
	
	private static Rect screenRect;
	private static int starRadius;
	Integer mySpaceShipID = 0;//the perspective follows this object
	private static final int amountStars = 120;
	ArrayList<Star> stars;
	private GraphicSpaceship mySpaceship;
	private Matrix middleOffset = new Matrix();
	
	SpaceView(Context context) {super(context);}

	public SpaceView(Context context, ShipTypes shipType, GameModes gameMode)
	{
		super(context);
		passedTime = 0;
		screenRect = new Rect();
		getWindowVisibleDisplayFrame(screenRect);
		starRadius = screenRect.height()+screenRect.width();//arbitrary but functioning
		BitmapManager.setup(this);
		this.stars = generateStars();
		
		this.mySpaceship = new GraphicSpaceship(shipType);
		resetMiddleMatrix();
		//this.mySpaceship
		
		myThread = new SpaceThread(getHolder(), this);
		getHolder().addCallback(this);
	}
	private void resetMiddleMatrix() {
		Position p = getScreenCenterPosition();
		this.middleOffset.setTranslate(p.getX(), p.getY());
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
        Matrix mat = new Matrix();
        //positions are set relative to position of spaceship
        mat.postTranslate(getScreenCenterPosition().getX() / 2,
                getScreenCenterPosition().getY() / 2);
		GraphicSpaceship s=this.mySpaceship;
		s.setAngle(s.getAngle() + 0.01f);
		canvas.drawColor(Color.BLACK);
		this.drawBackground(canvas, mat);
		this.drawSpaceship(canvas, mat);
		//resetMiddleMatrix();//TODO see if this is really necessary
	}
	/**
	 * draws stars, smoke, and debris
	 * @param canvas
	 */
	private void drawBackground(Canvas canvas, Matrix mat) {
        Matrix m = new Matrix();
		for(Star s : this.stars)
		{
			s.draw(canvas, mat);
		}
	}
	private void drawSpaceship(Canvas canvas, Matrix mat)
	{
		Matrix m = new Matrix(mat);
		m.setRotate(this.mySpaceship.getAngle()*360);
		//m.postTranslate(getScreenCenterPosition().getX(),
		//		getScreenCenterPosition().getY());
		this.mySpaceship.draw(canvas, m);
		
	}
	protected void updateObjects(List<Event> changes)
	{
        for (Event ev: changes)
        {
            if(ev.getClass() == MovePerspectiveEvent.class)
            {
                Position delta = ((MovePerspectiveEvent) ev).getDistance();
                this.middleOffset.preTranslate(delta.getX(),delta.getY());
                this.moveStars(delta);
            }
        }
		//die ersten zwei nachrichten abfragen
		//die erste ist immer die TimeChangeMessage
		//die zweite immer die LogicStepCountMessage
		
		
		//die objekte, deren position absolut gesetzt wurde,
		//sollten ihre position nicht durch schwung*numTicks aendern
		//sie muessen also markiert werden

	}

    private void moveStars(Position delta) {
        for(Star s : this.stars)
        {
            s.move(delta);
			if(isOutsideVisibility(s))
			{
				repositionStar(s);
			}
        }
    }

	private void repositionStar(Star s) {
		Position p = s.pos;
		s.pos = new Position(-p.getX(),-p.getY());
	}

	private boolean isOutsideVisibility(Star s)
	{
		float starDist = s.pos.getX() + s.pos.getY();
		starDist *= starDist;

		starDist = (float)Math.sqrt((double)starDist);
		return starDist > starRadius;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		
		myThread.setRunning(true);
		myThread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		boolean retry = true;
		myThread.setRunning(false);
		while(retry)
		{
			try
			{
				myThread.join();
				retry = false;
			} catch (InterruptedException e)
			{
				//pass
			}
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	
	@SuppressLint("WrongCall")
	class SpaceThread extends Thread
	{
		private SpaceView _panel;
		private SurfaceHolder _surfaceHolder;
		private boolean _run = false;
		private AccelerometerSystem myAccelerometerSystem = null;
		
		
		public SpaceThread(SurfaceHolder surfaceHolder, SpaceView panel) {
			_surfaceHolder = surfaceHolder;
			_panel = panel;
			
			AccelerometerSystem.checkForAccelerometer(panel.getContext());
			this.myAccelerometerSystem = new AccelerometerSystem(panel.getContext());
		}
		public void setRunning(boolean run) {
			_run = run;
		}
		
		@Override
		public void run() 
		{
			Canvas c = null;
			while(_run)
			{
				//TODO measure time it took the device to render a frame and adjust gamelogic stepsize
				GameLogic.get().elapseTime();
				GameLogic.get().setDeviceAngle(this.myAccelerometerSystem.getDeviceAngle());
				GameLogic.get().executeEventsUntilNow();
				
				//GameLogic.get().getMySpaceShipID();
				_panel.updateObjects(GameLogic.get().getVisibleChanges(_panel.mySpaceShipID));
				

				try
				{
					c = _surfaceHolder.lockCanvas(null);
					synchronized(_surfaceHolder)
					{
						_panel.onDraw(c);
					}
				}
				finally
				{
					if(c != null)
						_surfaceHolder.unlockCanvasAndPost(c);
				}
/*				c = _surfaceHolder.lockCanvas();
				_panel.onDraw(c);
				_surfaceHolder.unlockCanvasAndPost(c);*/
			}
			
			// cleanup when the activity is closed due to game end
			myAccelerometerSystem.deactivateSystem(getContext());
		}
		
	}/* class SpaceThread ends here */
	
	private static ArrayList<Star> generateStars() {
        Position center = getScreenCenterPosition();
        ArrayList<Star> retu = new ArrayList<Star>();
        float radius = getStarRadius() * 2;
        System.out.println(radius);
        for (int i = 0; i < amountStars; i++) {
            //TODO evenly distribute points in sphere
            Position starPos = new Position(
                    (randy.nextFloat() * radius) -getStarRadius(),
                    (randy.nextFloat() * radius) - getStarRadius());
            //starPos.subtract(center);
            //distance should lie between 0.7 and 0.8
            float distance = randy.nextFloat();
            distance /= 10.0f;
            distance += 0.7f;
            Star toAdd = new Star(
                    starPos,
                    distance,
                    randy.nextInt() % BitmapManager.getNumStarPics());
            Log.d("star",starPos.toString());
            retu.add(toAdd);
        }
        return retu;
    }

	public static int getStarRadius()
	{
		return (screenRect.height() + screenRect.width())/2;
		
	}
	public static Position getScreenCenterPosition()
	{
		Position retu = new Position(
				screenRect.centerX(),
				screenRect.centerY());
		return retu;
	}
	
	public void joinThread()
	{
		this.myThread.setRunning(false);
		try {
			this.myThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
