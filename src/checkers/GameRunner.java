/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

/**
 *
 * @author Shankar
 */
public interface GameRunner
{
    public String getStringInputFromUser();
    public int getMenuSelectionFromUser();
    
    public void displayPromptToUser(String msg);
    public void displayInfoUser(String msg);
    public void displayErrorUser(String msg);
}
