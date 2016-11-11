/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author Shankar
 */
public class Player
{

    private TreeMap<Integer, Piece> activePieces = new TreeMap<>();
    private ArrayList<Move> allAvailableMoves = new ArrayList<>();

    private int direction; //plus minus 1 to indicate player type and direction of forward movement 
    private String name = "";

    public Player(String name, int direction)
    {
        this.name = name;
        this.direction = direction;
    }
    
    public String getName()
    {
        return name;
    }

    public int getDirection()
    {
        return direction;
    }

    void addPiece(Piece piece)
    {
        activePieces.put(piece.getPosition(), piece);
        piece.setOwner(this);
    }

    void removePiece(Piece piece)
    {
        activePieces.remove(piece.getId());
    }

    public String getAvailableMovesList()
    {
        StringBuilder sb = new StringBuilder("");
        int index = 0;
        for (Move move : allAvailableMoves)
        {
            sb.append("[");
            sb.append(++index);
            sb.append("] ");
            sb.append(move.toString());
            sb.append("\r\n");
        }

        return sb.toString();
    }

    public List<Piece> getAllActivePieces()
    {
        ArrayList<Piece> myPieces = new ArrayList<>();
        Collection col = activePieces.values();
        myPieces.addAll(col);
        Collections.sort(myPieces, new Piece.IdComparator());

        return myPieces;
    }
    
    public int getNumberActivePieces()
    {
        return activePieces.size();
    }

    public void updateValidMoves(Board board)
    {
        allAvailableMoves.clear();
        List<Piece> myPieces = getAllActivePieces();
        for (Piece piece : myPieces)
        {
            board.updateValidMoves(piece, true);
            allAvailableMoves.addAll(piece.getAvailableMoves());
        }
        
        filterAvailableMoves();
    }

     /**
     *  Use this method to restrict available moves to jumps
     *  if jumps are available
     *  Iterates over the moves available to see if there are jump moves
     */
    void filterAvailableMoves()
    {
        ArrayList <Move> jumpMoves = new ArrayList<>();
        for ( Move move : allAvailableMoves)
        {
            if ( move.isJump()) 
            {
                jumpMoves.add(move);
            }
        }
        
        if ( !jumpMoves.isEmpty())
        {
            allAvailableMoves = jumpMoves;
        }    
    }
    /**
     * Returns a random move from the list of available moves
     * Or returns null if there are no valid moves
     * @return 
     */
    public Move pickRandomValidMove()
    {
        
        Move randomMove = null;

        int numMoves = allAvailableMoves.size();
        if (numMoves > 0)
        {
            int moveIndex = (int)(Math.random() * numMoves);
            randomMove = allAvailableMoves.get(moveIndex);
        }
        
        return randomMove;
       
    }
    
    /**
     * Returns the specified move from the list of available moves
     * Or returns null if the index specified is out of bounds
     * @param moveIndex
     * @return 
     */
    public Move getValidMove(int moveIndex)
    {
        Move selectedMove = null;
        if ( 0 < moveIndex &&  moveIndex <= allAvailableMoves.size())
        {
            selectedMove = allAvailableMoves.get(moveIndex - 1);
        }
        
        return selectedMove;
    }
    
    public int getNumberOfAvailableMoves()
    {
        return allAvailableMoves.size();
    }

}
