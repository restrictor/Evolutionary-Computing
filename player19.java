import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Properties;



public class player19 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
    	private int evaluations_limit_;
	
	public player19()
	{
		rnd_ = new Random();
	}
	
	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		evaluation_ = evaluation;
		
		// Get evaluation properties
		Properties props = evaluation.getProperties();
        	// Get evaluation limit
        	evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
		boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
		boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
		boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal){
            // Do sth
        }else{
            // Do sth else
        }
    }
    
	public void run()
	{
		// Run your algorithm here
        
		int evals = 0;
        
        // Initialize population
		ArrayList<vector> pop = new ArrayList<vector>();
		for (int i=0; i<100; i++ ){
			vector member = new vector();
			member.fitness = (double) evaluation_.evaluate(member.solution);
			evals ++;
			pop.add(member);
		}
		Collections.sort(pop);

		ArrayList<vector> children = new ArrayList<vector>();  
		ArrayList<vector> parents = new ArrayList<vector>();
		
        // calculate fitness
		int mutations = 0;
		int iter = 0;
		
        while(evals<evaluations_limit_){
		iter = iter + 1;

		// Select parents (very basic selection: take best half)
    		for (int i = 0; i<50; i++) {
    			parents.add(pop.get(i));
    		}
    		pop.clear();
    		
            // Apply crossover / mutation operators 
    		for (int i = 25; i < 50; i++) {
    			double[] mutation = parents.get(i).mutate();
    			double fitness = (double) evaluation_.evaluate(mutation);
    			evals++;
				if (fitness > parents.get(i).fitness) {
					for (int j = 0; j < 10; j++) {
						parents.get(i).scalarArray[j].x = mutation[j];
						parents.get(i).solution[j]= mutation[j];			
					}
					parents.get(i).fitness = fitness;
					mutations = mutations + 1;
				}
    		}
    		for (int i = 0; i < 50; i++) {
    			// select second parent random
			System.out.print(i);
    			vector parent1 = parents.get(i);
    			vector parent2 = parents.get(rnd_.nextInt(50));
    			vector child1 =  lib.recombine(parent1, parent2, rnd_.nextInt(8));
    			vector child2 = lib.recombine(parent2, parent1, rnd_.nextInt(8));
    			// 
    			
    			// HIER ONDER ZOUDEN WE WILLEN EVALUEREN MAAR DAT WERKT NOG NIET!
    			// Ik denk dat ik elke keer een reference maak naar zelfde opject?? 
    			
    			//
    			//child1.fitness = (double) evaluation_.evaluate(child1.solution);
    			//evals++;
    			//child2.fitness = (double) evaluation_.evaluate(child2.solution);
				//child1.adjustFitness((double) evaluation_.evaluate(child1.solution));
    			//evals++;
    			children.add(child1);
    			children.add(child2);
			
    		}
    		
    		// Add mutated parents and children together, so no selection applied.
			pop.clear();    		
			pop.addAll(parents);
    		pop.addAll(children);
            parents.clear();
            children.clear();
            Collections.sort(pop);
    		
    		// Select survivors (remove the last best)
    		for (int i=100; i<150; i++ ){
    			pop.remove(100);
    		}
    		if (iter % 5 == 0) {
    			for (int i = 0; i<100; i++) {
    				for (int j = 0; j<10; j++) {
						if ((mutations/ 250) > 1/5 ) {
							pop.get(i).scalarArray[j].sigma = pop.get(i).scalarArray[j].sigma * 1.05;
						} else {
							pop.get(i).scalarArray[j].sigma = pop.get(i).scalarArray[j].sigma * 0.95;
						}
						mutations = 0;
    				}
    			}
    		}
        }
	}
}


// help function
//remove duplicates arrayList1.removeAll(arrayList2);

	  // Components of Evolutionary Algorithm
	  
		  /** - representation = (array of length 10)
		  *   - fitness function = (comes with the assignment)
		  *   - population = (N random chosen solutions)
		  *   - parent selection method = (tournament, ...)
		  *   - variation operators, recombination and mutation = (crossover, ...)
		  *   - survivor selection mechanism = (replacement, ...)
		  */
	  
	  // ASSIGMENT WITH THREE (OR FOUR) DIFFERENT KIND OF PROBLEMS
  		
		/** - 1 unimodal (no need for diversity)
		 * 		SETUP
		 * 		- more selective pressure? (less mutation more selection)
		 * 		- ...
		 * 		- ..
		 *  - 2 multimodal (diversity is needed)
		 *  	SETUP
		 *  	- higher mutation paramters (less crossover more selection)
		 *  	- ...
		 *  - 3 ..
		 *  - 4
		 */
	  
		// INITIATE POPULATION
		  
			  /** - make random solutions (say 100)
			   * - efficient to use problem-specific heuristics to obtain fitter start population?
			   */
		
		// DO WHILE/UNTILL
  
			  /** - elapse times
			   * - maximum number of fitness evaluations
			   * - no further improvement in fitness can be seen
			   * * - diversity threshold population has been reached
			   */ 
		  
			// DO A PARENT SELECTION  
			  
				  /** - get fitness if needed for all parents (tournament doesn't need)
				   * - create set parents by these fitnesses (stochastic)
				   * - 
				   */
			  
			// DO OFFSPRING
			  
				  /**  - use crossover 
				   * 
				   */
		  
		  	// DO MUTATION
		  
			  	  /** - whole population or part
			  	   *  - depends on last fitness improvement? (less mutation in ending)
			  	   *  - Gaussian pertubation
			  	   */
		  
		  	// DO SELECTION
		  
			  	  /** - get the fitness if necessary from all (not with tournaments)
			  	   *  - select from parents or only from children? (deterministic)
			  	   *  - lambda = 7 mu? (ration of offspring selection)
			  	   *  - ranking? (absolute, relative, ...)
			  	   *  - elitism, remove the worst, ...
			  	   *  - with replace?
			  	   *  - ...
			  	   */
		  
