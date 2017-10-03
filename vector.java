import java.util.Random;

public class vector implements Comparable<vector>{

	/**
	 * @param args
	 * 	vector	a 10 dimensional solution for the function at hand
	 */

    @Override
    public int compareTo(vector o) {
        return new Double(fitness).compareTo(fitness);
    }
	
	static int DIMENSIONS = 10;
	double fitness;
	scalar[] s;
	double[] sol;
	public scalar[] scalarArray;
	public double[] solution;
	
	vector() {
		s = new scalar[DIMENSIONS];
		sol = new double[DIMENSIONS];
		for (int i = 0; i < DIMENSIONS; i++) {
			s[i] = new scalar();
			sol[i] = s[i].x;
		}
		this.scalarArray = s;
		this.fitness = 0.0;
		this.solution = sol;
	}
	
	vector(scalar[] s) {
		sol = new double[DIMENSIONS];
		for (int i = 0; i < DIMENSIONS; i++) {
			sol[i] = s[i].x;
		}
		this.solution = sol;
		this.scalarArray = s;
		this.fitness = 0.0;
	}
	
	double[] mutate() {
		double[] sol2 = new double[DIMENSIONS];
		for (int i = 0; i < DIMENSIONS; i++) {
			Random r = new Random();
			sol2[i] = (r.nextGaussian()*2*this.scalarArray[i].sigma) + this.scalarArray[i].x;
			sol2[i] = Math.min(Math.max(-5, sol2[i]),5);
		}
		return sol2;
	}
	
	void adjustFitness(double fit) {
		this.fitness = fit;
	}
}
