/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import checkers.model.Game;
import checkers.model.Board;
import checkers.model.Player;
import java.util.Scanner;

/**
 *
 * @author Shankar
 */
public class CheckersMain implements GameRunner
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
 
        CheckersMain checkersMain = new CheckersMain();
        Game game = new Game(checkersMain);
        
        game.play();
    }

    @Override
    public String getStringInputFromUser()
    {
       Scanner sc = new Scanner(System.in);
       return sc.next();
    }

    @Override
    public int getMenuSelectionFromUser()
    {
       Scanner sc = new Scanner(System.in);
       int input = 0;
       try 
       {
           input = sc.nextInt();
       }
       catch (Exception ex)
       {
            System.err.println("Not a number");
            input = 0;
       }
       
       return input;
    }

    @Override
    public void displayInfoUser(String msg)
    {
        System.out.println(msg);
        
    }

    @Override
    public void displayErrorUser(String msg)
    {
       System.err.println(msg);
    }

    @Override
    public void displayPromptToUser(String msg)
    {
        System.out.print(msg);
    }
    
}
