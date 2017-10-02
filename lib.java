
public class lib {
	
	/**
	 * @param args
	 * This file consist of used functions for the AE
	 * 
	 */

	static vector[] recombine(vector parent1, vector parent2, int position) {
		scalar[] scalarArray1 = new scalar[10];
		scalar[] scalarArray2 = new scalar[10];
		for (int i = 0; i <10; i++){
			if (i<position) {
				scalarArray1[i] = parent1.scalarArray[i];
				scalarArray2[i] = parent2.scalarArray[i];
				
			} else {
				scalarArray1[i] = parent2.scalarArray[i];
				scalarArray2[i] = parent1.scalarArray[i];
			}
		}
		vector[] twins = new vector[2];
		twins[0] = new vector(scalarArray1,0.0);
		twins[1] = new vector(scalarArray2,0.0);
		return twins;
	}
}
