# BowlingScoreKeeper
Java application using JSP/Servlets to design a simple interface to track the score of a single game of bowling. The interface will accept the result of each ball bowled and calculate the correct score, including spares and strikes.
The interface will also account for 10th frame includes a third ball.


## Bowling Game Rules
* A game of bowling consists of 10 frames, in two balls are rolled per frame to attempt to knock down all ten pins.
* If all ten pins are knocked down in those two turns in the 10th frame, an extra bonus chance is awarded.
* A strike is defined as knocking down all ten pins in the first ball of a frame, signified with an X
* A spare is defined as knocking down all the remaining pins in the second ball of a frame, signified with a /
* A miss is defined as zero pins being knocked down on a roll, signified with a -

## Scoring Rules
* Generally, 1 point per pin knocked down.
* Strike - Score of 10 (for knocking down all ten pins), plus the total of the next two rolls
* Spare - Score of 10, plus the total number of pins knocked down on the next roll only
* Each frame displays the cumulative score up to that point for all complete frames. If a frame has a strike or spare, the score for that frame is not displayed until sufficient subsequent rolls have been input

#TODO
* Only loads frame by frame, needs work on displaying previous frames
* score calculation is able to handle a variety of games but still needs some optimization
* UI is pretty basic and could be enhanced 

#Completed
* above 80% code coverage
* Handles variety of games
* Handles 10th frame (extra roll)
