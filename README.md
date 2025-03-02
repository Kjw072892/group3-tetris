# Group Project Tetris

### Students

- Kassie Whitney

- James Strand

- Roman Bureacov 

- Zainab Stanikzy

### Winter 2025

## Sprint 1 Meeting Agenda
Agenda #1: Determine who's responsible for which part for sprint 1.

Agenda #2: Discuss basic project requirements for sprint 1.

Agenda #3: Discuss ideas about how we want to create this project including project layout, code base format, classes, 
and setting up for sprint 2 and beyond.

Agenda #4: Draft a basic outlines on what our tetris board will look like.


## Sprint 1 Contribution
For sprint 1, we were able to get together and present our own vision and ideas on what our project would look like at 
the end of sprint 1. On the first day we were able to code with each other, trying out each other's ideas on creating
a basic layout of our tetris game board.
During this same meeting, we agreed on who would do what, and what the result should look like.

## Kassie 
Kassie was responsible for creating the ScorePanel class and DirectionLabel class. 
She also helped with refactoring code for potential reusability, clarity, 
and making sure that checkstyle warnings were cleared.
She also created a utility class that takes in any number of JLabels, extracts the labels font attributes, 
and concatenates the labels together; such that each section of the output string would have the same font data as the 
labels that were passed, and that-string would then be used to create the new JLabel text.
She also helped design an outline of the game board. 
She also added two JMenuItems "Feature" and "Help" where the Feature item will store different game background music, 
and background colors, and the Help item will store the "about" and other possible helpful instructions.

## James 
James had worked on getting the FileMenu class started so that a new game can be started, can exit the program, and 
 have an about page. 
He had also worked on making the nextPiece Gui component working, ensuring that every 
metronomic that gets inputted is centered on the spot. 
He also helped Zainab with bugfix the problem of the game screen 
not displaying the metronomes correctly on the start screen. 
James also made sure to listen to feedback of Roman, 
 particularly on how to find the center of a tetromino piece on a cartesian coordinate graph. 
James lastly was the project manager.

## Roman
Roman played an essential role in refining the structure of the classes and layout of the game board.
One of his primary contributions was eliminating magic numbers in the base layout class by introducing well-defined
Constance, and this improved the overall clarity and readability of the code. 
He cleaned things up by replacing hardcoded values. 
He also worked on design designing and structuring the borders. 
This helped allow for better spacing and clear visual distinctions
Between the different regions of the game. 
His attention to clean code organization and consistent formatting really helped maintain a structured
foundation. 


## Zainab 
Zainab was responsible for implementing the board panel class to ensure that individual Tetris pieces are 
Properly displayed on the game board. She worked on retrieving and rendering the defined sprint one tetromino,
From the sprint One values class. She made sure they aligned and displayed correctly with the grid. Through multiple
Trial and errors + lots of debugging she fine-tunes the positioning of each shape. 
She collaborated with teammates to resolve display issues to ensure the game board accurately represented the
individual tetromino pieces.
James assisted her in correcting the x,y coordinate calculations because the pieces were originally displayed upside down.
By refining the peace placement logic, the blocks were then positioned and aligned correctly, right side up.
She also actively participated in team discussions, reviewed code implementation and
insured her class integrated smoothly with the overall project, and made changes when and where necessary.
Her work on displaying the pieces leads a crucial function, setting up the game board for the next sprint where movement
Game logic will be implemented.

## Sprint 1 Comments


