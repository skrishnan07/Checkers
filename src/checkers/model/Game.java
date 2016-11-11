/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.model;

import checkers.GameRunner;
import checkers.model.Board;
import checkers.model.Player;

/**
 *
 * @author Shankar
 */
public class Game
{

    private GameRunner runner = null;
    private Board board = null;
    private Player computer = null;
    private Player human = null;
    private boolean bTurnHuman = false;
    private boolean bStaleMate = false;
    private boolean bGameWon = false;
   

    public Game(GameRunner runner)
    {
        this.runner = runner;
    }

    public void play()
    {

        selectPiecesAndPlayOrder();
        
        
        board.assignInitialPieces(computer);
        board.assignInitialPieces(human);

        computer.updateValidMoves(board);
        human.updateValidMoves(board);

        String display = board.getBoardStatus();
                
        runner.displayInfoUser("\r\n" + display + "\r\n");
        
        Move nextMove = null;
        Player nextPlayer = null;
        Player opponent = null;
        
        while ( !bStaleMate && !bGameWon)
        {
            if ( bTurnHuman )
            {
                nextPlayer = human;
                opponent = computer;
                nextMove = getMoveFromUser();
            }
            else
            {
                nextPlayer = computer;
                opponent = human;
                nextMove = computer.pickRandomValidMove();
            }
            
            if ( nextMove == null)
            {
                if ( bStaleMate )
                {
                    display = board.getBoardStatus();
                    runner.displayInfoUser("\r\n" + display );
                    runner.displayInfoUser("\r\nStalemate: No more valid moves available. Game ends");
                    
                }
                else
                {
                    runner.displayInfoUser("Bye " + human.getName() + ". Play again anytime.");
                    break;
                }
            }
            else
            {
                if ( bTurnHuman) 
                {
                    runner.displayInfoUser("Moving your piece as you selected: " + nextMove.toString());
                }
                else
                {
                    runner.displayInfoUser("I am making my move: " + nextMove.toString());
                }
                board.makeNextMove(nextPlayer, opponent, nextMove);
                
                if ( opponent.getNumberActivePieces() == 0)
                {
                    bGameWon = true;
                    
                     display = board.getBoardStatus();
                     runner.displayInfoUser("\r\n" + display );
                     
                    runner.displayInfoUser("\r\nGame Over: Winner is " + nextPlayer.getName());
                    break;
                }
                
                
                computer.updateValidMoves(board);
                human.updateValidMoves(board);
                
                if ( opponent.getNumberOfAvailableMoves() < 1)
                {
                    bStaleMate = true;
                    
                    display = board.getBoardStatus();
                    runner.displayInfoUser("\r\n" + display );
                    
                    runner.displayInfoUser("\r\nStalemate: No more moves left for " + opponent.getName());
                    runner.displayInfoUser(human.getName() + " has " + human.getNumberActivePieces() + " pieces left");
                    runner.displayInfoUser(computer.getName() + " has " + computer.getNumberActivePieces() + " pieces left\r\n");
                    break;
                }

                display = board.getBoardStatus();
                runner.displayInfoUser("\r\n" + display + "\r\n");
                
            }
            
            
            bTurnHuman = !bTurnHuman;
           
        }

       

    }

    private void selectPiecesAndPlayOrder()
    {
        board = new Board();
        
        runner.displayInfoUser("Welcome! I am Charlie Checker");
        runner.displayPromptToUser("What's your name? ");
        String name = runner.getStringInputFromUser();

        int choice;
        runner.displayInfoUser("Hi " + name + ", please select your game pieces\r\n");
        runner.displayInfoUser("[1] X X X X X X X X X X X X    (Plays First)");
        runner.displayInfoUser("[2] O O O O O O O O O O O O");
        runner.displayPromptToUser("\r\nEnter your choice [1 or 2]: ");

        choice = runner.getMenuSelectionFromUser();
        while (choice != 1
                && choice != 2)
        {
            runner.displayErrorUser("Invalid choice.  Please enter 1 or 2 only");
            runner.displayInfoUser("[1] X X X X X X X X X X X X    (Plays First)");
            runner.displayInfoUser("[2] O O O O O O O O O O O O");
            runner.displayPromptToUser("\r\nEnter your choice [1 or 2]: ");

            choice = runner.getMenuSelectionFromUser();
        }

        if (choice == 1)
        {
            computer = new Player("Charlie Checker", Board.BACKWARD);
            human = new Player(name, Board.FORWARD);
            bTurnHuman = true;
        } else
        {
            computer = new Player("Charlie Checker", Board.FORWARD);
            human = new Player(name, Board.BACKWARD);
            bTurnHuman = false;
        }
    }

    private Move getMoveFromUser()
    {

        Move selectedMove = null;
        int numAvailableMoves = human.getNumberOfAvailableMoves();
      
        if ( numAvailableMoves == 0 )
        {
            bStaleMate = true;
            runner.displayInfoUser(human.getName() + ", you have no more moves left");
        }
        else
        {
            String strAvailableMoves = human.getAvailableMovesList();
            runner.displayInfoUser(human.getName() + ", It is your turn. Please select from the available moves listed below:\r\n ");
            runner.displayInfoUser(strAvailableMoves);
            runner.displayPromptToUser("Enter an index or 0 to exit game: ");
            
            int moveIndex = runner.getMenuSelectionFromUser();
            if ( moveIndex == 0)
            {
                selectedMove = null;
            }
            
            while ( moveIndex < 0 || moveIndex > numAvailableMoves)
            {
                
                runner.displayErrorUser("Invalid selection. Please try again. Select from the available moves listed below");
                runner.displayInfoUser(strAvailableMoves);
                runner.displayPromptToUser("Enter an index or 0 to exit game: ");
                moveIndex = runner.getMenuSelectionFromUser();
            }
            selectedMove = human.getValidMove(moveIndex);
            
        }
        
        return selectedMove;
    }

   

}
