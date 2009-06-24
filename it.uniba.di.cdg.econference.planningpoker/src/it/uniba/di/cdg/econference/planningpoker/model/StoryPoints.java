package it.uniba.di.cdg.econference.planningpoker.model;

public enum StoryPoints {
	
	UNKNOW(-1),
	ONE(1),
	TWO(2),
	THREE(3),
	FIVE(5),
	EIGHT(8),
	THIRTEEN(13),
	TWENTY(20),
	FOURTY(40),
	ONEUNDRED(100);
	
	private int points;
	
	private StoryPoints(int points){
		this.points = points;
	}
	
	public int getPoints(){
		return points;
	}
}
