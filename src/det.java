public class det {
    public static int nrOfMult;
    
    //Berechnung mit 1. Normalform
    public static double calcDet(double[][] A){
        double det = 0;
        if (isTriangularMatrix(A)) {
            for (int i = 0, j = 0; i < A.length && j < A.length; i++, j++)
                det *= A[i][j];

            return det;
        }

        else return Double.NaN;
    }

    //Rekursive Berechnung mit Def. L.4.1.1 Skript
    public static double calcDetRec(double[][] A){
        double det = 0;

        if (A.length == 1) {
            return A[0][0];
        }
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
        int p=0;

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
        int p=0;
        int m = A.length;

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
}







