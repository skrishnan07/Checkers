/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Shankar
 */
public class Piece
{
 
    // A unique ID that is assigned at startup
    private int id;
    
    // Current row and col of the square being occupied
    private int row;
    private int col;
    
    // A value of either +1 or -1 to indicate owner/direction of movement
    private int direction;
    
    // The player who owns thid piece
    private Player owner;
    
    // Available list of moves for this player
    private ArrayList<Move> availableMoves = new ArrayList<>();
    
      
    public Piece(int id, int direction)
    {
        this.id = id;
        this.direction = direction;
        
        row = id/10;
        col = id%10;
    }
    
    public Player getOwner()
    {
        return owner;
    }

    public void setOwner(Player player)
    {
        owner = player;
    }
    
    public int getDirection ()
    {
        return direction;
    }
    
    public int getId()
    {
        return id;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getColumn()
    {
        return col;
    }
    
    public int getPosition()
    {
        return row*10 + col;
    }
  
    public void clearMoves()
    {
        availableMoves.clear();
    }
    
    // Add a valid move to the list of moves of this piece
    public Move addAvailableMove(int fromRow, int fromCol, int toRow, int toCol)
    {
        Move move = new Move(this, fromRow, fromCol, toRow, toCol);
        availableMoves.add(move);
        return move;
    }   
    
    public List<Move> getAvailableMoves()
    {
        return availableMoves;
    }

    public void moveTo(int to)
    {
        row = to/10;
        col = to%10;
    }

    public Move checkAndGetNextJumpMove()
    {
        Move nextJumpMove = null;
        for ( Move move : availableMoves )
        {
            if ( move.isJump())
            {
                nextJumpMove = move;
                break;
            }
        }
        return nextJumpMove;
    }

   
    
    public static class IdComparator implements Comparator<Piece>
    {

        @Override
        public int compare(Piece o1, Piece o2)
        {
            if (null == o1 || null == o2)
            {
                return 1;
            } else
            {
                return Integer.compare(o1.getPosition(), o2.getPosition());
            }
        }
    }
}
