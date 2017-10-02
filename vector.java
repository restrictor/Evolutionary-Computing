
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
	
	vector(scalar[] scalarArray, double fitness) {
		sol = new double[DIMENSIONS];
		for (int i = 0; i < DIMENSIONS; i++) {
			sol[i] = scalarArray[i].x;
		}
		this.solution = sol;
		this.scalarArray = scalarArray;
		this.fitness = fitness;
	}
	
	void mutate() {
		for (int i = 0; i < DIMENSIONS; i++) {
			this.scalarArray[i].addGaussian();
			this.solution[i] = this.scalarArray[i].x;
		}
	}
}
