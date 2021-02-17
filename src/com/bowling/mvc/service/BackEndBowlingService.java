//package com.bowling.mvc.service;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.bowling.mvc.controller.BackEndBowlingController;
//import com.bowling.mvc.controller.BackEndBowlingScoreBoard;
//
//public class BackEndBowlingService {
//	
//	BackEndBowlingController backEndBowlingController;
//	
//	BackEndBowlingScoreBoard singlePlayerGame = new BackEndBowlingScoreBoard();
//	int totalScore = 0;
//	int counter = 1;
//	int frameNbr = 1;
//	int rolls = 2;
//	Map<Integer, Integer> frameScores = new HashMap<>();
//	
//	public BackEndBowlingService(BackEndBowlingController backEndBowlingController) {
//		
//		this.backEndBowlingController = backEndBowlingController;
//		
//	}
//	
//	public void scoreCalculator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		com.bowling.mvc.controller.BackEndBowlingScoreBoard.Frame frame = singlePlayerGame.frames.get(frameNbr - 1);
//		boolean flag = false; 	 	
//		if (frameNbr < 11) {
//			int noOfPins = 0;
//			String ballRoll = request.getParameter("inputScore");
//			if (ballRoll.equals("Miss")) {
//				noOfPins = 0;
//				request.setAttribute("totalScore", totalScore);
//			} else if (ballRoll.equals("Strike")) {
//				noOfPins = 10;
//			} else if (ballRoll.equals("Spare"))
//				noOfPins = BackEndBowlingScoreBoard.MAX_PINS - Integer.valueOf(frameScores.get(frameNbr));
//			else
//				noOfPins = Integer.valueOf(ballRoll);
//
//			if (!frameScores.containsKey(frameNbr)) {
//				frameScores.put(frameNbr, noOfPins);
//			} else {
//				frameScores.put(frameNbr, frameScores.get(frameNbr) + noOfPins);
//				if (Integer.valueOf(frameScores.get(frameNbr)) == 10) {
//					request.setAttribute("totalScore", "");
//					flag = true;
//				}
//			}
//
//			singlePlayerGame.roll(noOfPins);
//			int currScore = singlePlayerGame.score();
//			int firstScore = frame.getFirstScore();
//			int secondScore = frame.getSecondScore();
//
//			if (!frame.isDone()) {
//				currScore = firstScore;
//			} else {
//				currScore = firstScore + secondScore;
//				totalScore += currScore;
//			}
//
////			if (!ballRoll.equals("Miss")) {
////				if (counter % 2 == 0) {
////					totalScore += currScore;
////					if (currScore == 10) {
////						ballRoll = "/";
////					}
////					currScore = totalScore;
////					if (!flag)
////						request.setAttribute("totalScore", totalScore);
////					rolls = 3;
////					frameNbr++;
////				} else {
////					if (currScore == 10) {
////						ballRoll = "X";
////						rolls = 3;
////					}
////					request.setAttribute("totalScore", totalScore + currScore);
////				}
////			}
//
//			if (currScore == 0) {
//				ballRoll = "-";
//			}
//			if (noOfPins == 10) {
//				rolls--;
//			}
//			if (totalScore == 0 || frame.noAttempts > 0 ||frame.isStrike) {
//				request.setAttribute("totalScore", "");
//			} else {
//				request.setAttribute("totalScore", totalScore);
//			}
//
//			request.setAttribute("rollsRemaining", --rolls);
//			request.setAttribute("inputPins", ballRoll);
//			request.setAttribute("frameNumber", frameNbr);
//			request.getRequestDispatcher("ScoreBoardView.jsp").forward(request, response);
//			backEndBowlingController.doGet(request, response);
//			counter++;
//		} else
//			response.sendRedirect("ErrorView.jsp");
//		if (rolls < 1) {
//			rolls = 3;
//		}
//		
//	}
//	
//	
//
//}
