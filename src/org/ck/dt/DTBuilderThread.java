package org.ck.dt;

import org.ck.ga.Genome;
import org.ck.ga.OptimalScoreException;
import org.ck.gui.Constants;

public class DTBuilderThread extends Thread implements Constants{
	String chromosome;
	boolean hasStopped;
	Thread parent;
	OptimalScoreException ose;
	public DTBuilderThread(Thread parent, String chromosome)
	{
		this.parent = parent;
		this.chromosome = chromosome;
		this.hasStopped = false;
	}
	
	
	public void run()
	{
		try
		{
			Genome allFeaturesGenome = new Genome(chromosome);
			
		}catch (OptimalScoreException e) {
			this.ose = e;
			hasStopped = true;
		}
		parent.interrupt();
	}


	public OptimalScoreException getException() {
		return ose;
	}

}
