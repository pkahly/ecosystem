package data.entities.animal;

import java.awt.Color;
import data.entities.Entity;
import data.entities.plant.Plant;
import data.world.Position;
import data.world.World;
import util.RandomUtil;

public class DeadAnimal extends Entity {
	private static final Color COLOR = new Color(108, 50, 179);
	private static final int DECAY_CHANCE_PER_AGE = 10;
	private static final int BASE_ENERGY = 100;
	
	public DeadAnimal() {
		super(Type.CORPSE);
	}

	@Override
	public void tick(World world, Position pos) {
		// Decay
		int decayChance = age * DECAY_CHANCE_PER_AGE;
		if (RandomUtil.occurs(decayChance)) {
			world.addOrReplace(new Plant(), pos);
		}
		
		// Aging
		age++;
	}

	@Override
	public Color getColor() {
		return COLOR;
	}

	@Override
	public int getBaseEnergy() {
		return BASE_ENERGY;
	}	
}
