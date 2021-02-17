package com.bowling.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import com.bowling.mvc.model.BackEndBowling;

public final class BackEndBowlingScoreBoard implements BackEndBowling {

	public final List<Frame> frames;
	protected static final int MAX_FRAMES = 10;
	public static final int MAX_PINS = 10;
	protected static final int MAX_ATTEMPTS_PER_FRAME = 2;
	public int frameCounter = 0;
	public int strikeCounter = 0;
	protected static final int ALL_STRIKE_SCORE = 300;

	/**
	 * setup the game with valid 10 frames(MAX_FRAMES)
	 */
	public BackEndBowlingScoreBoard() {

		frames = new ArrayList<Frame>(MAX_FRAMES);

		for (int i = 0; i < MAX_FRAMES; i++) {
			frames.add(new Frame());
		}
	}

	@Override
	public void roll(int noOfPins) {

		if (noOfPins > MAX_PINS) {
			throw new BowlingException("illegal argument " + noOfPins);
		}

		Frame frame = getFrame();

		if (frame == null) {
			throw new BowlingException("all attempts exhausted - start new game");
		}

		frame.setScore(noOfPins);

		if (isBonusFrame()) {
			Frame prev = getPreviousFrame();
			// restrict to one attempt, when last frame was spare
			if (prev.isSpare()) {
				frame.limitToOneAttempt();
			}
		}

	}

	/**
	 * returns a frame and moves to next frame if current has used up attempts
	 * 
	 * 
	 */
	private Frame getFrame() {

		Frame frame = getCurrentFrame();

		if (frame.isDone()) {

			// new bonus frame
			if (isLastFrame() && (frame.isSpare() || frame.isStrike())) {
				Frame bonus = new Frame();
				frames.add(bonus);
				frameCounter++;
				return bonus;
			}

			frameCounter++;
			if (frameCounter == MAX_FRAMES || isBonusFrame()) {
				return null;
			}

			frame = getCurrentFrame();
		}

		return frame;
	}

	@Override
	public int score() {

		int score;

		// first frame
		if (frameCounter == 0) {

			Frame curr = getCurrentFrame();
			return curr.score();

		} else {

			// score 300, strikes for all frames
			if (isLastFrame() && isAllStrikes()) {
				return ALL_STRIKE_SCORE;
			}

			Frame curr = getCurrentFrame();
			Frame prev = getPreviousFrame();

			// only add previous last frame to current score
			if (isBonusFrame()) {
				return prev.score() + curr.score();
			}

			score = curr.score();

			if (prev.isSpare()) {
				score += (prev.score() + curr.getFirstScore());
			}

			if (prev.isStrike()) {
				score = (prev.score() + curr.getFirstScore() + curr.getSecondScore());
			}

		}

		return score;
	}

	private Frame getPreviousFrame() {
		return frames.get(frameCounter - 1);
	}

	private Frame getCurrentFrame() {
		return frames.get(frameCounter);
	}

	private boolean isAllStrikes() {
		return strikeCounter == MAX_FRAMES;
	}

	private boolean isBonusFrame() {
		return frames.size() > MAX_FRAMES;
	}

	private boolean isLastFrame() {
		return frameCounter == MAX_FRAMES - 1;
	}

	/**
	 * class to encapsulate Frame and manages score and attempts allowed for each
	 * frame
	 */
	public class Frame {

		public int[] scores = new int[MAX_ATTEMPTS_PER_FRAME];
		public int noOfPins = 10;
		public int noAttempts = 0;
		public boolean isStrike = false;

		protected boolean isSpare() {
			return noOfPins == 0 && noAttempts == MAX_ATTEMPTS_PER_FRAME && !isStrike;
		}

		protected boolean isStrike() {
			return noOfPins == 0 && noAttempts == MAX_ATTEMPTS_PER_FRAME && isStrike;
		}

		public boolean isDone() {
			return noAttempts == MAX_ATTEMPTS_PER_FRAME;
		}

		protected void setScore(int score) {

			scores[noAttempts++] = score;
			noOfPins -= score; // keep track of remaining pins/frame

			if (score == MAX_PINS) {
				isStrike = true;
				strikeCounter++;
			}
		}

		protected void limitToOneAttempt() {
			scores[1] = 0;
			noAttempts++;
		}

		protected int score() {
			return scores[0] + scores[1];
		}

		public int getFirstScore() {
			return scores[0];
		}

		public int getSecondScore() {
			return scores[1];
		}

	}

	/**
	 * Represents an exception for the bowling game
	 */
	public class BowlingException extends RuntimeException {

		public BowlingException(String message) {
			super(message);
		}

	}

}
