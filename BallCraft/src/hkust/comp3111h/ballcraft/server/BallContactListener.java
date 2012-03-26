package hkust.comp3111h.ballcraft.server;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class BallContactListener implements ContactListener
{

	
	public void beginContact(Contact arg0)
	{
		//TODO: bug here
		String string = "collision:";
		string += ServerGameState.getStateInstance().getUnits().indexOf(arg0.m_fixtureA.m_body);
		string += ",";	
		string += ServerGameState.getStateInstance().getUnits().indexOf(arg0.m_fixtureB.m_body);
		Server.extraMessage(string);
	}

	
	public void endContact(Contact arg0)
	{
	}

	
	public void postSolve(Contact arg0, ContactImpulse arg1)
	{
		// TODO Auto-generated method stub
		
	}

	
	public void preSolve(Contact arg0, Manifold arg1)
	{
		// TODO Auto-generated method stub
		
	}

}
