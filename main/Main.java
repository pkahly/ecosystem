package main;

import java.util.Iterator;
import data.entities.animal.Animal;
import data.entities.animal.DeadAnimal;
import data.entities.animal.GeneticCode;
import data.entities.plant.Plant;
import data.world.World;
import gui.MainFrame;
import util.RandomUtil;

public class Main {
   // Size of the World
	private static final int HEIGHT = 100;
	private static final int WIDTH = HEIGHT * 2;
	
	// Probability of adding a plant to an empty square
	private static final int REFOREST_CHANCE = 1;
	
	// Initial number of entities to add
	private static final int PLANTS = WIDTH * 10;
	private static final int ANIMALS = 100;
	
	
	public static void main(String[] args) {
		// Create world
		World world = new World(HEIGHT, WIDTH, REFOREST_CHANCE);
		
		// Add plants
		for (int i = 0; i < PLANTS; i++) {
			world.addOrReplace(new Plant(), RandomUtil.randPos(HEIGHT, WIDTH));
		}
		
		// Add Herbavores
		for (int i = 0; i < ANIMALS; i++) {
			world.addOrReplace(new Animal(new GeneticCode()), RandomUtil.randPos(HEIGHT, WIDTH));
		}
		
		// Open window and start main loop
		new MainFrame(world);
	}
}
