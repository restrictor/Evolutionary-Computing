

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
		multi = true;
        }else{
		multi = false;
        }
	if(hasStructure){
		hasStruc = true;
        }else{
		hasStruc = false;
        }
	if(isSeparable){
		isSep = true;
        }else{
		isSep = false;
        }
//katsuura
//multi
//hasNoStructure
//isNotSeparable

//BentCigar
//no_multi
//hasNoStructure
//isNotSeparable

//Schaffers
//multi
//hasStructure
//isNotSeparable

		/** - representation = (array of length 10)
		  *   - fitness function = (comes with the assignment)
		  *   - population = (N random chosen solutions)
		  *   - parent selection method = (tournament, ...)
		  *   - variation operators, recombination and mutation = (crossover, ...)
		  *   - survivor selection mechanism = (replacement, ...)
		  */

    }
    
	public void run()
	{
		// Run your algorithm here
        	settings setup = new settings();
		int evals = 0;
        	int POPULATION = setup.population; // needs to be even number and 4 as comon divider
		double learingRate = setup.learningRate;
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
    			for (int i = 0; i<pop.size(); i++) {
    				for (int j = 0; j<10; j++) {
						if (mutations/(mutations + no_mutations) > 1/5) {
							pop.get(i).scalarArray[j].sigma = pop.get(i).scalarArray[j].sigma * learingRate;
						} else {
							pop.get(i).scalarArray[j].sigma = pop.get(i).scalarArray[j].sigma / learingRate;
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

