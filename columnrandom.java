package Loot_Table_project;
import java.util.Random;
public class columnrandom
{
	
	public int columnrand(String column3, String column2)
	{
		Random randcol = new Random();
		int three=Integer.parseInt(column3), two=Integer.parseInt(column2);
		return randcol.nextInt(three + 1 - two)+two;
	}
}
