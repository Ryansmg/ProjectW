package test;

public class Main {
    public static void main(String[] args) {
        int sum = 0;
        for(int i=60; i<90; i++){
            sum += 1000.0 * Math.sin(Math.toRadians(i));
        }
        System.out.println(sum);
    }
}
