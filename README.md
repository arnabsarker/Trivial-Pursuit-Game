# Trivial-Pursuit-Game
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: arnabs
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. You may copy and paste from your proposal
  document if you did not change the features you are implementing.

  1. I/O Parsing
     I/O Parsing is used by the QuestionSet.java class in order to determine
     the questions from a text file. This is an appropriate use of the concept
     because there are over 300 questions in the game and it makes sense to
     parse through the data to determine the questions rather than type each
     question and answer by hand.

  2. JUnit Testing
     JUnit Testing is used to test primarily the moveOptions() function in
     Board.java, as well as the design of the QuestionSet.java class. To test
     moveOptions(), it makes sense to use JUnit testing because of the various
     different results the method can output. For testing QuestionSet.java, JUnit
     testing is appropriate because there is a large amount of data and I mainly
     wanted to test whether or not questions were removed from the set properly,
     so JUnit Testing was a much better option than using gameplay to determine
     what questions were still in the set.

  3. Object Oriented Design
     Object Oriented Design is used with the Tile.java class as the superclass
     and InnerTile.java, WedgeTile.java, and WinningTile.java as its subclasses.
     I use this hierarchy because tiles share the same methods, but specific tiles
     needed to use different methods at different points. For example, the wedge
     tiles needed an additional method to give wedges to player. Also, the
     instanceof operation was used multiple times to make sure these methods could
     be used and determine a player's behavior based on the tile they were on.

  4. 2D Array
     A 2D Array was used to model the playing board. Because the board had an outer
     ring and inner paths, a 2D Array is appropriate to model the board since it
     can use varying lengths for the inner arrays. Also, the 2D Array consists of
     Tiles, so it can keep track of each tile's category and subtype (if applicable)
     making it appropriate for modeling the board for Board.java


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Board.java
  This class is used to store the array for the board as well as its users
  and the current roll of the player. It is analagous to GameCourt.java
  in the sample game, as it creates the initial state of the game and creates
  the playing space for the players.
  
  Game.java
  This class is used to instantiate all of the Swing objects and run the program
  using the invokeLater method.
  
  QType.java
  This enumerated type is used to represent the different categories of questions
  and the winning tile. These types are used to determine which Set of questions
  should be presented to the user.
  
  Tile.java
  This class is used to instantiate the perimeter tiles other than wedge tiles in
  the board. These tiles each have a QType, and when a user lands on the tile they
  will be asked a question with the same category as that tile's QType.
  
  InnerTile.java
  An Inner Tile is a specific instance of a Tile, and is primarily used to 
  distinguish where a tile is on the board, since a player can only be on an
  inner tile under specific circumstances.
  
  WedgeTile.java
  This class creates specific kinds of tiles that can give a player a wedge if the
  player answers the question correctly. A player must collect a wedge from each 
  category in order to be able to travel to an inner tile.
  
  WinningTile.java
  This class is another instance of a tile that has a method that wins the game when
  the related question is answered quickly. Additionally, this class uses the 
  QType WIN so its questions have any type rather than only one type.
  
  Player.java
  This class is used to keep track of the data for each player. Each player has a 
  Set of QTypes representing their wedges. Using a Set ensures that the same wedge 
  tile can't give two wedges to one player, because a Set can't have two instances
  of the same value.
  
  Question.java
  This class is used to store data for each question. It contains the isCorrect()
  method which determines if a user's input for a question is correct.
  
  QuestionSet.java
  This class goes through the text files in order to determine the set of questions
  that will be used in the game. It uses I/O parsing to do so.
  
  TurnListener.java
  This class is an action listener that is used to start a player's turn. It causes
  another panel to appear that is used to determine where the player can move the
  piece next.
  
  MoveListener.java
  This class is another action listener that actually moves the piece and updates
  the board by repainting it afterwards.
  
  QuestionListener.java
  This class is an action listener that lets the user input data into a JTextField
  and based on the user's response, acts appropriately.
  
- Revisit your proposal document. What components of your plan did you end up
  keeping? What did you have to change? Why?
  
  I kept most of the components of my plan, but I did change the categories
  from being represented as strings to being represented as eunumerated types.
  I did this in order to prevent confusion in my code.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  The hardest part of my design was implementing the graphics for each player.
  I ended up having to use parametric equations and linear transformations to
  determine where the circle should be drawn, which was very difficult to plan.
  Also, it was difficult to figure out how to make a JLabel display more than one
  line, but the Swing library helped me figure that out.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  There is a decent amount of separation of functionality in this design, and it
  follows the MVC separation of functionality fairly well. Private state is 
  generally encapsulated, but there are instances where I probably could return
  copies of sets instead of the actual sets, such as in getting tile options for the 
  board class. I also had to write down some code twice for moving the pieces, so
  I feel that there is a more efficient way to do the method with a set of Tuples,
  but I did not want to overcomplicate my code for this assignment.



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  I looked at the Swing library extensively, especially to understand the 
  JTextField and other functions for buttons and JLabels. I additionally got an
  image of a blank trivial pursuit board online at instillingvalues.com
  and colored it in myself using Mac Paint. I used online resources, mainly 
  http://www.classicweb.com/usr/jseng/trivi.htm and
  http://www.triviacafe.com/general-trivia-questions
  to find the questions for my project.
