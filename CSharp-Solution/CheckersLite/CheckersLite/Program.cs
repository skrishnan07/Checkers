using System;

namespace CheckersLite
{
	class MainClass : GameRunner
	{
		public static void Main()
		{
			MainClass mc = new MainClass();
			Game game = new Game(mc);

			game.Play();
		}

		public void DisplayErrorUser(string msg)
		{
			Console.Error.WriteLine(msg);
		}

		public void DisplayInfoUser(string msg)
		{
			Console.Out.WriteLine(msg);
		}

		public void DisplayPromptToUser(string msg)
		{
			Console.Out.Write(msg);
		}

		public int GetMenuSelectionFromUser()
		{

			int input = 0;
			try
			{
				input = int.Parse(Console.ReadLine());
			}
			catch (Exception)
			{
				Console.Error.WriteLine("Not a number");
				input = 0;
			}

			return input;
		}

		public string GetStringInputFromUser()
		{
			return Console.ReadLine();
		}
	}
}
