package player19;

import java.util.Random;

public class scalar {

	/**
	 * @param args
	 * x 		the scalar in that dimension
	 * sigma	the learning parameter in that dimension
	 */

	double x;
	double sigma;
	
	scalar() {
		double random = Math.random();
		this.x = random;
		this.sigma = 1;
		
	}
	
	scalar(double x, double sigma) {
		this.x = x;
		this.sigma = sigma;
	}

	void average(scalar otherParent, double alpha) {
		this.x = (otherParent.x * (alpha) + x *(1-alpha));
	}
	
	void addGaussian() {
		Random r = new Random();
		this.x = r.nextGaussian()*this.sigma + this.x;
	}
	
	void updateSigma(double oldFitness, double newFitness) {
		this.sigma = Math.abs(oldFitness - newFitness);
	}
	
}