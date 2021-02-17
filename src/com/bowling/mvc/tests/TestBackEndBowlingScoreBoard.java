package com.bowling.mvc.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bowling.mvc.controller.BackEndBowlingScoreBoard;
import com.bowling.mvc.controller.BackEndBowlingScoreBoard.BowlingException;
import com.bowling.mvc.controller.BackEndBowlingScoreBoard.Frame;


// Junit tests for controller 
public class TestBackEndBowlingScoreBoard {

	BackEndBowlingScoreBoard helperObj = null;

	public BackEndBowlingScoreBoard testSetup() {
		helperObj = new BackEndBowlingScoreBoard();
		Frame frame9 = helperObj.new Frame();
		Frame frame10 = helperObj.new Frame();
		frame9.isStrike = false;
		helperObj.frameCounter = 10;

		frame9.scores[0] = 8;
		frame9.scores[1] = 1;
		frame9.noAttempts = 2;

		frame10.scores[0] = 8;
		frame10.scores[1] = 1;
		frame10.noAttempts = 2;
		frame10.isStrike = false;

		helperObj.frames.add(9, frame9);
		helperObj.frames.add(9, frame10);
		return helperObj;
	}

	public BackEndBowlingScoreBoard testSetupTwo() {
		helperObj = new BackEndBowlingScoreBoard();
		Frame frame9 = helperObj.new Frame();
		frame9.isStrike = true;
		helperObj.frameCounter = 9;

		frame9.scores[0] = 8;
		frame9.noAttempts = 1;

		helperObj.frames.add(9, frame9);
		return helperObj;
	}

	@Test
	public void testRollFive() {
		helperObj = new BackEndBowlingScoreBoard();
		helperObj.roll(5);
		assertEquals(helperObj.score(), 5);
	}

	@Test
	public void testSetupFrame() {
		helperObj = testSetup();
		assertEquals(helperObj.score(), 18);
	}

	@Test
	public void testBonusStrikeFrame() {
		helperObj = testSetupTwo();
		helperObj.roll(10);
		assertEquals(helperObj.score(), 18);
	}

	@Test
	public void testAllStrike() {
		helperObj = new BackEndBowlingScoreBoard();
		helperObj.strikeCounter = 10;
		helperObj.frameCounter = 9;
		assertEquals(helperObj.score(), 300);
	}

	@Test
	public void testAll() {
		helperObj = new BackEndBowlingScoreBoard();
		helperObj.frames.remove(9);
		helperObj.frameCounter = 8;

		helperObj.frames.get(7).isStrike = true;
		helperObj.frames.get(7).noAttempts = 2;
		helperObj.frames.get(7).noOfPins = 0;
		assertEquals(helperObj.score(), 0);

		helperObj.frames.get(7).isStrike = false;
		helperObj.frames.get(7).noAttempts = 2;
		helperObj.frames.get(7).noOfPins = 0;
		assertEquals(helperObj.score(), 0);
	}

	@Test
	public void testBonusFrame() {
		helperObj = new BackEndBowlingScoreBoard();

		helperObj.frames.get(9).noAttempts = 2;
		helperObj.frameCounter = 9;
		helperObj.frames.get(9).noOfPins = 0;
		helperObj.roll(10);
		assertEquals(helperObj.score(), 10);
	}

	@Test(expected = BowlingException.class)
	public void testMaxPins() {
		helperObj = new BackEndBowlingScoreBoard();
		helperObj.roll(11);
	}

	@Test(expected = BowlingException.class)
	public void testNullFrame() {
		helperObj = new BackEndBowlingScoreBoard();
		Frame frame = helperObj.new Frame();
		frame.noAttempts = 2;
		helperObj.frameCounter = 9;
		helperObj.frames.add(9, frame);
		helperObj.roll(1);
	}
}
