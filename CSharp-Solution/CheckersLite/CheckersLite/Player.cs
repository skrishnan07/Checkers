using System;
using System.Collections.Generic;
using System.Text;

namespace CheckersLite
{
	public class Player
	{

		private SortedDictionary<int, Piece> activePieces = new SortedDictionary<int, Piece>();
		private List<Move> allAvailableMoves = new List<Move>();

		private int direction; //plus minus 1 to indicate player type and direction of forward movement 
		private String name = "";

		public Player(String name, int direction)
		{
			this.name = name;
			this.direction = direction;
		}

		public String GetName()
		{
			return name;
		}

		public int GetDirection()
		{
			return direction;
		}

		public void AddPiece(Piece piece)
		{
			activePieces.Add(piece.GetPosition(), piece);
			piece.SetOwner(this);
		}

		public void RemovePiece(Piece piece)
		{
			activePieces.Remove(piece.GetId());
		}

		public String GetAvailableMovesList()
		{
			StringBuilder sb = new StringBuilder("");
			int index = 0;
			foreach (Move move in allAvailableMoves)
			{
				sb.Append("[");
				sb.Append(++index);
				sb.Append("] ");
				sb.Append(move.toString());
				sb.Append("\r\n");
			}

			return sb.ToString();
		}

		public List<Piece> GetAllActivePieces()
		{
			List<Piece> myPieces = new List<Piece>(activePieces.Values);
			myPieces.Sort();

			return myPieces;
		}

		public int GetNumberActivePieces()
		{
			return activePieces.Count;
		}

		public void UpdateValidMoves(Board board)
		{
			allAvailableMoves.Clear();
			List<Piece> myPieces = GetAllActivePieces();
			foreach (Piece piece in myPieces)
			{
				board.UpdateValidMoves(piece, true);
				allAvailableMoves.AddRange(piece.GetAvailableMoves());
			}

			FilterAvailableMoves();
		}

	
		private void FilterAvailableMoves()
		{
			List<Move> jumpMoves = new List<Move>();
			foreach (Move move in allAvailableMoves)
			{
				if (move.IsJump())
				{
					jumpMoves.Add(move);
				}
			}

			if (jumpMoves.Count != 0 )
			{
				allAvailableMoves = jumpMoves;
			}
		}

	
		public Move PickRandomValidMove()
		{

			Move randomMove = null;

			int numMoves = allAvailableMoves.Count;
			if (numMoves > 0)
			{
				int moveIndex = new Random().Next(0, numMoves - 1);
				randomMove = allAvailableMoves[moveIndex];
			}

			return randomMove;

		}

		/**
		 * Returns the specified move from the list of available moves
		 * Or returns null if the index specified is out of bounds
		 * @param moveIndex
		 * @return 
		 */
		public Move GetValidMove(int moveIndex)
		{
			Move selectedMove = null;
			if (0 < moveIndex && moveIndex <= allAvailableMoves.Count)
			{
				selectedMove = allAvailableMoves[moveIndex - 1];
			}

			return selectedMove;
		}

		public int GetNumberOfAvailableMoves()
		{
			return allAvailableMoves.Count;
		}

	}
}
