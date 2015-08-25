package ie.itcarlow.reapeatproject;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ball extends AnimatedSprite {
	private Body body;
	
	public Ball(float pX, float pY, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld)
	{
	    super(pX, pY, ResourceManager.getInstance().ball_region, vbo);
	    this.setWidth(20);
	    this.setHeight(20);
	    createPhysics(physicsWorld);
	}
	
	private void createPhysics(PhysicsWorld physicsWorld)
    {        
        body = PhysicsFactory.createCircleBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));//createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

        body.setUserData("ball");
        body.setFixedRotation(true);
        body.setLinearVelocity(10, 0);
        
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
        {
            @Override
            public void onUpdate(float pSecondsElapsed)
            {
            	//body.setLinearVelocity(body.getLinearVelocity().x * pSecondsElapsed, body.getLinearVelocity().y * pSecondsElapsed);
                super.onUpdate(pSecondsElapsed);
                //final long[] BALL_ANIMATE = new long[] { 100, 100, 100 };
                //animate(BALL_ANIMATE, 0, 7, true);
            }
        });
    }
	
	public void bounce(boolean axis){
		if (axis)
			body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y * -1);
		else
			body.setLinearVelocity(body.getLinearVelocity().x * -1, body.getLinearVelocity().y);
	}
}