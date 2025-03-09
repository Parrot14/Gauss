package Gauss;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Gauss.Rational;
import Gauss.Gauss;

public class Main {
    public static void main(String[] args) {
        new Gauss(getData()).compute();

/*         new Gauss(new int[][]{
            {2,  1,  4},
            {1, -1,  2}
        }).compute();

        new Gauss(new int[][]{
            {1,  1,  1,  6},
            {2, -1,  1,  3},
            {3,  0, -1,  0},
        }).compute();

        new Gauss(new int[][]{
            {1, -2,  1,  1,  2},
            {3,  0,  2, -2, -8},
            {2,  4, -1, -1,  1},
            {5,  0,  3, -1,  0},
        }).compute(); */
    }

    public static Rational[][] getData(){

        System.out.println("De cuantas variables es su sistema...");
        int n = getNumber();
        System.out.println("De cuantas ecuaciones es su sistema...");
        int m = getNumber();
        

        Rational[][] nums = new Rational[m][n+1];

        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca los valores...{int|float}[/int]");
        for (int i = 0; i < m; i++) {
            String line = "\t[ ";
            for (int j = 0; j <= n; j++) {
                while(true){
                    System.out.print(line);
                    String input = sc.nextLine().trim();
                    Rational rational = toRational(input);
                    if(rational == null){
                        System.out.println("Numero mal formado, intentelo de nuevo.");
                        continue;
                    }

                    line += input+" ] [ ";
                    nums[i][j] = rational;
                    System.out.print("\u001B[A");
                    break;
                }
            }
            System.out.print(line.substring(0, line.length()-2)+"\n");
        }

        return nums;
    }

    private static int getNumber(){
        Scanner sc = new Scanner(System.in);
        int n;
        while (true) {
            String num = sc.nextLine();
            if(num.matches("^\\d+$")){
                n = Integer.parseInt(num);
                break;
            }
            System.out.println("Numero mal formado, intentelo de nuevo.");
        }
        return n;
    }

    private static Pattern pat = Pattern.compile("(^(?:\\+|-)?\\d+)(?:\\.(\\d+))?(?:\\/(\\d+))?$");

    public static Rational toRational(String str){
        int numerator;
        int denominator = 1;
        Matcher match = pat.matcher(str);
        if(match.matches()){
            String numerator_str = match.group(1), decimal_str = match.group(2), denominator_str = match.group(3);
            
            numerator = Integer.parseInt(numerator_str+(decimal_str!=null?decimal_str:""));

            if(denominator_str!=null){
                denominator = Integer.parseInt(denominator_str);
                if(denominator == 0){
                    return null;
                }
            }
            if(decimal_str != null)
                for (int i = 0; i < decimal_str.length(); i++)
                    denominator *= 10;
            return new Rational(numerator, denominator);
        }
        return null;
    }
}
