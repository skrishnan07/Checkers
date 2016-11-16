namespace CheckersLite
{
	public class Game
	{


		private GameRunner runner = null;
		private Board board = null;

		private Player computer = null;
		private Player human = null;
		private bool bTurnHuman = false;
		private bool bStaleMate = false;
		private bool bGameWon = false;


		public Game(GameRunner runner)
		{
			this.runner = runner;
		}

		public void Play()
		{

			SelectPiecesAndPlayOrder();


			board.AssignInitialPieces(computer);
			board.AssignInitialPieces(human);

			computer.UpdateValidMoves(board);
			human.UpdateValidMoves(board);

			string display = board.GetBoardStatus();

			runner.DisplayInfoUser("\r\n" + display + "\r\n");

			Move nextMove = null;
			Player nextPlayer = null;
			Player opponent = null;

			while (!bStaleMate && !bGameWon)
			{
				if (bTurnHuman)
				{
					nextPlayer = human;
					opponent = computer;
					nextMove = getMoveFromUser();
				}
				else
				{
					nextPlayer = computer;
					opponent = human;
					nextMove = computer.PickRandomValidMove();
				}

				if (nextMove == null)
				{
					if (bStaleMate)
					{
						display = board.GetBoardStatus();
						runner.DisplayInfoUser("\r\n" + display);
						runner.DisplayInfoUser("\r\nStalemate: No more valid moves available. Game ends");

					}
					else
					{
						runner.DisplayInfoUser("Bye " + human.GetName() + ". Play again anytime.");
						break;
					}
				}
				else
				{
					if (bTurnHuman)
					{
						runner.DisplayInfoUser("Moving your piece as you selected: " + nextMove.toString());
					}
					else
					{
						runner.DisplayInfoUser("I am making my move: " + nextMove.toString());
					}
					board.MakeNextMove(nextPlayer, nextMove);

					if (opponent.GetNumberActivePieces() == 0)
					{
						bGameWon = true;

						display = board.GetBoardStatus();
						runner.DisplayInfoUser("\r\n" + display);

						runner.DisplayInfoUser("\r\nGame Over: Winner is " + nextPlayer.GetName());
						break;
					}


					computer.UpdateValidMoves(board);
					human.UpdateValidMoves(board);

					if (opponent.GetNumberOfAvailableMoves() < 1)
					{
						bStaleMate = true;

						display = board.GetBoardStatus();
						runner.DisplayInfoUser("\r\n" + display);

						runner.DisplayInfoUser("\r\nStalemate: No more moves left for " + opponent.GetName());
						runner.DisplayInfoUser(human.GetName() + " has " + human.GetNumberActivePieces() + " pieces left");
						runner.DisplayInfoUser(computer.GetName() + " has " + computer.GetNumberActivePieces() + " pieces left\r\n");
						break;
					}

					display = board.GetBoardStatus();
					runner.DisplayInfoUser("\r\n" + display + "\r\n");

				}


				bTurnHuman = !bTurnHuman;

			}



		}

		private void SelectPiecesAndPlayOrder()
		{
			board = new Board();

			runner.DisplayInfoUser("Welcome! I am Charlie Checker");
			runner.DisplayPromptToUser("What's your name? ");
			string name = runner.GetStringInputFromUser();

			int choice;
			runner.DisplayInfoUser("Hi " + name + ", please select your game pieces\r\n");
			runner.DisplayInfoUser("[1] X X X X X X X X X X X X    (Plays First)");
			runner.DisplayInfoUser("[2] O O O O O O O O O O O O");
			runner.DisplayPromptToUser("\r\nEnter your choice [1 or 2]: ");

			choice = runner.GetMenuSelectionFromUser();
			while (choice != 1
					&& choice != 2)
			{
				runner.DisplayErrorUser("Invalid choice.  Please enter 1 or 2 only");
				runner.DisplayInfoUser("[1] X X X X X X X X X X X X    (Plays First)");
				runner.DisplayInfoUser("[2] O O O O O O O O O O O O");
				runner.DisplayPromptToUser("\r\nEnter your choice [1 or 2]: ");

				choice = runner.GetMenuSelectionFromUser();
			}

			if (choice == 1)
			{
				computer = new Player("Charlie Checker", Board.BACKWARD);
				human = new Player(name, Board.FORWARD);
				bTurnHuman = true;
			}
			else
			{
				computer = new Player("Charlie Checker", Board.FORWARD);
				human = new Player(name, Board.BACKWARD);
				bTurnHuman = false;
			}
		}

		private Move getMoveFromUser()
		{

			Move selectedMove = null;
			int numAvailableMoves = human.GetNumberOfAvailableMoves();

			if (numAvailableMoves == 0)
			{
				bStaleMate = true;
				runner.DisplayInfoUser(human.GetName() + ", you have no more moves left");
			}
			else
			{
				string strAvailableMoves = human.GetAvailableMovesList();
				runner.DisplayInfoUser(human.GetName() + ", It is your turn. Please select from the available moves listed below:\r\n ");
				runner.DisplayInfoUser(strAvailableMoves);
				runner.DisplayPromptToUser("Enter an index or 0 to exit game: ");

				int moveIndex = runner.GetMenuSelectionFromUser();
				if (moveIndex == 0)
				{
					selectedMove = null;
				}

				while (moveIndex < 0 || moveIndex > numAvailableMoves)
				{

					runner.DisplayErrorUser("Invalid selection. Please try again. Select from the available moves listed below");
					runner.DisplayInfoUser(strAvailableMoves);
					runner.DisplayPromptToUser("Enter an index or 0 to exit game: ");
					moveIndex = runner.GetMenuSelectionFromUser();
				}
				selectedMove = human.GetValidMove(moveIndex);

			}

			return selectedMove;
		}



	}
}

