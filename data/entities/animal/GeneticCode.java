package data.entities.animal;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import data.entities.Entity.Type;
import util.RandomUtil;

public class GeneticCode {
   // Colors
	private static final Color HERBAVORE_COLOR = new Color(250, 184, 72);
	private static final Color OMNIVORE_COLOR = new Color(145, 135, 118);
	private static final Color CARNIVORE_COLOR = Color.RED;
	
	// Initial genetics
	private static final int SENSING_DISTANCE = 10;
	private static final int SPEED = 1;
	private static final int REPRODUCTION_CHANCE = 100;
	
	public int sensingDistance;
	public int speed;
	public int reproductionChance;
	public boolean eatsPlants;
	public boolean eatsMeat;
	
	/**
	* Create new genetics with default settings
	*/
	public GeneticCode() {
		sensingDistance = SENSING_DISTANCE;
		speed = SPEED;
		reproductionChance = REPRODUCTION_CHANCE;
		eatsPlants = true;
		eatsMeat = false;
	}	
	
	/**
	* Get a child genetic code, with mutated versions of the parent's genetics
	*/
	public GeneticCode mutate() {
		GeneticCode childCode = new GeneticCode();
		childCode.sensingDistance = Math.max(2, RandomUtil.mutate(sensingDistance, 4));
		childCode.speed = Math.max(1, RandomUtil.mutate(speed, 4));
		childCode.reproductionChance = Math.max(0, RandomUtil.mutate(reproductionChance, 4));
		childCode.eatsPlants = RandomUtil.mutate(eatsPlants);
		childCode.eatsMeat = RandomUtil.mutate(eatsMeat);
		return childCode;
	}
	
	/**
	* Returns the color, which is derived from the type
	*/
	public Color getColor() {
		Type type = getType();
		
		if (type == Type.HERBAVORE) {
			return HERBAVORE_COLOR;
		} else if (type == Type.OMNIVORE) {
			return OMNIVORE_COLOR;
		} else if (type == Type.CARNIVORE) {
			return CARNIVORE_COLOR;
		}
		
		return Color.BLACK;
	}
	
	/**
	* Classify the entity based on what it eats
	*/
	public Type getType() {
		if (eatsPlants && !eatsMeat) {
			return Type.HERBAVORE;
		} else if (eatsPlants && eatsMeat) {
			return Type.OMNIVORE;
		} else if (eatsMeat) {
			return Type.CARNIVORE;
		} else {
			return null;
		}
	}
	
	/**
	* Return a list of food entity types
	*/
	public List<Type> getFoodTypes() {
		List<Type> foodTypes = new ArrayList<>();
		
		if (eatsPlants && !eatsMeat) {
			foodTypes.add(Type.PLANT);
		} else if (eatsPlants && eatsMeat) {
			foodTypes.add(Type.PLANT);
			foodTypes.add(Type.HERBAVORE);
			foodTypes.add(Type.CORPSE);
		} else if (eatsMeat) {
			foodTypes.add(Type.HERBAVORE);
			foodTypes.add(Type.OMNIVORE);
			foodTypes.add(Type.CORPSE);
		}
		
		return foodTypes;
	}
}
