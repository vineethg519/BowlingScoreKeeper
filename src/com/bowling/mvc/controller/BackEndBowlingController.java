package com.bowling.mvc.controller;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BackEndBowlingController
 */
@WebServlet("/BackEndBowlingController")
public class BackEndBowlingController extends HttpServlet {
	BackEndBowlingScoreBoard singlePlayerGame = new BackEndBowlingScoreBoard();
	int totalScore = 0;
	int frameNbr = 1;
	int rolls = 2;
	Map<Integer, Integer> frameScores = new HashMap<>();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BackEndBowlingController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// regular expression for filtering the input and
		// TODO- needs more validation
		String regex = "^[smSM1-9][a-zA-Z]{0,5}$";
		Pattern p = Pattern.compile(regex);

		String ballRoll = request.getParameter("inputScore").toLowerCase();
		Matcher m = p.matcher(ballRoll);
		try {

			if (!m.matches()) {
				// simple error view
				response.sendRedirect("ErrorView.jsp");
			} else {

				if (singlePlayerGame.frameCounter < 10) {
					int noOfPins = 0;
					if (ballRoll.equals("miss")) {
						noOfPins = 0;
					} else if (ballRoll.equals("strike")) {
						noOfPins = 10;
					} else if (ballRoll.equals("spare"))
						noOfPins = BackEndBowlingScoreBoard.MAX_PINS
								- Integer.valueOf(frameScores.get(singlePlayerGame.frameCounter));
					else
						noOfPins = Integer.valueOf(ballRoll);

					// Roll the ball
					singlePlayerGame.roll(noOfPins);

					com.bowling.mvc.controller.BackEndBowlingScoreBoard.Frame frame = singlePlayerGame.frames
							.get(singlePlayerGame.frameCounter);

					// TODO logic for storing cumulative rolls score
					if (!frameScores.containsKey(singlePlayerGame.frameCounter)) {
						frameScores.put(singlePlayerGame.frameCounter, noOfPins);
					} else {
						frameScores.put(singlePlayerGame.frameCounter,
								frameScores.get(singlePlayerGame.frameCounter) + noOfPins);
					}

					// Calculate Score
					int currScore = singlePlayerGame.score();

					rolls = frame.noAttempts;
					if (currScore == 0 || noOfPins == 0) {
						ballRoll = "-";
					}
					if (noOfPins == 10) {
						rolls = 2;
						frame.limitToOneAttempt();
					}
					if (frame.isDone()) {
						totalScore += currScore;
						if (singlePlayerGame.frameCounter == 9 && frame.isStrike || frame.isSpare()) {

						} else {
							frameNbr++;
						}
					}
					if (frame.noOfPins == 0) {
						if (frame.isStrike) {
							ballRoll = "X";
							request.setAttribute("totalScore", "");
						} else if (frame.isSpare()) {
							ballRoll = "/";
							request.setAttribute("totalScore", "");
						}

					} else if (frame.noOfPins >= 1 && frame.isDone()) {
						request.setAttribute("totalScore", totalScore);
					} else {
						request.setAttribute("totalScore", "");
					}

					// display score when last frame is done
					if (singlePlayerGame.frameCounter == 9 && frame.isDone()) {
						if (frame.isStrike || frame.isSpare()) {
							request.setAttribute("totalScore", "");
						} else {
							request.setAttribute("totalScore", totalScore);
						}

					} else if (singlePlayerGame.strikeCounter >= 10) {
						request.setAttribute("totalScore", BackEndBowlingScoreBoard.ALL_STRIKE_SCORE);
					}

					// rolls remaining for each frame and each roll
					request.setAttribute("rollsRemaining", rolls);
					// knocked out pins for a roll
					request.setAttribute("inputPins", ballRoll);
					// frame number
					request.setAttribute("frameNumber", frameNbr);
					request.getRequestDispatcher("ScoreBoardView.jsp").forward(request, response);
					doGet(request, response);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Bowling board broken! Please re-start game");
		}

	}
}
