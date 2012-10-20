package org.ck.ga;

public class OptimalScoreException extends Exception
{
	private Genome genome_solution;
	public OptimalScoreException()
	{}
	
	public OptimalScoreException(String msg) 
	{
	    super(msg);
	}

	public OptimalScoreException(Genome genome) {
		this.genome_solution = genome;
		genome_solution.displayGenes();
	}
}
