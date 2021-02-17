package com.bowling.mvc.model;

public interface BackEndBowling {

	/**
	 * Keeps track of pins for each ball bowled 
	 * @param ballRoll 
	 */
	public void roll(int ballRoll);

	/**
	 *
	 * @return score of current frame only
	 */
	public int score();

}