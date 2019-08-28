package data.world;

import java.util.List;

import data.entities.Entity;
import data.entities.Entity.Type;

/**
* Represents a spot in the world
*/
public class Position {
	private Entity entity;
	private int row;
	private int column;
	private boolean isEmpty = false;
	private boolean isWall = false;

   /**
	* Initialize new position containing the given entity
	*/
	public Position(Entity entity, int row, int column) {
		if (entity == null) {
			isEmpty = true;
		}
		this.entity = entity;
		this.row = row;
		this.column = column;
	}
	
	/**
	* Initialize new position and set the isEmpty and isWall options manually
	*/
	public Position(int row, int column, boolean isEmpty, boolean isWall) {
		this.row = row;
		this.column = column;
		this.isEmpty = isEmpty;
		this.isWall = isWall;
	}

	public boolean isEmpty() {
		return isEmpty;
	}
	
	public boolean isWall() {
		return isWall;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

   /**
	* Checks the Position's coordinates against the given coordinates
	*/
	public boolean isAt(int row2, int column2) {
		return row == row2 && column == column2;
	}
	
	@Override
	public String toString() {
		return row + "," + column;
	}

	public Type getEntityType() {
		if (isEmpty()) {
			return Type.NONE;
		} 
		if (isWall()) {
			return Type.WALL;
		}
		
		return entity.getType();
	}

   /**
	* Returns the position in the list that is closest to this position
	*/
	public Position findClosestPoint(List<Position> posList) {
		double minDistance = Double.MAX_VALUE;
		Position closestPos = null;
		
		for (Position otherPos : posList) {
		    double distance = distance(otherPos);
			
			if (distance <= minDistance) {
				minDistance = distance;
				closestPos = otherPos;
			}
		}
		
		return closestPos;
	}

	public Entity getEntity() {
		return entity;
	}

   /**
	* Calculates the distance between this position and the given position
	*/
	public double distance(Position otherPos) {
		double rowDist = Math.abs(row - otherPos.getRow());
	    double colDist = Math.abs(column - otherPos.getColumn());
	         
	    return Math.hypot(rowDist, colDist);
	}
}
