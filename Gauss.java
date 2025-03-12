package Gauss;

import Gauss.Rational;

public class Gauss {
    private Rational [][] extendedMatrix;
    private Rational aux = new Rational(0);
    private Rational aux2 = new Rational(0);
    private int n,m;
    private String[] ops;

    public Gauss(Rational[][] extendedMatrix){
        this.extendedMatrix = extendedMatrix;
        n = extendedMatrix.length;
        m = extendedMatrix[0].length;
        
        ops = new String[n];

        for (int i = 0; i < n; i++) {
            ops[i] = "";
        }
    }

    public Gauss(int[][] extendedMatrix){
        this.extendedMatrix = new Rational[extendedMatrix.length][extendedMatrix[0].length];

        n = extendedMatrix.length;
        m = extendedMatrix[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.extendedMatrix[i][j] = new Rational(extendedMatrix[i][j]);
            }
        }

        ops = new String[n];

        for (int i = 0; i < n; i++) {
            ops[i] = "";
        }
    }

    public void compute(){
        if(!computeGauss())
            return;
        if(!areRowNBelowZero(m-1)){
            System.out.println("\t\t\t----- SIN SOLUCIÓN -----");
            System.out.println(this);
            return;
        }
        computeJordan();
        printResult();
    }

    private void printResult(){
        for (int i = 0; i < m-1; i++){
            System.out.print("\n\t");
            if (i>n-1||extendedMatrix[i][i].isZero()){
                System.out.print("    "+(char)('a'+i)+"   =   "+(char)('s'+i));
                continue;
            }
            System.out.print("    "+(char)('a'+i)+"   = "+extendedMatrix[i][m-1]);
            for (int j = i+1; j < m-1; j++) {
                extendedMatrix[i][j].copyTo(aux).aInverse();
                if(!aux.isZero())
                    System.out.print(" "+aux.toSignedString()+" "+(char)('s'+j));
            }
        }
        System.out.print('\n');
    }

    private void computeJordan(){
        System.out.println("\t\t\t\t---- JORDAN ----");
        for (int i = m<n ? m-1: n-1; i >= 0; i--) {
            System.out.println(this);
            for (int j = i-1; j >= 0; j--) {
                if(!extendedMatrix[j][i].equals(Rational.ZERO)){
                    extendedMatrix[j][i].copyTo(aux).aInverse();
                    mulSum(aux, i, j);
                }
            }
        }
    }

    private boolean computeGauss(){
        System.out.println("\t\t\t\t---- GAUSS ----");
        for (int i = 0; i < m-1; i++) {
            if(i>n-1){
                System.out.println("\t\t\t----- SOLUCIÓN MULTIPLE -----");
                break;
            }
            System.out.println(this);

            // See if there is a single variable equation(shortcut)
            int row_ready = findReadyRow(i);
            if(row_ready != -1){
                // Prevent switching row with itselft
                if(row_ready != i){
                    switchRows(i, row_ready);
                    System.out.println(this);
                }
                // Ensure coefficient equals to one
                if(!extendedMatrix[i][i].equals(Rational.ONE)){
                    extendedMatrix[i][i].copyTo(aux).mInverse();
                    multiplyRow(aux, i);
                    System.out.println(this);
                }
            // Ensure coefficient equals to one
            }else if(!extendedMatrix[i][i].equals(Rational.ONE)){
                // See if there is a row with coefficient equals to one
                int findone = findBelow(i, Rational.ONE);
                if(findone != -1){
                    switchRows(i, findone);
                }else{
                    // Ensure coefficient is not zero
                    if(extendedMatrix[i][i].equals(Rational.ZERO)){
                        int findany = findFirstDiferentBelow(i, Rational.ZERO);
                        if(findany != -1){
                            switchRows(i, findany);
                            System.out.println(this);
                        }else{
                            if(areRowNBelowZero(i)){
                                System.out.println("\t\t\t----- SOLUCIÓN MULTIPLE -----");
                                return true;
                            }else{
                                System.out.println("\t\t\t----- SIN SOLUCIÓN -----");
                                return false;
                            }
                        }
                    }
                    extendedMatrix[i][i].copyTo(aux).mInverse();
                    multiplyRow(aux, i);
                }
                System.out.println(this);
            }

            for (int j = i+1; j < n; j++) {
                if(!extendedMatrix[j][i].equals(Rational.ZERO)){
                    extendedMatrix[j][i].copyTo(aux).aInverse();
                    mulSum(aux, i, j);
                }
            }
        }

        System.out.println(this);
        return true;
    }

    private boolean ensureNotZero(int index){
        return true;
    }

    public int findReadyRow(int index){
        for (int i = index; i < n; i++)
            if(isRowReady(i, index))
                return i;
        return -1;
    }
    
    public boolean isRowReady(int nindex, int mindex){
        if(extendedMatrix[nindex][mindex].isZero())
            return false;
        for(int i = mindex+1; i < m-1; i++)
            if(!extendedMatrix[nindex][i].isZero())
                return false;
        return true;
    }

    public boolean areRowNBelowZero(int index){
        for (int i = index; i < n; i++)
            if(!isRowZero(i))
                return false;
        return true;
    }

    public boolean isRowZero(int index){
        for (Rational rational : extendedMatrix[index])
            if(!rational.isZero())
                return false;
        return true;
    }

    public int findFirstDiferentBelow(int dindex, Rational not){
        for (int i = dindex+1; i < this.n; i++) {
            if(!extendedMatrix[i][dindex].equals(not)){
                return i;
            }
        }
        return -1;
    }

    public int findBelow(int dindex, Rational num){
        for (int i = dindex+1; i < this.n; i++) {
            if(extendedMatrix[i][dindex].equals(num)){
                return i;
            }
        }
        return -1;
    }

    public void switchRows(int a, int b){
        Rational[] temp = extendedMatrix[a];
        extendedMatrix[a] = extendedMatrix[b];
        extendedMatrix[b] = temp;
        ops[a]="R"+(a+1)+","+(b+1)+"\t";
    }

    public void multiplyRow(Rational mul, int index){
        for (Rational rational : extendedMatrix[index]) {
            rational.multiply(mul);
        }
        ops[index]=mul+" R"+(index+1)+"\t";
    }

    public void mulSum(Rational mul, int indexA, int indexB){
        Rational[] rowA = extendedMatrix[indexA];
        Rational[] rowB = extendedMatrix[indexB];

        for (int i = 0; i < m; i++) {
            rowA[i].copyTo(aux2).multiply(mul);
            rowB[i].add(aux2);
        }
        ops[indexB]=mul+" R"+(indexA+1)+"+R"+(indexB+1)+"\t";
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < n; i++) {
            str+= "%20s  ".formatted(ops[i].trim());
            ops[i] = "";
            str+= "%-3s |".formatted("R"+(i+1));
            for (Rational rational : extendedMatrix[i]) {
                str += "%14s |" .formatted(rational);
            }
            str += '\n';
        }
        return str;
    }
}
