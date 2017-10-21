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
	String fun;
	double learingRate;
	int POPULATION;
	double updateInterval;
	double mu;
	int mut;
	int no_mut;
	
	
	public player19() {
		rnd_ = new Random();
	}
	
	public void setSeed(long seed) {
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation) {
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
		if (isMultimodal && hasStructure) {
	 		fun = "sch";
		} else if (isMultimodal) {
			fun = "kat";
		} else {
			fun = "ben";
		}
    }
    
	public void run() {
		
		// Run your algorithm here

		// load settings from file
		settings setup = new settings();
		int evals = 0;
        	POPULATION = setup.population; 
		learingRate = setup.learningRate;
		updateInterval = setup.updateInterval;
		mu = setup.mu;

		// override settings manually
		if (fun == "sch") {
	 		POPULATION = 160;
			learingRate = 2;
			updateInterval = 20; //20
		} else if (fun == "kat") {
	 		POPULATION = 40; //40
			learingRate = 1.01; //1.01
			updateInterval = 5; //5
		} else {
	 		POPULATION = 20;
			learingRate = 3;
			updateInterval = 15; //15
		}

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

		int mutations = 0;
		int no_mutations = 0;
		int iter = 0;
		boolean print = false;
		System.out.println("Settings: " + POPULATION +  ", " + learingRate + ", " + updateInterval);

		//if ((fun == "kat")) { 
		//	for (int i = 0; i<pop.size(); i++) {
		//		for (int j = 0; j<10; j++) {
		//			pop.get(i).scalarArray[j].sigma = pop.get(i).scalarArray[j].sigma * 0.1;
		//		}
		//	}
		//}

        while(evals<evaluations_limit_) {
		
        ////////////////////////////////////////////////////////////	
        //
        // PRINTING THE STATISTICS HERE
			
			System.out.println("Iter: " + iter);
			System.out.println("Eval: " + evals);
			System.out.println("sigma: " + pop.get(0).scalarArray[0].sigma);	
			System.out.println("Update: " + updateInterval);
			System.out.println("max: " + pop.get(0).fitness);
			System.out.println("min: " + pop.get(POPULATION -1).fitness);
			double sum = 0;
			double[] f = new double[POPULATION];			
			for (int i = 0; i<POPULATION; i++) {
				sum = sum + pop.get(i).fitness/POPULATION;
				f[i] = Math.floor(100*pop.get(i).fitness);
				//System.out.println(f[i]);
			}
			System.out.println("Uni: " + lib.FindTotalUniqueNumbers(f));
			System.out.println("Average: " + sum);
			double dist = 0;			
			for (int i = 0; i<POPULATION; i++) {
				for (int j = 0; j<POPULATION; j++) {
					dist = dist + lib.distance(pop.get(i).solution,pop.get(j).solution)/2;
				}
				
			}
			System.out.println("Dist: " + Math.pow(dist, 1.0 / 3.0));
		
			//for (int i = 0; i<POPULATION; i++) {		
				//double[] sol = pop.get(i).solution;
				//System.out.println("sol: " + sol[0] + " " + sol[1] + " " + sol[2] + " " + sol[3] + " " + sol[4] + " " + sol[5] + " " + sol[6] + " " + sol[7] + " " + sol[8]+ " " + sol[9] + " "  + pop.get(i).fitness);
//}
        //
        ////////////////////////////////////////////////////////////
        	
        	iter = iter + 1;

			//BentCigar
        	if (fun == "ben") {
	
        		for (int i = 0; i<POPULATION/2; i++) {
        			int ind = rnd_.nextInt(pop.size()-1);
        			parents.add(pop.get(ind));
        			pop.remove(ind);
        		}	
        		pop.clear();
	
				// recombination
				for (int i = 0; i < POPULATION; i++) {
	    			vector parent1 = parents.get(rnd_.nextInt(parents.size()-1));
	    			vector parent2 = parents.get(rnd_.nextInt(parents.size()-1));
					vector child1 = lib.recombine(parent1, parent2, rnd_.nextInt(10));
					vector child2 = lib.recombine(parent2, parent1, rnd_.nextInt(10));
	    			child1.fitness = (double) evaluation_.evaluate(child1.solution);
					evals++;
					child2.fitness = (double) evaluation_.evaluate(child2.solution);
					evals++;
					children.add(child1);
		    		children.add(child2);
				}
				
				// mutatie
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
				
				// (mu + sigma)
				pop.clear();    		
				pop.addAll(parents);
	    		pop.addAll(children);
	            parents.clear();
	            children.clear();
	            Collections.sort(pop);

				// survival selection elitism
				pop.subList(POPULATION, pop.size()).clear();

				// update sigma
	    		if ((iter % updateInterval == 0)) { 
	    			for (int i = 0; i<pop.size(); i++) {
	    				for (int j = 0; j<10; j++) {
							if ((double) mutations/( (double) mutations + (double) no_mutations) > 1.0/5) {
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
			if (fun == "kat") {
			
				if (mu ==0) {
					// 2nd order addaptive
					if (iter % 7 == 0 & iter % 5 != 0) {
						if (mut/(mut + no_mut) > 1/5) {
							updateInterval = Math.max(1, updateInterval - 1);
						} else {
							updateInterval = Math.min(20, updateInterval + 1);
						}
					mut = 0;
					no_mut = 0;
					}
				} else if (mu == 1) {
					// lineair increasing
					if (iter % 39 == 0 & iter % 5 != 0) {
						updateInterval = Math.floor(Math.min(1 + evals*(19.0/1000000), 20));
					}	
				} else if (mu == 2) {
					if (iter % 39 == 0 & iter % 5 != 0) {
						// linear decreasing
						updateInterval = Math.floor(Math.max(1 , 20.0 - evals*(19.0/1000000)));
					}
				}		

			// keep the best
			//vector best = pop.get(0);

				// parents selection random uniform
			if (iter <= 1) { 
		    		for (int i = 0; i < POPULATION/2; i++) { // POPULATION/2
					//System.out.println("help" + iter);
		    			int ind = rnd_.nextInt(pop.size()-1);
		    			parents.add(pop.get(ind));
		    			pop.remove(ind);
		    		}
			} else {	
				for (int i = 0; i < 80; i++) {
					//System.out.println("help" + iter);
		    			int ind = rnd_.nextInt(pop.size()-1);
		    			parents.add(pop.get(ind));
		    			pop.remove(ind);
		    		}
			}
		
	    		
	
				// offspring intermediate recombination
				for (int i = 0; i < 2 * 100; i++) {
					if (rnd_.nextDouble()<0.7) {
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

				
				// mutatie
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
							mut = mut + 1;
						} else {
							no_mutations = no_mutations +1;
							no_mut = no_mut + 1;
						}
		    	}
				
				
				// (mu,sigma)
				pop.clear();  
	    			pop.addAll(children);
	    			pop.addAll(parents);
	            		parents.clear();
	            		children.clear();

				// survival selection

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
				// add the best
				//pop.add(best);
				selection.clear();
				Collections.sort(pop);
				
		    	
		    	if ((iter % updateInterval == 0)) { 
	    			//System.out.println("mutation " + mutations);
								//System.out.println("no_mutation" + no_mutations);
								//System.out.println("ratio"+ mutations/(mutations + no_mutations));
		    		for (int i = 0; i<pop.size(); i++) {
	    				for (int j = 0; j<10; j++) {

							if ((double) mutations/((double) mutations + (double) no_mutations) > 1.0/5) {
								
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
		
			// Schaffers
			if (fun == "sch") {
	
				// parents selection random uniform
	    		for (int i = 0; i<POPULATION/2; i++) {
	    			int ind = rnd_.nextInt(pop.size()-1);
	    			parents.add(pop.get(ind));
	    			pop.remove(ind);
	    		}
	
				// uniform crossover
				for (int i = 0; i < parents.size(); i++) {
		    		vector parent1 = parents.get(i);;
		    		vector parent2 = parents.get(rnd_.nextInt(parents.size()-1));
					vector[] twins = lib.uniformCrossover(parent1, parent2);
		    		vector child1 =  twins[0];
					vector child2 =  twins[1];
	   				child1.fitness = (double) evaluation_.evaluate(child1.solution);
					evals++;
					child2.fitness = (double) evaluation_.evaluate(child2.solution);
					evals++;
					children.add(child1);
		    		children.add(child2);
				}	
		
				// mutatie
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
				
				// (mu + sigma)
				pop.clear();    		
	    		pop.addAll(children);
	    		pop.addAll(parents);
	            parents.clear();
	            children.clear();
	            Collections.sort(pop);
	
				// survival selection
				pop.subList(POPULATION, pop.size()).clear();
	
				// update sigma
				if ((iter % updateInterval == 0)) { 
					System.out.println((double) mutations/((double) mutations + (double) no_mutations));
	    			for (int i = 0; i<pop.size(); i++) {
	    				for (int j = 0; j<10; j++) {
							if ((double) mutations/((double) mutations + (double) no_mutations) > 1.0/5) {
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


