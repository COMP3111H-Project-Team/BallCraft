package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Ball;

public class Player 
{
	Ball ball;
	Player(Ball ball)
	{
		this.ball = ball;
	}
	
	public Ball getBall()
	{
		return ball;
	}
}
