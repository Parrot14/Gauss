package Gauss;

import Gauss.Frac;

public class Gauss {
    private Frac [][] extendedMatrix;
    private Frac aux = new Frac(0);
    private Frac aux2 = new Frac(0);
    private int n,m;
    private String[] ops;

    public Gauss(Frac[][] extendedMatrix){
        this.extendedMatrix = extendedMatrix;
        n = extendedMatrix.length;
        m = extendedMatrix[0].length;
        
        ops = new String[n];

        for (int i = 0; i < n; i++) {
            ops[i] = "";
        }
    }

    public Gauss(int[][] extendedMatrix){
        this.extendedMatrix = new Frac[extendedMatrix.length][extendedMatrix[0].length];

        n = extendedMatrix.length;
        m = extendedMatrix[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.extendedMatrix[i][j] = new Frac(extendedMatrix[i][j]);
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
            if(!extendedMatrix[i][i].equals(Frac.ONE)){
                int findone = findBelow(i, Frac.ONE);
                if(findone != -1){
                    switchRows(i, findone);
                }else{
                    if(extendedMatrix[i][i].equals(Frac.ZERO)){
                        int findany = findFirstDiferentBelow(i, Frac.ZERO);
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
                if(!extendedMatrix[j][i].equals(Frac.ZERO)){
                    extendedMatrix[j][i].copyTo(aux).aInverse();
                    mulSum(aux, i, j);
                }
            }
        }
        System.out.println("\t\t\t\t---- JORDAN ----");
        for (int i = n-1; i >= 0; i--) {
            System.out.println(this);
            for (int j = i-1; j >= 0; j--) {
                if(!extendedMatrix[j][i].equals(Frac.ZERO)){
                    extendedMatrix[j][i].copyTo(aux).aInverse();
                    mulSum(aux, i, j);
                }
            }
        }
    }

    public boolean isRowZero(int index){
        for (Frac frac : extendedMatrix[index])
            if(!frac.isZero())
                return false;
        return true;
    }

    public int findFirstDiferentBelow(int dindex, Frac not){
        for (int i = dindex+1; i < this.n; i++) {
            if(!extendedMatrix[i][dindex].equals(not)){
                return i;
            }
        }
        return -1;
    }

    public int findBelow(int dindex, Frac num){
        for (int i = dindex+1; i < this.n; i++) {
            if(extendedMatrix[i][dindex].equals(num)){
                return i;
            }
        }
        return -1;
    }

    public void switchRows(int a, int b){
        Frac[] temp = extendedMatrix[a];
        extendedMatrix[a] = extendedMatrix[b];
        extendedMatrix[b] = temp;
        ops[a]="R"+(a+1)+","+(b+1)+"\t";
    }

    public void multiplyRow(Frac mul, int index){
        for (Frac frac : extendedMatrix[index]) {
            frac.multiply(mul);
        }
        ops[index]=mul+" R"+(index+1)+"\t";
    }

    public void mulSum(Frac mul, int indexA, int indexB){
        Frac[] rowA = extendedMatrix[indexA];
        Frac[] rowB = extendedMatrix[indexB];

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
            for (Frac frac : extendedMatrix[i]) {
                str += "%14s |" .formatted(frac);
            }
            str += '\n';
        }
        return str;
    }
}
