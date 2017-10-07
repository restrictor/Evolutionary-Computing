
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
	boolean multi;
	boolean hasStruc;
	boolean isSep;
	
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
            System.out.println("multi");
		multi = true;
        }else{
            System.out.println("no_multi");
		multi = false;
        }
	if(hasStructure){
		hasStruc = true;
            System.out.println("hasStructure");
        }else{
            System.out.println("hasNoStructure");
		hasStruc = false;
        }
	if(isSeparable){
		isSep = true;
            System.out.println("isSeparable");
        }else{
            System.out.println("isNotSeparable");
		isSep = false;
        }
    }
    
	public void run()
	{
		// Run your algorithm here
        	settings setup = new settings();
		int evals = 0;
        	int POPULATION = setup.population; // needs to be even number and 4 as comon divider
        // Initialize population
		ArrayList<vector> pop = new ArrayList<vector>();
		for (int i=0; i<POPULATION; i++ ){
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
		int no_mutations = 0;
		int iter = 0;
		
        while(evals<evaluations_limit_){
		iter = iter + 1;

		// Select parents (very basic selection: take best half)
    		for (int i = 0; i<POPULATION/2; i++) {
    			parents.add(pop.get(i));
    		}
    		pop.clear();
    		
            // Apply crossover / mutation operators 
    		for (int i = POPULATION/4; i < POPULATION/2; i++) {
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
				} else {
					no_mutations = no_mutations +1;
				}
    		}
    		for (int i = 0; i < POPULATION/2; i++) {
    			vector parent1 = parents.get(i);
    			vector parent2 = parents.get(rnd_.nextInt(POPULATION/2));
			if (multi) {
				vector[] twins = lib.uniformCrossover(parent1, parent2);
	    			vector child1 =  twins[0];
				vector child2 =  twins[1];
				child1.fitness = (double) evaluation_.evaluate(child1.solution);
				evals++;
				child2.fitness = (double) evaluation_.evaluate(child2.solution);
				evals++;
				evals++;  
				children.add(child1);
	    			children.add(child2);
			} else {
				// this works very good for no_multi
				vector child1 = lib.recombine(parent1, parent2, rnd_.nextInt(8));
				vector child2 = lib.recombine(parent2, parent1, rnd_.nextInt(8));
				children.add(child1);
	    			children.add(child2);
			}
    		}
    		
			pop.clear();    		
			pop.addAll(parents);
    		pop.addAll(children);
            parents.clear();
            children.clear();
            Collections.sort(pop);
    		
    		// Select survivors (remove the last best)
    		pop.subList(POPULATION, pop.size()).clear();
    		if ((iter % 5 == 0)) { // && (multi)) {
    			for (int i = 0; i<POPULATION; i++) {
    				for (int j = 0; j<10; j++) {
						if (mutations/(mutations + no_mutations) > 1/5) {
							pop.get(i).scalarArray[j].sigma = pop.get(i).scalarArray[j].sigma * 1.05;
						} else {
							pop.get(i).scalarArray[j].sigma = pop.get(i).scalarArray[j].sigma * 0.95;
						}
						
    				}
    			}
    			mutations = 0;
			no_mutations = 0;
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
		  
