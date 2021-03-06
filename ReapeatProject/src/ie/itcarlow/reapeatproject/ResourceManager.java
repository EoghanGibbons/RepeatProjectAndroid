package ie.itcarlow.reapeatproject;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class ResourceManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourceManager INSTANCE = new ResourceManager();
    
    public Engine engine;
    public MainActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    
    private BitmapTextureAtlas splashTextureAtlas;   
    private BitmapTextureAtlas gameOverTextureAtlas;
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    public static Font font;
    public static SharedPreferences mSharedPref;
    
    //audio
    public static Sound bounceSound;
    public static Music menuMusic;
    
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    
    //Splash Screen
    public ITextureRegion splash_region;
    //Gameover Screen
    public ITextureRegion gameOver_region;
    
    //Menu
    public ITextureRegion menu_background_region;
    public ITextureRegion singlePlayer_region;
    public ITextureRegion multiPlayer_region;
    
    //Game
    public BuildableBitmapTextureAtlas gameTextureAtlas;
    public ITextureRegion player_region;
    public ITextureRegion brick_region;
    public ITextureRegion HUD_region;
    public ITextureRegion boundry_region;
    public ITextureRegion wall_region;
    public ITiledTextureRegion ball_region;
    public ITextureRegion game_background_region;
    public boolean new_highscore = false;
    public Font gameFont;
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    public void loadMenuResources()
    {
    	loadMenuGraphics();
        loadMenuAudio();
    }
    
    public void loadGameResources()
    {
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }
    
    private void loadMenuGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("");
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "BreakoutBackground.png");
    	singlePlayer_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "SinglePlayer.png");
    	multiPlayer_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "MultiPlayer.png");
    	
    	try {
    	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.menuTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e){
    	        Debug.e(e);
    	}
    }

    public void loadMenuTextures()
    {
        menuTextureAtlas.load();
    }
    
    private void loadMenuAudio(){
    	MusicFactory.setAssetBasePath("");
        try {
        	menuMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "mainMenuMusic.mp3");
		}  catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void unloadGameTextures()
    {
    }
    
    private void loadGameGraphics()
    {
    	gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
    	player_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "player.png");
    	brick_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "brick.png");
    	game_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "BreakoutBackground.png");
    	ball_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "ball.png", 8, 4);
    	HUD_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "HUD.png");
    	boundry_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "floor.png");
    	wall_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "wall.png");
    	
    	try 
        {
            gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            gameTextureAtlas.load();
        } 
        catch (final TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }
    }
    
    private void loadGameFonts()
    {
    	FontFactory.setAssetBasePath("");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        gameFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 16, true, Color.WHITE, 2, Color.BLACK);
        gameFont.load();
    }
    
    private void loadGameAudio()
    {
    	SoundFactory.setAssetBasePath("");
        try {
			bounceSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "boing.wav");
			
			//bounceSound.setLoopCount(0);
		}  catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void loadSplashScreen(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("");
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
    	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
    	splashTextureAtlas.load();
    }
    
    public void unloadSplashScreen(){
    	splashTextureAtlas.unload();
    	splash_region = null;
    }
    
    public void loadGameOverScreen(){
    }
    
    public void unloadGameOverScreen(){
    	gameOverTextureAtlas.unload();
    	gameOver_region = null;
    }
    
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
        
        mSharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        
        FontFactory.setAssetBasePath("");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourceManager getInstance()
    {
        return INSTANCE;
    }
    
    public void saveHighScore(int highScore) {
		 mSharedPref = activity.getPreferences(Context.MODE_PRIVATE);
		 SharedPreferences.Editor editor = mSharedPref.edit();		
		 editor.putInt(activity.getString(R.string.saved_high_score), highScore);
		 editor.commit();
	 }
    
    public int getHighScore(){
		 int defaultValue = 0;
		 int highScore = ResourceManager.mSharedPref.getInt(activity.getString(R.string.saved_high_score), defaultValue);
		 return highScore;
	 }
}