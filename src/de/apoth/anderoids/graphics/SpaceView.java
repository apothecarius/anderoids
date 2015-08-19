package de.apoth.anderoids.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.apoth.anderoids.logic.GameLogic;
import de.apoth.anderoids.logic.GameModes;
import de.apoth.anderoids.logic.GuiUpdateEvent;
import de.apoth.anderoids.logic.Position;
import de.apoth.anderoids.logic.ShipTypes;
import de.apoth.anderoids.logic.Messages.ChangeSetAssembler;
import de.apoth.anderoids.logic.input.AccelerometerSystem;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;;

public class SpaceView extends SurfaceView implements SurfaceHolder.Callback
{
	SpaceThread myThread;
	private static Random randy = new Random();
	long passedTime;
	
	private static Rect screenRect;

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
		GraphicSpaceship s=this.mySpaceship;
		s.setAngle(s.getAngle()+0.01f);
		canvas.drawColor(Color.BLACK);
		this.drawBackground(canvas);
		this.drawSpaceship(canvas);
		resetMiddleMatrix();//TODO see if this is really necessary
	}
	/**
	 * draws stars, smoke, and debris
	 * @param canvas
	 */
	private void drawBackground(Canvas canvas) {
		Matrix mat = new Matrix();
		for(Star s : this.stars)
		{
			s.draw(canvas, mat);
		}
	}
	private void drawSpaceship(Canvas canvas)
	{
		Matrix m = new Matrix();
		m.setRotate(this.mySpaceship.getAngle()*360);
		m.postTranslate(getScreenCenterPosition().getX(), 
				getScreenCenterPosition().getY());	
		this.mySpaceship.draw(canvas, m);
		
	}
	protected void updateObjects(List<GuiUpdateEvent> changes)
	{
		//die ersten zwei nachrichten abfragen
		//die erste ist immer die TimeChangeMessage
		//die zweite immer die LogicStepCountMessage
		
		
		//die objekte, deren position absolut gesetzt wurde,
		//sollten ihre position nicht durch schwung*numTicks aendern
		//sie muessen also markiert werden

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
		// TODO Auto-generated method stub
		
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
			Canvas c;
			while(_run)
			{
				
				GameLogic.get().setDeviceAngle(this.myAccelerometerSystem.getDeviceAngle());
				//GameLogic.get().getMySpaceShipID();
				_panel.updateObjects(ChangeSetAssembler.getChanges(
						GameLogic.get().getMySpaceShipID()));
				
				c = null;
				
				/*try
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
				}*/
				c = _surfaceHolder.lockCanvas();
				_panel.onDraw(c);
				_surfaceHolder.unlockCanvasAndPost(c);
			}
		}
		
	}/* class SpaceThread ends here */
	
	public static ArrayList<Star> generateStars()
	{
		Position center = getScreenCenterPosition();
		ArrayList<Star> retu = new ArrayList<Star>();
		float radius = getStarRadius()*2;
		System.out.println(radius);
		for(int i = 0; i < amountStars; i++)
		{
			Position starPos = new Position(randy.nextFloat()*radius, randy.nextFloat()*radius);
			starPos.subtract(center);
			Star toAdd = new Star(
					starPos,
					randy.nextFloat(),
					randy.nextInt() % BitmapManager.getNumStarPics());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
