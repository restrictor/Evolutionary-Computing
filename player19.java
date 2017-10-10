
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
	String fun;
	double learingRate;
	
	
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
		
	System.out.println(evaluations_limit_);
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

	learingRate = 2;
	if (multi && hasStruc) {
 		fun = "sch";
	} else if (multi) {
		fun = "kat";
	} else {
		fun = "ben";
	}
	

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
		learingRate = setup.learningRate;

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
		ArrayList<vector> robin = new ArrayList<vector>();
		ArrayList<vector> selection = new ArrayList<vector>();
		ArrayList<vector> fitnessSharing = new ArrayList<vector>();
        	// calculate fitness
		int mutations = 0;
		int no_mutations = 0;
		int iter = 0;
		
        while(evals<evaluations_limit_){
        	iter = iter + 1;

			//BentCigar
			//no_multi
			//hasNoStructure
			//isNotSeparable
        	if (fun == "ben") {
	
        		learingRate = 2;

        		for (int i = 0; i<POPULATION/2; i++) {
        			int ind = rnd_.nextInt(pop.size()-1);
        			parents.add(pop.get(ind));
        			pop.remove(ind);
        		}	
        		pop.clear();
	
				// mutatie
//				for (int i = POPULATION/4; i < POPULATION/2; i++) {
//		    			double[] mutation = parents.get(i).mutate();
//		    			double fitness = (double) evaluation_.evaluate(mutation);
//		    			//evals++;
//						if (fitness > parents.get(i).fitness) {
//							for (int j = 0; j < 10; j++) {
//								parents.get(i).scalarArray[j].x = mutation[j];
//								parents.get(i).solution[j]= mutation[j];			
//							}
//							parents.get(i).fitness = fitness;
//							mutations = mutations + 1;
//						} else {
//							no_mutations = no_mutations +1;
//						}
//		    	}
				
				for (int i = 0; i < 100; i++) {
	    			vector parent1 = parents.get(rnd_.nextInt(POPULATION/2));
	    			vector parent2 = parents.get(rnd_.nextInt(POPULATION/2));
					vector child1 = lib.recombine(parent1, parent2, rnd_.nextInt(10));
					vector child2 = lib.recombine(parent2, parent1, rnd_.nextInt(10));
					child1.fitness = (double) evaluation_.evaluate(child1.solution);
					child2.fitness = (double) evaluation_.evaluate(child2.solution);
					evals++;
					evals++;
					evals++;
					children.add(child1);
		    		children.add(child2);
				}
				
				
				// mutatie
				for (int i = 0; i < children.size(); i++) {
		    			double[] mutation = children.get(i).mutate();
		    			double fitness = (double) evaluation_.evaluate(mutation);
		    			//evals++;
						if (fitness > children.get(i).fitness) {
							for (int j = 0; j < 10; j++) {
								children.get(i).scalarArray[j].x = mutation[j];
								children.get(i).solution[j]= mutation[j];			
							}
							children.get(i).fitness = fitness;
							mutations = mutations + 1;
						} else {
							no_mutations = no_mutations +1;
						}
		    	}
				
				// (mu + sigma)
				pop.clear();    		
				pop.addAll(parents);
	    		pop.addAll(children);
	            parents.clear();
	            children.clear();
	            Collections.sort(pop);

				// survival selection
				pop.subList(POPULATION, pop.size()).clear();
	
				// 
				// Select parents (elite) (misschien fittness proportion
	    		//for (int i = 0; i<POPULATION/2; i++) {
	    		//	parents.add(pop.get(i));
	    		//}
				
	    		if ((iter % 5 == 0)) { 
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

		
        //katsuura
		//multi
		//hasNoStructure
		//isNotSeparable
		if (fun == "kat") {
			
			learingRate = 1.01;
			// WE LOSE DIVERSITY UNNECESSERY
			 //Stochastic universal sampling (more diversity)
//			for (int i = 0; i<5; i++) {
//				for (int j = 0; j< ((int) (90-(20*(i)))/5); j++) {
//					int min = i*20;
//					int max = ((i+1)*20)-1; 
//					parents.add(pop.get(rnd_.nextInt(max - min + 1) + min));
//				}
//    		}
//			pop.clear();

//			for (int i = 0; i<POPULATION/2; i++) {
//    			parents.add(pop.get(rnd_.nextInt(POPULATION)));
//    		}	
//    		pop.clear();
			
    		for (int i = 0; i<POPULATION/2; i++) {
    			int ind = rnd_.nextInt(pop.size()-1);
    			parents.add(pop.get(ind));
    			pop.remove(ind);
    		}	
	    	pop.clear();
			
			
			// mutatie
//			for (int i = 0; i < POPULATION/2; i++) {
//	    			double[] mutation = parents.get(i).mutate();
//	    			double fitness = (double) evaluation_.evaluate(mutation);
//	    			//evals++;
//					if (fitness > parents.get(i).fitness) {
//						for (int j = 0; j < 10; j++) {
//							parents.get(i).scalarArray[j].x = mutation[j];
//							parents.get(i).solution[j]= mutation[j];			
//						}
//						parents.get(i).fitness = fitness;
//						mutations = mutations + 1;
//					} else {
//						no_mutations = no_mutations +1;
//					}
//	    		}
			
			
			// offspring intermediate recombination
			for (int i = 0; i < 200; i++) {
				if (rnd_.nextDouble()<0.3) {
					int par1 = rnd_.nextInt(parents.size()-1); 
					int par2 = rnd_.nextInt(parents.size()-1); 
					int par3 = rnd_.nextInt(parents.size()-1);
					vector child = new vector();
					for (int l = 0; l < 10; l++) {
						double p1x = parents.get(par1).scalarArray[l].x;
						double p2x = parents.get(par2).scalarArray[l].x;
						double p3x = parents.get(par3).scalarArray[l].x;
						double a = 0.33;  //Math.random();
						double newx = Math.max(Math.min((a * (p1x + p2x + p3x)), 5), -5);
						child.scalarArray[l].x = newx;
						child.scalarArray[l].sigma = parents.get(par2).scalarArray[l].sigma;
						child.solution[l] = newx;
					}
					child.fitness = (double) evaluation_.evaluate(child.solution);
					evals++;
					evals++;
					children.add(child);
				} else {
					int par = rnd_.nextInt(parents.size()-1); 
					vector child = new vector();
					for (int l = 0; l < 10; l++) {
						child.scalarArray[l].x = parents.get(par).scalarArray[l].x;
						child.solution[l] = parents.get(par).solution[l];
						child.scalarArray[l].sigma = parents.get(par).scalarArray[l].sigma;
					}
					child.fitness = parents.get(par).fitness;
					children.add(child);
				}
			}
			// uniform crossover
//			for (int i = 0; i < 50; i++) {
//	    		vector parent1 = parents.get(i);;
//	    		vector parent2 = parents.get(rnd_.nextInt(POPULATION/2));
//				vector[] twins = lib.uniformCrossover(parent1, parent2);
//	    		vector child1 =  twins[0];
//				vector child2 =  twins[1];
//				child1.fitness = (double) evaluation_.evaluate(child1.solution);
//				//evals++;
//				child2.fitness = (double) evaluation_.evaluate(child2.solution);
//				evals++;
//				evals++;
//				evals++;
//				children.add(child1);
//	    		children.add(child2);
//			}	
			
//			// mutatie
			for (int i = 0; i < children.size(); i++) {
	    			double[] mutation = children.get(i).mutate();
	    			double fitness = (double) evaluation_.evaluate(mutation);
	    			evals++;
					if (fitness > children.get(i).fitness) {
						for (int j = 0; j < 10; j++) {
							children.get(i).scalarArray[j].x = mutation[j];
							children.get(i).solution[j]= mutation[j];			
						}
						children.get(i).fitness = fitness;
						mutations = mutations + 1;
					} else {
						no_mutations = no_mutations +1;
					}
	    	}
			
			
			// (mu,sigma)
			pop.clear();    		
    		pop.addAll(children);
    		pop.addAll(parents);
            parents.clear();
            children.clear();
            //Collections.sort(pop);

			// survival selection
			//pop.subList(POPULATION, pop.size()).clear();
//	    	
			for (int i = 0; i < 100; i++) {
				for (int j=0; j < 15; j++) {
	    				robin.add(pop.get(rnd_.nextInt(pop.size()-1)));
				}
				Collections.sort(robin);
				selection.add(robin.get(0));				
				robin.clear();
			}
			pop.clear();
			pop.addAll(selection);
			selection.clear();
			Collections.sort(pop);
			
//	    	double share = 5.236;	
//	    	//System.out.println(pop.size());
//	    	if ((iter % 25 == 0)) { 
//				for (int i = 0; i<pop.size(); i++) {
//					for (int k = 0; k < 10; k++) {
//						//System.out.println(pop.get(i).solution[k]);
//					}
//					for (int j = 0; j<pop.size(); j++) {
//						double sumDistances = 0;
//						//System.out.println(lib.distance(pop.get(i).solution, pop.get(j).solution));
//						if (lib.distance(pop.get(i).solution, pop.get(j).solution) < share) {
//							fitnessSharing.add(pop.get(j));
//							double dist = lib.distance(pop.get(i).solution, pop.get(j).solution);
//							sumDistances = sumDistances + (1-(dist/share));
//						}
//						pop.get(i).fitness = pop.get(i).fitness / sumDistances;
//					}
//				}
//	    	}
	    	
	    	if ((iter % 5 == 0)) { 
    			
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

		if (fun == "sch") {
			
			learingRate= 1.2239;
			//Schaffers
			//multi
			//hasStructure
			//isNotSeparable

			// Select parents (elite)
//	    		for (int i = 0; i<POPULATION/2; i++) {
//	    			parents.add(pop.get(i));
//	    		}
//	    		pop.clear();
    		for (int i = 0; i<POPULATION/2; i++) {
    			int ind = rnd_.nextInt(pop.size()-1);
    			parents.add(pop.get(ind));
    			pop.remove(ind);
    		}
	    		
	    		
			// mutation
//			for (int i = POPULATION/4; i < POPULATION/2; i++) {
//    			double[] mutation = parents.get(i).mutate();
//    			double fitness = (double) evaluation_.evaluate(mutation);
//    			//evals++;
//				if (fitness > parents.get(i).fitness) {
//					for (int j = 0; j < 10; j++) {
//						parents.get(i).scalarArray[j].x = mutation[j];
//						parents.get(i).solution[j]= mutation[j];			
//					}
//					parents.get(i).fitness = (double) fitness;
//					mutations = mutations + 1;
//				} else {
//					no_mutations = no_mutations +1;
//				}
//    			}

			// uniform crossover
			for (int i = 0; i < 50; i++) {
	    		vector parent1 = parents.get(i);;
	    		vector parent2 = parents.get(rnd_.nextInt(POPULATION/2));
				vector[] twins = lib.uniformCrossover(parent1, parent2);
	    		vector child1 =  twins[0];
				vector child2 =  twins[1];
				child1.fitness = (double) evaluation_.evaluate(child1.solution);
				//evals++;
				child2.fitness = (double) evaluation_.evaluate(child2.solution);
				evals++;
				evals++;
				evals++;
				children.add(child1);
	    		children.add(child2);
			}	
	
			// mutatie
			for (int i = 0; i < children.size(); i++) {
	    			double[] mutation = children.get(i).mutate();
	    			double fitness = (double) evaluation_.evaluate(mutation);
	    			//evals++;
					if (fitness > children.get(i).fitness) {
						for (int j = 0; j < 10; j++) {
							children.get(i).scalarArray[j].x = mutation[j];
							children.get(i).solution[j]= mutation[j];			
						}
						children.get(i).fitness = fitness;
						mutations = mutations + 1;
					} else {
						no_mutations = no_mutations +1;
					}
	    	}
			
			// (mu,sigma)
			pop.clear();    		
    		pop.addAll(children);
    		pop.addAll(parents);
            parents.clear();
            children.clear();
            Collections.sort(pop);


			// survival selection
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
}


// help function
//remove duplicates arrayList1.removeAll(arrayList2);




