
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
}

