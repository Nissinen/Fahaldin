package Handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Entity.PlayerSave;

public class LoadGame {
	
	private  ArrayList<String> strings;
	
	public boolean loadGame() {
		strings = new ArrayList<String>();
		
			try {
				File file = new File("saveGame.txt");
				if(file.exists()){
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					strings.add(line);
				}
				fileReader.close();
				convertToIntAndSet();
				return true;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
	}

	private  void convertToIntAndSet(){
		ProgressHandler.setLock(Integer.parseInt(strings.get(0)));
		PlayerSave.setHealth(Integer.parseInt(strings.get(1)));
		PlayerSave.setLives(Integer.parseInt(strings.get(2)));
		PlayerSave.setScore(Integer.parseInt(strings.get(3)));
		PlayerSave.setTime(Integer.parseInt(strings.get(4)));
	}
}
