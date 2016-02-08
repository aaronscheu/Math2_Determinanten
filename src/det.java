public class det {
    public static int nrOfMult;
    
    //Berechnung mit 1. Normalform
    public static double calcDet(double[][] A){
        int counter = 0; // increase starting parameter
        double det = 0;

        while(!is1NF(A)){ // Noch keine 1.NF
            A = processMatrix(A, counter, counter);
            counter++;
        }


        det =   A[A.length-2][A.length-2] * A[A.length-1][A.length-1] -
                A[A.length-2][A.length-1] * A[A.length-1][A.length-2];

        nrOfMult += 2;
        return det;
    }

    //Rekursive Berechnung mit Def. L.4.1.1 Skript

    /**
     * Calculation of determinant is done by recursive implementation
     * of the LaPlace algorithm.
     * @param A 2D matrix with double values
     * @return double value which represents the determinant
     */
    public static double calcDetRec(double[][] A){
        double det = 0;

        //Base case no. 1: Matrix is 1x1
        if (A.length == 1) {
            return A[0][0];
        }

        //Base case no. 2: Matrix is 2x2
        //Determinant is calculated by subtracting the products of both diagonals.
        else if (A.length == 2) {
            det = A[0][0] * A[1][1] - A[0][1] * A[1][0];
            nrOfMult += 2;
            return det;
        }
        else {
            for (int line = 0; line < A.length; line++) {

                if (line % 2 == 0)
                    det += A[line][0] * calcDetRec(makeSubMatrix(A, line, A.length));
                else
                    det -= A[line][0] * calcDetRec(makeSubMatrix(A, line, A.length));

                nrOfMult++;
            }
        }
        return det;
    }


    /*** Generate submatrix with size -1 ***/
    private static double[][] makeSubMatrix(double[][] A, int line, int size) {
        double[][] B = new double[size - 1][size - 1];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < (size - 1); j++) {

                if (i < line) {
                    B[i][j] = A[i][j + 1];
                }
                else if (i > line) {
                    B[i - 1][j] = A[i][j + 1];
                }
            }
        }

        return B;
    }

    private static boolean isTriangularMatrix (double[][] A) {
        return isLowerTriangularMatrix(A) || isUpperTriangularMatrix(A);
    }

    private static boolean isUpperTriangularMatrix (double[][] A) {
        int m = A.length;
        int p = 0;

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < i; j++) {

                if(A[i][j]!= 0) {
                    p = 1;
                    break;
                }
            }
        }

        return (p == 0);
    }

    private static boolean isLowerTriangularMatrix (double[][] A) {
        int m = A.length;
        int p = 0;

        for(int i = 0; i < m; i++) {
            for(int j = i+1; j < m; j++) {

                if(A[i][j]!= 0) {
                    p = 1;
                    break;
                }
            }
        }

        return (p == 0);
    }

    
    private static double[][] processMatrix(double[][] A, int startZeile, int startSpalte){
    	// Mach die gesamte Spalte zu 1 indem die Werte auf der Diagonalen durch
        // sich selbst geteilt werden, z.B. 3 / 3 = 1
    	// Ziel = Diagonale aus 1

    	double[] diagonalRow = A[startZeile];
    	for(int i = startZeile; i < A.length; i++){
    		double divideValue = A[startZeile][startSpalte];
    		A[i] = divideLineValues(A[i], divideValue);
    	}
    	
    	// Jetzt sollte die ganze Spalte aus 1 bestehen
    	// Nun die 1 unter den Diagonalen zu 0 machen
    	// Zeilen unter der Diagonalen mit der ersten Zeile subtrahieren
    	// Anfangen mit der ersten Zeile UNTER der Diagonalen
    	for(int i = startZeile + 1; i < A.length; i++){
    		A[i] = subtractLines(A[i], diagonalRow);
    	}

    	return A;
    }

    // Teilt eine gesamte Zeile mit einem angegebenen Quotienten
    private static double[] divideLineValues(double[] row, double divideValue){
    	// Hier nur die gesamte Zeile durchgehen 
    	double[] tempArray = new double[row.length];
    	for(int spalte = 0; spalte < row.length; spalte++){
    		tempArray[spalte] = row[spalte] / divideValue;
    	}
    	return tempArray;
    }

    // Zieht zwei Zeilen voneinander ab
    private static double[] subtractLines(double[] resultLine, double[] subtractionLine){
    	double[] tempArray = new double[resultLine.length];
		for(int spalte = 0; spalte < resultLine.length; spalte++){
			tempArray[spalte] = resultLine[spalte] - subtractionLine[spalte];
		}
		return tempArray;
    }
    
    // Matrix auf erste Normalform ueberpruefen
    // Besteht Diagonale aus 1?
    // Auf Nullzeilen achten!
    private static boolean is1NF(double[][] A){
    	for(int i = 0; i < A.length; i++){
    		for(int j = 0; j < A[i].length; j++){
    			if(i == j){

    				// Diagonale
    				if(A[i][j] != 1){
    					if(A[i][j] != 0){
    						return false; // Keine Nullzeile
    					}
    					else{
    						// Erst nach links pruefen, dann nach rechts
        					for(int links = j-1; links > -1; links--){
        						if(A[i][links] != 0){ // Keine Nullzeile
        							return false;
        						}
        					}
        					for(int rechts = j+1; rechts < A.length; rechts++){
        						if(A[i][rechts] != 0){ // Keine Nullzeile
        							return false;
        						}
        					}
    					}
        			}
    			}
    		}
    	} // complete

        //Pruefung, ob unter Diagonale Nullen vorhanden
    	for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < i; j++) {

                if(A[i][j]!= 0) {
                	return false;
                }
            }
        }

    	return true;
    }
}







