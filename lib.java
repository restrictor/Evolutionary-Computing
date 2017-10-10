
import java.util.Random;

public class lib {
	
	/**
	 * @param args
	 * This file consist of used functions for the AE
	 * 
	 */

	static vector recombine(vector parent1, vector parent2, int position) {
		scalar[] s = new scalar[10];
		for (int i = 0; i <10; i++){
			s[i] = new scalar();
			if (i<position) {
				s[i].x = parent1.scalarArray[i].x;
				s[i].sigma = parent1.scalarArray[i].sigma;
			} else {
				s[i].x = parent2.scalarArray[i].x;
				s[i].sigma = parent2.scalarArray[i].sigma;
			}
		}
		vector a = new vector(s);
		return a;
	}

	static vector[] uniformCrossover(vector parent1, vector parent2) {
		Random r = new Random();		
		vector child1 = copyVector(parent1);
		vector child2 = copyVector(parent2);
		for (int i = 0; i <10; i++){
			if (Math.random()<0.5) {
				child1.scalarArray[i].x = parent2.scalarArray[i].x;
				child1.scalarArray[i].sigma = parent2.scalarArray[i].sigma;
				child2.scalarArray[i].x = parent1.scalarArray[i].x;
				child2.scalarArray[i].sigma = parent1.scalarArray[i].sigma;
			}
		}
		vector[] twins = new vector[2];
		twins[0] = child1;
		twins[1] = child2;
 		return twins;
	}

	static vector copyVector(vector original) {
		scalar[] s = new scalar[10];
		for (int i = 0; i <10; i++){
			s[i] = new scalar();
			s[i].x = original.scalarArray[i].x;
			s[i].sigma = original.scalarArray[i].sigma;  		
		}
		vector copy = new vector(s);
		copy.fitness = original.fitness;
		return copy; 
	}

	static double distance(double[] s1, double[] s2) {
		double dist = 0;
		for (int i = 0; i <10; i++){
			//System.out.println(s1[i]);
			//System.out.println(s2[i]);
			dist = dist + ((s1[i]-s2[i]) * (s1[i] - s2[i]));
		}
		return Math.sqrt(dist);
	}
	
}

