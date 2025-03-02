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
        System.out.println("\t\t\t\t---- GAUSS ----");
        for (int i = 0; i < n; i++) {
            System.out.println(this);
            if(!extendedMatrix[i][i].equals(Rational.ONE)){
                int findone = findBelow(i, Rational.ONE);
                if(findone != -1){
                    switchRows(i, findone);
                }else{
                    if(extendedMatrix[i][i].equals(Rational.ZERO)){
                        int findany = findFirstDiferentBelow(i, Rational.ZERO);
                        if(findany != -1){
                            switchRows(n, findany);
                        }else{
                            if(isRowZero(n-1))
                                System.out.println("\t\t\t----- SOLUCIÓN MULTIPLE -----");
                            else
                                System.out.println("\t\t\t----- SIN SOLUCIÓN -----");
                            return;
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
        System.out.println("\t\t\t\t---- JORDAN ----");
        for (int i = n-1; i >= 0; i--) {
            System.out.println(this);
            for (int j = i-1; j >= 0; j--) {
                if(!extendedMatrix[j][i].equals(Rational.ZERO)){
                    extendedMatrix[j][i].copyTo(aux).aInverse();
                    mulSum(aux, i, j);
                }
            }
        }
        System.out.print("\n\t\t\t");
        for (int i = 0; i < n; i++)
            System.out.print("    "+(char)('a'+i)+"   = "+"%-14s".formatted(extendedMatrix[i][m-1]));
        System.out.print('\n');
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
