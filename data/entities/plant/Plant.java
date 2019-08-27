package data.entities.plant;

import java.util.ArrayList;
import java.util.List;

import data.entities.Entity;
import data.world.Position;
import data.world.World;
import util.RandomUtil;

public class Plant extends Entity {
	private static final int NEW_COLOR = 30;
	private static final int OLD_COLOR = 20;
	private static final int ACTION_DISTANCE = 2;
	private static final int REPRODUCTION_CHANCE = 10;
	private static final int DEATH_CHANCE_PER_AGE = 1;
	
	public Plant() {
		super(Type.PLANT);
	}

	@Override
	public void tick(World world, Position pos) {
		List<Type> openSpotTypes = new ArrayList<>();
		openSpotTypes.add(Type.NONE);
		openSpotTypes.add(Type.CORPSE);
		
		// Reproduction
		for (Position adjacentPos : world.getNearbyPositionsOfType(pos, ACTION_DISTANCE, openSpotTypes)) {
			if (RandomUtil.occurs(REPRODUCTION_CHANCE)) {
				world.addOrReplace(new Plant(), adjacentPos);
			}
		}
		
		// Death
		int deathChance = age * DEATH_CHANCE_PER_AGE;
		if (RandomUtil.occurs(deathChance)) {
			world.addOrReplace(new DeadPlant(), pos);
		}
		
		// Aging
		age++;
	}

	@Override
	public int getColor() {
		if (age < 10) {
			return NEW_COLOR;
		}
		
		return OLD_COLOR;
	}
}
