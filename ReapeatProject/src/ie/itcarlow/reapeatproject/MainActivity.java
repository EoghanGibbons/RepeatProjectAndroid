package ie.itcarlow.reapeatproject;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;


public class MainActivity extends BaseGameActivity implements IUpdateHandler {
	
	private Camera camera;
	private ResourceManager resourceManager;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, 800, 480);
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 480), this.camera);
	    engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
	    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
	    return engineOptions;
	}

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}	

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		ResourceManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
	    resourceManager = ResourceManager.getInstance();
	    pOnCreateResourcesCallback.onCreateResourcesFinished();
	}


	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback){
		 SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
	{
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				SceneManager.getInstance().createMenuScene();
			}
		}));
		
		mEngine.registerUpdateHandler(this);
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	    System.exit(0);	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		if ( SceneManager.getInstance().getCurrentSceneType() == SceneManager.SceneType.SCENE_GAME) {
			GameScene scene = (GameScene) SceneManager.getInstance().getCurrentScene();
			if (scene.gameOver){
				SceneManager.getInstance().createGameOverScene();
			}
		}
		else if (SceneManager.getInstance().getCurrentSceneType() == SceneManager.SceneType.SCENE_MULTIPLAYER) {
			MultiPlayerGameScene scene = (MultiPlayerGameScene) SceneManager.getInstance().getCurrentScene();
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}