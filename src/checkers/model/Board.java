/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.model;

import java.util.TreeMap;

/**
 *
 * @author Shankar
 */
public class Board
{

    final public static int BOARD_SIZE = 8;
    final public static int NUM_PIECES = 12;

    final static public int EMPTY = 0;
    final static public int FORWARD = 1;
    final static public int BACKWARD = -1;

    final static String DISPLAY_STRINGS[] =
    {
        "O", ".", "X"
    };

    final static private int[] FP_INITIAL_POS =
    {
        12, 14, 16, 18, 21, 23, 25, 27, 32, 34, 36, 38
    };
    final static private int[] BP_INITIAL_POS =
    {
        81, 83, 85, 87, 72, 74, 76, 78, 61, 63, 65, 67
    };

    int squares[][] = new int[BOARD_SIZE][BOARD_SIZE];

    TreeMap<Integer, Piece> gamePieces = new TreeMap<>();

    public Board()
    {

    }

    public int setPiece(Piece pc)
    {
        int value = pc.getDirection() * pc.getId();

        squares[pc.getRow() - 1][pc.getColumn() - 1] = value;

        return value;
    }

    public int liftPiece(Piece pc)
    {
        squares[pc.getRow() - 1][pc.getColumn() - 1] = EMPTY;
        return EMPTY;
    }

    public String getBoardStatus()
    {
        StringBuilder sb = new StringBuilder("  ");
     
        
        for (int col = 0; col < BOARD_SIZE; col++)
        {
            sb.append(" ");    
            sb.append(col+1);
        }
        sb.append("\r\n");
        for (int row = 0; row < BOARD_SIZE; row++)
        {
            sb.append(row+1);
            sb.append("  ");
            for (int col = 0; col < BOARD_SIZE; col++)
            {
                sb.append(DISPLAY_STRINGS[Integer.signum(squares[row][col]) + 1]);
                sb.append(" ");
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }

    public void assignInitialPieces(Player player)
    {
        int direction = player.getDirection();
        int[] posArray;
        Piece piece = null;

        if (direction == Board.FORWARD)
        {
            posArray = FP_INITIAL_POS;

        } else
        {
            posArray = BP_INITIAL_POS;
        }

        for (int i = 0; i < NUM_PIECES; i++)
        {
            piece = new Piece(posArray[i], direction);
            gamePieces.put(posArray[i], piece);
            player.addPiece(piece);
            setPiece(piece);
        }
    }

    /**
     * Update the list of valid moves for this piece After a move by the owner
     * of this piece. So, possible jumps are counted as immediately available
     * moves
     *
     * @param pc
     */
    public void updateValidMoves(Piece pc, boolean bIncludeSimple)
    {
        pc.clearMoves();
        int row = pc.getRow();
        int col = pc.getColumn();
        int direction = pc.getDirection();

        int newRow = row + direction;
        int leftCol = col - 1;
        int rightCol = col + 1;

        int jumpRow = newRow + direction;
        int jumpLeftCol = leftCol - 1;
        int jumpRightCol = rightCol + 1;

        Move move = null;
        int capturePieceId = 0;
        Piece capturePiece = null;

        // Add a left simple move if diagonal square is empty
        // Or add a jump move if the diagonal is occupied by
        // opponent's piece and the next diagonal one is empty
        if (newRow > 0 && newRow <= BOARD_SIZE
                && leftCol > 0 && leftCol <= BOARD_SIZE)
        {
            if (squares[newRow - 1][leftCol - 1] == Board.EMPTY && bIncludeSimple)
            {
                pc.addAvailableMove(row, col, newRow, leftCol);
            } else if (squares[newRow - 1][leftCol - 1] * direction < 0)
            {
                if (jumpRow > 0 && jumpRow <= BOARD_SIZE
                        && jumpLeftCol > 0 && jumpLeftCol <= BOARD_SIZE)
                {
                    if (squares[jumpRow - 1][jumpLeftCol - 1] == Board.EMPTY)
                    {
                        move = pc.addAvailableMove(row, col, jumpRow, jumpLeftCol);
                        capturePieceId = Math.abs(squares[newRow - 1][leftCol - 1]);
                        capturePiece = gamePieces.get(capturePieceId);
                        if (null != capturePiece)
                        {
                            move.setCapturePiece(capturePiece);
                        }
                    }
                }
            }
        }

        // Add a left simple move if diagonal square is empty
        if (newRow > 0 && newRow <= BOARD_SIZE
                && rightCol > 0 && rightCol <= BOARD_SIZE)
        {
            if (squares[newRow - 1][rightCol - 1] == Board.EMPTY && bIncludeSimple)
            {
                pc.addAvailableMove(row, col, newRow, rightCol);
            } else if (squares[newRow - 1][rightCol - 1] * direction < 0)
            {
                if (jumpRow > 0 && jumpRow <= BOARD_SIZE
                        && jumpRightCol > 0 && jumpRightCol <= BOARD_SIZE)
                {
                    if (squares[jumpRow - 1][jumpRightCol - 1] == Board.EMPTY)
                    {
                        move = pc.addAvailableMove(row, col, jumpRow, jumpRightCol);
                        capturePieceId = Math.abs(squares[newRow - 1][rightCol - 1]);
                        capturePiece = gamePieces.get(capturePieceId);
                        if (null != capturePiece)
                        {
                            move.setCapturePiece(capturePiece);
                        }
                    }
                }
            }
        }
    }

    /**
     * Moves the player's piece specified in the move If a piece is captured,
     * then the opponent's pieces are updated.
     *
     * @param player
     * @param opponent
     * @param move
     */
    void makeNextMove(Player player, Player opponent, Move move)
    {
        if (!move.isJump())
        {
            // This is a simple move. 
            // No capture and so no automatic next move
            // Need to simply update the board positions

            Piece piece = move.getPiece();
            liftPiece(piece);
            piece.moveTo(move.getTo());
            setPiece(piece);
        } else
        {
            // Need to perform a capture of the piece caught in between
            // Also need to update available moves and see if another jump is possible from current position

            Move nextMove = move;

            while (nextMove != null)
            {
                Piece piece = nextMove.getPiece();
                liftPiece(piece);
                piece.moveTo(nextMove.getTo());
                setPiece(piece);

                Piece capturePiece = nextMove.getCapturePiece();
                if (capturePiece != null)
                {
                    liftPiece(capturePiece);
                    capturePiece.getOwner().removePiece(capturePiece);
                }

                player.updateValidMoves(this);
               
                // It is possible at this point that further jump moves are possible
                // Do them all until the original moving piece has no more jump moves
                
                nextMove = piece.checkAndGetNextJumpMove();
                
            }

        }

    }
}
