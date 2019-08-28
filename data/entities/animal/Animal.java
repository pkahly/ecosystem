package data.entities.animal;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import data.entities.Entity;
import data.world.Position;
import data.world.World;
import util.RandomUtil;

public class Animal extends Entity {
   // How close this animal must be to an object in order to interact with it
	private static final int ACTION_DISTANCE = 1;

   // Energy Statistics
	private final int BASE_ENERGY = 100;
	private final int ENERGY_USAGE = BASE_ENERGY / 10;
	private final int HUNGER_THRESHOLD = BASE_ENERGY * 10;
	private final int REPRODUCTION_COST = BASE_ENERGY * 2;
	
	private GeneticCode config;
	protected int energy;
	
	public Animal(GeneticCode config) {
		super(config.getType());
		this.config = config;
	}
	
	/**
	* Perform one action
	*/
	@Override
	public void tick(World world, Position pos) {

		// Feeding
		if (getEnergy() < HUNGER_THRESHOLD) {
			pos = seekFood(world, pos);

      // Reproduction
		} else if (getEnergy() - REPRODUCTION_COST >= getBaseEnergy()){
			List<Position> emptySpots = world.getNearbyPositionsOfType(pos, ACTION_DISTANCE, Type.NONE);
			
			if (!emptySpots.isEmpty() && RandomUtil.occurs(config.reproductionChance)) {
			   // Choose a spot
   			Position adjacentPos = RandomUtil.choosePos(emptySpots);
   			
            // Add a new Animal in that spot   			
				world.addOrReplace(new Animal(config.mutate()), adjacentPos);
				
				// Deduct the energy needed to create the new Animal
				subtractEnergy(REPRODUCTION_COST);
			}
		}
		
		// Energy Usage
		subtractEnergy(ENERGY_USAGE);
		
		// Death
		if (getEnergy() <= 0) {
			world.addOrReplace(new DeadAnimal(), pos);
		}
		
		// Aging
		age++;
	}

	private Position seekFood(World world, Position pos) {
		// Eat Food If Possible
		List<Position> foodWithinReach = world.getNearbyPositionsOfType(pos, ACTION_DISTANCE, config.getFoodTypes());
		if (!foodWithinReach.isEmpty()) {
			for (Position foodPos : foodWithinReach) {
				eatEntity(world.remove(foodPos));
			}
			return pos;
		}
		
		// Move toward food
		List<Position> food = world.getNearbyPositionsOfType(pos, config.sensingDistance, config.getFoodTypes());
		Position closestFood = pos.findClosestPoint(food);
				
		if (closestFood != null) {
			Position spotToMoveTo = closestFood.findClosestPoint(world.getNearbyPositionsOfType(pos, config.speed, Type.NONE));
			
			if (spotToMoveTo != null) {
				world.move(pos, spotToMoveTo);
				return world.getUpdatedPosition(spotToMoveTo);
			}
		}
		
		return pos;
	}

	private void eatEntity(Entity food) {
		addEnergy(food.getBaseEnergy());
	}

	@Override
	public Color getColor() {
		return config.getColor();
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public void addEnergy(int newEnergy) {
		energy += newEnergy;
	}
	
	@Override
	public int getBaseEnergy() {
		return BASE_ENERGY;
	}

	public void subtractEnergy(int energySpent) {
		energy -= energySpent;
		energy = Math.max(energy, 0);
	}

	public GeneticCode getGenetics() {
		return config;
	}
}
