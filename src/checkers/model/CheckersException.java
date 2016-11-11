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
public class CheckersException extends Exception
{

    public CheckersException(String msg)
    {
        super(msg);
    }

    static public CheckersException buildExceptionIllegalMove(int oldRow, int oldCol, int newRow, int newCol)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid Move from (");
        sb.append(oldRow);
        sb.append(",");
        sb.append(oldCol);
        sb.append(") to (");
        sb.append(newRow);
        sb.append(",");
        sb.append(newCol);

        return new CheckersException(sb.toString());
    }
}
