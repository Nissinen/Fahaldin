package Handlers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import Entity.PlayerSave;

public class SaveGame {
	
	public static void saveGame(){

		try {
			PrintWriter writer = new PrintWriter("savegame.txt");
			writer.println(ProgressHandler.getLock());
			writer.println(PlayerSave.getHealth());
			writer.println(PlayerSave.getLives());
			writer.println(PlayerSave.getScore());
			writer.println(PlayerSave.getTime());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
