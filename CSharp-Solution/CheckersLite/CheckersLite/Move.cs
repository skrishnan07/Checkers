using System;
using System.Text;
namespace CheckersLite
{
	public class Move
	{

		private int from;
		private int to;
		private Piece piece = null;
		private Piece capturePiece = null;

		public Move(Piece piece, int fromRow, int fromCol, int toRow, int toCol)
		{
			this.piece = piece;
			from = 10 * fromRow + fromCol;
			to = 10 * toRow + toCol;
		}


		public int GetFrom()
		{
			return from;
		}

		public int GetTo()
		{
			return to;
		}

		public bool IsJump()
		{
			return (Math.Abs(from % 10 - to % 10) > 1);
		}

		public string toString()
		{
			StringBuilder sb = null;
			if (IsJump())
			{
				sb = new StringBuilder("Jump from (");

			}
			else
			{
				sb = new StringBuilder("Move from (");
			}
			sb.Append(from / 10);
			sb.Append(",");
			sb.Append(from % 10);
			sb.Append(") to (");
			sb.Append(to / 10);
			sb.Append(",");
			sb.Append(to % 10);
			sb.Append(")");

			return sb.ToString();
		}

		public Piece GetPiece()
		{
			return piece;
		}

		public void SetCapturePiece(Piece capturePiece)
		{
			this.capturePiece = capturePiece;
		}

		public Piece GetCapturePiece()
		{
			return capturePiece;
		}
	}
}
