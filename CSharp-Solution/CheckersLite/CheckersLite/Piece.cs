using System;
using System.Collections.Generic;
namespace CheckersLite
{
	public class Piece : IComparable<Piece>
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
		private List<Move> availableMoves = new List<Move>();


		public Piece(int id, int direction)
		{
			this.id = id;
			this.direction = direction;

			row = id / 10;
			col = id % 10;
		}

		public Player GetOwner()
		{
			return owner;
		}

		public void SetOwner(Player player)
		{
			owner = player;
		}

		public int GetDirection()
		{
			return direction;
		}

		public int GetId()
		{
			return id;
		}

		public int GetRow()
		{
			return row;
		}

		public int GetColumn()
		{
			return col;
		}

		public int GetPosition()
		{
			return row * 10 + col;
		}

		public void ClearMoves()
		{
			availableMoves.Clear();
		}

		// Add a valid move to the list of moves of this piece
		public Move AddAvailableMove(int fromRow, int fromCol, int toRow, int toCol)
		{
			Move move = new Move(this, fromRow, fromCol, toRow, toCol);
			availableMoves.Add(move);
			return move;
		}

		public List<Move> GetAvailableMoves()
		{
			return availableMoves;
		}

		public void moveTo(int to)
		{
			row = to / 10;
			col = to % 10;
		}

		public Move CheckAndGetNextJumpMove()
		{
			Move nextJumpMove = null;
			foreach (Move move in availableMoves)
			{
				if (move.IsJump())
				{
					nextJumpMove = move;
					break;
				}
			}
			return nextJumpMove;
		}



		public int CompareTo(Piece p2)
		{

			if (p2 == null)
			{
				return 1;
			}
			else
			{
				return id.CompareTo(p2.id);
			}
		}

	
		public bool Equals(Piece other)
		{
			if (other == null) return false;
			return (id.Equals(other.id));
		}
	}
}
