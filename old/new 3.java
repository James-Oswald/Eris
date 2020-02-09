

public class Matrix
{
	private int[][] matrix;
	
	public Matrix(int rows, int columns)
	{
		matrix = new Matrix[rows][columns];
	}
	
	public void fillArray()
	{
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				matrix[i][j] = i + j + 7;
			}
		}
	}
	
	public int getRowSum(int row)
	{
		int sum = 0;
		for(int i = 0; i < matrix[i].length; i++)
		{
			sum += matrix[row][i];
		}
		return sum;
	}
	
	public int getColSum(int col)
	{
		int sum = 0;
		for(int i = 0; i < matrix.length; i++)
		{
			sum += matrix[i][col];
		}
		return sum;
	}
	
	public int getDiagonalSum()
	{
		
	}
	
	public static void main(String[] args)
	{
		System.out.println(0 % 2);
	}
}