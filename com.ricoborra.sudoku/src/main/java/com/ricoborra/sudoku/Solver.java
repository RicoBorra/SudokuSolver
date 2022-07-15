package com.ricoborra.sudoku;

public class Solver {
    private final int[][] base;
    private boolean[][] rows;
    private boolean[][] cols;
    private boolean[][] quads;
    private boolean isValid;
    
    public Solver(int[][] base){
        this.base = base;
        this.rows = new boolean[9][10];
        this.cols = new boolean[9][10];
        this.quads = new boolean[9][10];
        this.isValid = true;
        for (int i=0; i<9; i++){
            for (int j=0; j<=9; j++){
                rows[i][j] = cols[i][j] = quads[i][j] = false;
            }
        }
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                int cifra = base[i][j];
                if (cifra < 0 || cifra > 9){
                    isValid = false;
                    return;
                }
                if (cifra == 0) continue;
                if (rows[i][cifra] == true || cols[j][cifra] == true || quads[3*(i/3) + (j/3)][cifra] == true){
                    isValid = false;
                }
                rows[i][cifra] = cols[j][cifra] = quads[3*(i/3) + (j/3)][cifra] = true;
            }
        }
    }
    
    public boolean isValid(){
        return isValid;
    }
    
    public int[][] solve(){
        int[][] sol = new int[9][9];
            for (int i=0; i<9; i++){
                for (int j=0; j<9; j++){
                    sol[i][j] = base[i][j];
                }
            }
        if (!isValid) return sol;
        solveR(sol, 0, 0);
        return sol;
    }
    
    private boolean solveR(int[][] sol, int i, int j){
        if (j >= 9){
            return solveR(sol, i+1, 0);
        }
        if (i >= 9) return true;
        if (base[i][j] != 0) return solveR(sol, i, j+1);
        for (int t = 1; t <= 9; t++){
            if (rows[i][t] == true || cols[j][t] == true || quads[3*(i/3) + (j/3)][t] == true) continue;
            sol[i][j] = t;
            rows[i][t] = cols[j][t] = quads[3*(i/3) + (j/3)][t] = true;
            if (solveR(sol, i, j+1)) return true;
            rows[i][t] = cols[j][t] = quads[3*(i/3) + (j/3)][t] = false;
        }
        return false;
    }
}