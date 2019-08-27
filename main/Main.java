package main;

import java.util.Iterator;
import data.entities.animal.Herbavore;
import data.entities.animal.DeadAnimal;
import data.entities.animal.GeneticCode;
import data.entities.animal.Carnivore;
import data.entities.plant.Plant;
import data.world.World;
import gui.MainFrame;
import util.RandomUtil;

public class Main {
	private static final int HEIGHT = 100;
	private static final int WIDTH = HEIGHT * 2;
	
	private static final int PLANTS = WIDTH * 10;
	private static final int ANIMALS = 100;
	private static final int IMMORTAL_CARNIVORES = 1;
	
	public static void main(String[] args) {
		// Create world
		World world = new World(HEIGHT, WIDTH);
		
		for (int i = 0; i < PLANTS; i++) {
			world.addOrReplace(new Plant(), RandomUtil.randPos(HEIGHT, WIDTH));
		}
		
		for (int i = 0; i < ANIMALS; i++) {
			world.addOrReplace(new Herbavore(new GeneticCode()), RandomUtil.randPos(HEIGHT, WIDTH));
		}
		
		for (int i = 0; i < IMMORTAL_CARNIVORES; i++) {
			world.addOrReplace(new Carnivore(), RandomUtil.randPos(HEIGHT, WIDTH));
		}
		
		new MainFrame(world);
	}
}
