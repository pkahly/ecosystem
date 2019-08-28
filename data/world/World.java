package data.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import data.entities.Entity;
import data.entities.Entity.Type;
import data.entities.animal.GeneticCode;
import data.entities.plant.Plant;
import util.RandomUtil;

public class World {
	private int height;
	private int width;
	private Entity[][] worldArray;
	private int reforestChance;

   /**
   * Initialize an empty world of the given size
   */
	public World(int height, int width, int reforestChance) {
		this.height = height;
		this.width = width;		
		worldArray = new Entity[height][width];
		this.reforestChance = reforestChance;
	}
	
	/**
	* Loop over all of the entities in the world and call tick() on each
	*/
	public Set<Entity> tick() {
		Set<Entity> processed = new HashSet<Entity>();
		
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				Entity entity = worldArray[row][column];
				Position pos = new Position(entity, row, column);
				
				if (entity != null && !processed.contains(entity)) {
					processed.add(entity);
					entity.tick(this, pos);
				} else if (RandomUtil.occurs(reforestChance)) {
					addOrReplace(new Plant(), pos);
				}
			}
		}
		
		return processed;
	}
	
	public int width() {
		return width;
	}

	public int height() {
		return height;
	}
   
   /**
	* Return the color of the entity at that location, or the background color if no entity
	*/
	public Color getColorValue(int row, int column) {
		Entity entity = worldArray[row][column];
		
		if (entity != null) {
			return entity.getColor();
		}
		
		return new Color(250, 250, 232);
	}

   /**
	* Updates the given Position object to contain the current entity at that location
	*/
	public Position getUpdatedPosition(Position pos) {
		return getUpdatedPosition(pos.getRow(), pos.getColumn());
	}
	
	/**
	* Returns a Position object with the entity at the given location
	*/
	public Position getUpdatedPosition(int row, int column) {
		if (!isValidPosition(row, column)) {
			return null;
		}
		
		return new Position(worldArray[row][column], row, column);
	}
	
	/**
	* Add a new entity at the given position
	*/
	public void addOrReplace(Entity entity, Position pos) {
		if (isValidPosition(pos)) {
			worldArray[pos.getRow()][pos.getColumn()] = entity;
		}
	}
	
	/**
	* Moves the entity in fromPos to toPos
	*/
	public void move(Position fromPos, Position toPos) {
		if (isValidPosition(toPos)) {
			addOrReplace(remove(fromPos), toPos);
		}
	}

   /**
	* Deletes the entity from the given position
	*/
	public Entity remove(Position pos) {
		pos = getUpdatedPosition(pos);
		Entity entity = pos.getEntity();
		
		addOrReplace(null, pos);
		
		return entity;
	}

   /**
	* Find the locations of any entities of the given type inside the given radius
	*/
	public List<Position> getNearbyPositionsOfType(Position center, int radius, Type type) {
		List<Type> list = new ArrayList<>();
		list.add(type);		
		return getNearbyPositionsOfType(center, radius, list);
	}

   /**
	* Find the locations of any entities of the given types inside the given radius
	*/	
	public List<Position> getNearbyPositionsOfType(Position center, int radius, List<Type> types) {
	   // Construct bounds of the search box
		int startRow = center.getRow() - radius;
		int endRow = center.getRow() + radius;
		int startCol = center.getColumn() - radius;
		int endCol = center.getColumn() + radius;
		
		// Search for entities of the given types
		List<Position> nearbyPositions = new ArrayList<>();
		for (int row = startRow; row <= endRow; row++) {
			for (int col = startCol; col <= endCol; col++) {
				if (isValidPosition(row, col)) {
					Position pos = new Position(worldArray[row][col], row, col);
					if (types.contains(pos.getEntityType())) {
						nearbyPositions.add(pos);
					}
				}
			}
		}
		
		return nearbyPositions;
	}
	
	/**
	* Checks if the given Position is within the world's bounds
	*/
	private boolean isValidPosition(Position pos) {
		return isValidPosition(pos.getRow(), pos.getColumn());
	}
	
	/**
	* Checks if the given coordinates are within the world's bounds
	*/
	private boolean isValidPosition(int row, int column) {
		if (row < 0 || column < 0) {
			return false;
		}
		
		if (row >= height || column >= width) {
			return false;
		}
		
		return true;
	}
}
