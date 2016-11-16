
namespace CheckersLite
{
	public interface GameRunner
	{
		string GetStringInputFromUser();
		int GetMenuSelectionFromUser();

		void DisplayPromptToUser(string msg);
		void DisplayInfoUser(string msg);
		void DisplayErrorUser(string msg);
	}
}
