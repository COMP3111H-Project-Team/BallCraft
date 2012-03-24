package hkust.comp3111h.ballcraft.server;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class BallContactListener implements ContactListener
{

	@Override
	public void beginContact(Contact arg0)
	{
		Server.msg += ServerGameState.getStateInstance().getUnits().indexOf(arg0.m_fixtureA);
		Server.msg += ",";	
		Server.msg += ServerGameState.getStateInstance().getUnits().indexOf(arg0.m_fixtureB);
	}

	@Override
	public void endContact(Contact arg0)
	{
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1)
	{
		// TODO Auto-generated method stub
		
	}

}
