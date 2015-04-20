import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Gui{
	static String toServer;
	static String fromServer;
	public static void main(String[] args){
		Boolean status = false;
		Client c = new Client();
		ConnectServerWin serverWin = new ConnectServerWin(c);
		MainMenuWin mainMenu = new MainMenuWin();
		serverWin.connectServer();
		while(!status){
			System.out.print("");
			if(c.status){
				mainMenu.mainMenu();
				status=true;
				try{
					c.connect();
				}catch(Exception e){
					System.exit(-1);
					break;
				}	
			}
		}
	}
}
