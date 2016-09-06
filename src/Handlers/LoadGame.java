package Handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Entity.PlayerSave;

public class LoadGame {
	
	private static ArrayList<String> strings;
	
	public static void loadGame() {
		strings = new ArrayList<String>();
		
		try {
			File file = new File("saveGame.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				strings.add(line);
			}
			fileReader.close();
			for(int i = 0; i < strings.size(); i++){

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		convertToIntAndSet();
	}
	private static void convertToIntAndSet(){
		ProgressHandler.setLock(Integer.parseInt(strings.get(0)));
		PlayerSave.setHealth(Integer.parseInt(strings.get(1)));
		PlayerSave.setLives(Integer.parseInt(strings.get(2)));
		PlayerSave.setScore(Integer.parseInt(strings.get(3)));
		PlayerSave.setTime(Integer.parseInt(strings.get(4)));
	}
}
