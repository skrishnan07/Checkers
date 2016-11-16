using System;
using System.Collections.Generic;
using System.Text;

namespace CheckersLite
{
	public class Board
	{

		static readonly int BOARD_SIZE = 8;
		static readonly int NUM_PIECES = 12;

		static public int EMPTY = 0;
		static public int FORWARD = 1;
		static public int BACKWARD = -1;

		static string[] DISPLAY_STRINGS = { "O", ".", "X" };

		private static int[] FP_INITIAL_POS =
		{
		12, 14, 16, 18, 21, 23, 25, 27, 32, 34, 36, 38
	};
		private static int[] BP_INITIAL_POS =
		{
		81, 83, 85, 87, 72, 74, 76, 78, 61, 63, 65, 67
	};

		int[,] squares = new int[BOARD_SIZE, BOARD_SIZE];

		SortedDictionary<int, Piece> gamePieces = new SortedDictionary<int, Piece>();


		public int SetPiece(Piece pc)
		{
			int value = pc.GetDirection() * pc.GetId();

			squares[pc.GetRow() - 1, pc.GetColumn() - 1] = value;

			return value;
		}

		public int LiftPiece(Piece pc)
		{
			squares[pc.GetRow() - 1, pc.GetColumn() - 1] = EMPTY;
			return EMPTY;
		}

		public string GetBoardStatus()
		{
			StringBuilder sb = new StringBuilder("  ");


			for (int col = 0; col < BOARD_SIZE; col++)
			{
				sb.Append(" ");
				sb.Append(col + 1);
			}
			sb.Append("\r\n");
			for (int row = 0; row < BOARD_SIZE; row++)
			{
				sb.Append(row + 1);
				sb.Append("  ");
				for (int col = 0; col < BOARD_SIZE; col++)
				{
					sb.Append(DISPLAY_STRINGS[Math.Sign(squares[row,col]) + 1]);
					sb.Append(" ");
				}
				sb.Append("\r\n");
			}
			return sb.ToString();
		}

		public void AssignInitialPieces(Player player)
		{
			int direction = player.GetDirection();
			int[] posArray;
			Piece piece = null;

			if (direction == FORWARD)
			{
				posArray = FP_INITIAL_POS;

			}
			else
			{
				posArray = BP_INITIAL_POS;
			}

			for (int i = 0; i < NUM_PIECES; i++)
			{
				piece = new Piece(posArray[i], direction);
				gamePieces.Add(posArray[i], piece);
				player.AddPiece(piece);
				SetPiece(piece);
			}
		}

		/**
		 * Update the list of valid moves for this piece After a move by the owner
		 * of this piece. So, possible jumps are counted as immediately available
		 * moves
		 *
		 * @param pc
		 */
		public void UpdateValidMoves(Piece pc, bool bIncludeSimple)
		{
			pc.ClearMoves();
			int row = pc.GetRow();
			int col = pc.GetColumn();
			int direction = pc.GetDirection();

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
				if (squares[newRow - 1,leftCol - 1] == EMPTY && bIncludeSimple)
				{
					pc.AddAvailableMove(row, col, newRow, leftCol);
				}
				else if (squares[newRow - 1, leftCol - 1] * direction < 0)
				{
					if (jumpRow > 0 && jumpRow <= BOARD_SIZE
							&& jumpLeftCol > 0 && jumpLeftCol <= BOARD_SIZE)
					{
						if (squares[jumpRow - 1,jumpLeftCol - 1] == EMPTY)
						{
							move = pc.AddAvailableMove(row, col, jumpRow, jumpLeftCol);
							capturePieceId = Math.Abs(squares[newRow - 1, leftCol - 1]);
							capturePiece = gamePieces[capturePieceId];
							if (null != capturePiece)
							{
								move.SetCapturePiece(capturePiece);
							}
						}
					}
				}
			}

			// Add a left simple move if diagonal square is empty
			if (newRow > 0 && newRow <= BOARD_SIZE
					&& rightCol > 0 && rightCol <= BOARD_SIZE)
			{
				if (squares[newRow - 1, rightCol - 1] == EMPTY && bIncludeSimple)
				{
					pc.AddAvailableMove(row, col, newRow, rightCol);
				}
				else if (squares[newRow - 1, rightCol - 1] * direction < 0)
				{
					if (jumpRow > 0 && jumpRow <= BOARD_SIZE
							&& jumpRightCol > 0 && jumpRightCol <= BOARD_SIZE)
					{
						if (squares[jumpRow - 1, jumpRightCol - 1] == EMPTY)
						{
							move = pc.AddAvailableMove(row, col, jumpRow, jumpRightCol);
							capturePieceId = Math.Abs(squares[newRow - 1, rightCol - 1]);
							capturePiece = gamePieces[capturePieceId];
							if (null != capturePiece)
							{
								move.SetCapturePiece(capturePiece);
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
		public void MakeNextMove(Player player, Move move)
		{
			if (!move.IsJump())
			{
				// This is a simple move. 
				// No capture and so no automatic next move
				// Need to simply update the board positions

				Piece piece = move.GetPiece();
				LiftPiece(piece);
				piece.moveTo(move.GetTo());
				SetPiece(piece);
			}
			else
			{
				// Need to perform a capture of the piece caught in between
				// Also need to update available moves and see if another jump is possible from current position

				Move nextMove = move;

				while (nextMove != null)
				{
					Piece piece = nextMove.GetPiece();
					LiftPiece(piece);
					piece.moveTo(nextMove.GetTo());
					SetPiece(piece);

					Piece capturePiece = nextMove.GetCapturePiece();
					if (capturePiece != null)
					{
						LiftPiece(capturePiece);
						capturePiece.GetOwner().RemovePiece(capturePiece);
					}

					player.UpdateValidMoves(this);

					// It is possible at this point that further jump moves are possible
					// Do them all until the original moving piece has no more jump moves

					nextMove = piece.CheckAndGetNextJumpMove();

				}

			}

		}
	}
}
