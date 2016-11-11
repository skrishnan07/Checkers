/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.model;

/**
 *
 * @author Shankar
 */
public class Move
{

    private int from;
    private int to;
    
    private Piece piece = null;
    private Piece capturePiece = null;

    
    public Move(Piece piece, int fromRow, int fromCol, int toRow, int toCol)
    {
        this.piece = piece;
        from = 10*fromRow + fromCol;
        to = 10*toRow + toCol;
        
    }

    public int getFrom()
    {
        return from;
    }

    public int getTo()
    {
        return to;
    }

    public boolean isJump()
    {
        return (Math.abs(from % 10 - to % 10) > 1);
    }

    public String toString()
    {
        StringBuilder sb = null;
        if (isJump())
        {
            sb = new StringBuilder("Jump from (");

        } else
        {
            sb = new StringBuilder("Move from (");
        }
        sb.append(from / 10);
        sb.append(",");
        sb.append(from % 10);
        sb.append(") to (");
        sb.append(to / 10);
        sb.append(",");
        sb.append(to % 10);
        sb.append(")");
        
        return sb.toString();
    }

    Piece getPiece()
    {
        return piece;
    }

    void setCapturePiece(Piece capturePiece)
    {
        this.capturePiece = capturePiece;
    }

    Piece getCapturePiece()
    {
        return capturePiece;
    }

}
