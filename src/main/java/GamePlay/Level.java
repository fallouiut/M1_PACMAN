package GamePlay;

public class Level {
	
	private int m_lifePoints;
	private int m_levelNumber;
	
	public Level(int lifePoints, int levelNumber)
	{
		m_lifePoints = lifePoints;
		m_levelNumber = levelNumber;
	}
	
	public int getLevelLife()
	{
		return m_lifePoints;
	}
	
	public int getlevelNumber()
	{
		return m_levelNumber;
	}
}
