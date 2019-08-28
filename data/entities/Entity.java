package data.entities;

import java.awt.Color;
import data.world.Position;
import data.world.World;

public abstract class Entity {
	public enum Type {
		NONE,
		PLANT,
		HERBAVORE,
		OMNIVORE,
		CARNIVORE,
		CORPSE,
		WALL
	}
	
	public Type type;
	protected int age = 0;
	
	public Entity(Type type) {
		this.type = type;
		age = 0;
	}
	
	public Type getType() {
		return type;
	}

	public abstract Color getColor();
	public abstract void tick(World world, Position pos);
	public abstract int getBaseEnergy();
}
