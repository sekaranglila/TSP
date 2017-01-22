/* NIM/Nama : 13514069/Sekar Anglila Hapsari
 Nama file : TSP.java
 Tanggal :  3 April 2016*/

package tsp;

import java.util.Scanner;
import java.io.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class TSP{
    //Atribut
    int [][] T = new int[50][50];
    int [] path;
    int [][] MP;
    int [] s;
    int i, j;
    int lowbound = 0;
    int assign = 0;
    int NB = 0; int NK = 0;
    int sm, sm2;
    Scanner sc = new Scanner(new File("C:\\Users\\ASUS\\Documents\\Stima\\Tucil 3\\TSPInput.txt")); 

    //Metode
    //Konstruktor
    TSP() throws IOException {
        for (i = 0; i < 50; i++){
            for (j = 0; j < 50; j++){
                T[i][j] = 0;
            }
        }
    }

    //Pembacaan File Eksternal dan Penulisan Matriks (ke layar)
    public void bacaFile() { 
        //Algoritma
        while(sc.hasNextLine()){ 
            String str = sc.nextLine();
            String str2[] = str.split(" ");
            NK = str2.length;
            for(j = 0; j < NK; j++){
                T[NB][j] = Integer.parseInt(str2[j]);
            }
            NB++;
        }
    }

    public void tampilMatriks(){
        //Algoritma
        for(i = 0; i < NB; i++){
            for(j = 0; j < NK; j++){
                System.out.print(T[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    //Mencari bobot terkecil dan menjumlahkannya
    public int findMax(int b){
        //Kamus Lokal 
        int max = T[b][0];

        //Algoritma
        for (j = 1; j < NK; j++){
            if (T[b][j] > max){
                max = T[b][j];
            }
        }
        return max;
    } 
   
    public int findSmallest(int b){
        //Kamus Lokal
        int k = 0;
        
        //Inisialisasi
        sm = findMax(b); sm2 = findMax(b);
        //Algoritma
        for (j = 0; j < NK; j++){
            if ((T[b][j] < sm) && (T[b][j] != -1)){
                sm = T[b][j]; k = j;
            }
        }
        for (j = 0; j < NK; j++){
            if ((T[b][j] < sm2) && (j != k) && (T[b][j] != -1)){
                sm2 = T[b][j];
            }
        }
        return (sm + sm2);
    }
    
    public int [] small(int b){
        //Kamus Lokal
        int k = 0;
        int [] s = new int [2];
        
        //Inisialisasi
        s[0] = findMax(b); s[1] = findMax(b);
        //Algoritma
        for (j = 0; j < NK; j++){
            if ((T[b][j] < sm) && (T[b][j] != -1)){
                s[0] = T[b][j]; k = j;
            }
        }
        for (j = 0; j < NK; j++){
            if ((T[b][j] < sm2) && (j != k) && (T[b][j] != -1)){
                s[1] = T[b][j];
            }
        }
        return s;
    }
    
    //Menghitung bound
    public int bound(){
        //Kamus Lokal
        int sum = 0;
        
        //Algoritma
        for (i = 0; i < NB; i++){
            sum = sum + findSmallest(i);
        }
        return sum;
    }
    
    //Membuat matriks penanda
    public void MPenanda(){
        //Kamus Lokal
        MP = new int [NB][NK];
        
        //Algoritma
        for (i = 0; i < NB; i++){
            for (j = 0; j < NB; j++){
                MP[i][j] = 0;  
            }
        }
    }
    
    public void UpdateMPenanda(int b, int k){
        //Algoritma
        MP[b][k] = 1; MP[k][b] = 1;
    }
    
    public int sumCost(int[][] temp){
        //Kamus Lokal
        int sum = 0;
        int nr = 0;
        int sum1, sum2;
        int [] req = new int [50];
        
        //Algoritma
        for (i = 0; i < NB; i++){
            for (j = 0; j < NK; j++){
                if (temp[i][j] == 1){
                    req[nr] = T[i][j];
                    nr++;
                }
            }
            if (nr == 0){
                sum1 = findSmallest(i); sum2 = 0;
            } else if (nr == 1){
                sum1 = req[0]; sum2 = sm2;
            } else {
                sum1 = req[0]; sum2 = req[1];
            }
            if (sum1 == -1){
                sum1 = 0;
            }
            if (sum2 == -1){
                sum2 = 0;
            }
            sum = sum1 + sum2 + sum;
        }
        return sum;
    }
    
    public boolean nodeExists(int n){
        //Kamus Lokal
        boolean exist = false;
        int a = 0;
        
        //Algoritma
        while ((a < NB) && !(exist)){
            if (n == path[a]){
                exist = true;
            } else {
               a++; 
            }
        }
        return exist;
    }
    
    public void findSolution(){
       //Kamus Lokal
       int count = 0; int v = 0; int p = 0; int x = 0;
       int c = 0; int id = 0;
       int bound, pos, sum, mincost;
       int [] cost = new int [NB];
       int [] visited = new int [NB];
       boolean done = false;
       int [][] temp = new int [NB][NK];
       path = new int [NB];
       int [] ID = new int [50];
       
       //Algoritma
       for (i = 0; i < NB; i++){
           path[i] = 0;
       }
       MPenanda(); bound = bound();
       pos = 0; path[p] = 0; p++;
       while (!(done)){
           for (int ix = 0; ix < NB; ix++){
               cost[ix] = 0;
               for(int jy = 0; jy < NK; jy++){
                   temp[ix][jy] = MP[ix][jy];
               }
           } c = 0;
           for (int jx = 1; jx < NK; jx++){
               if (nodeExists(jx) == false){
                    temp[pos][jx] = 1; temp[jx][pos] = 1;
                    cost[jx] = sumCost(temp); c++;
                    temp[pos][jx] = 0; temp[jx][pos] = 0;
               } else {
                   c = c + 0;
               }
           }
           if (v != 0){
               int y = 0;
               while (y < v){
                   cost[visited[y]] = 999999;
                   y++;
               }   
           } 
           mincost = cost[1]; id = 1;
           for(int idx = 1; idx <= c; idx++){
                if (cost[idx] < mincost){
                    mincost = cost[idx]; id = idx;
                }
            }
           if (mincost <= bound){
               path[p] = id; p++; ID[x] = id; x++;
               UpdateMPenanda(pos, id); 
               pos = id; count++; v = 0;
               for (int iv = 0; iv < NB; iv++){
                   visited[iv] = 0;
               } lowbound = cost[id];
               assign++;
           } else {
               if ((v + 1) == (NB - p)){
                   v = 0;
                   for (i = 0; i < NB; i++){
                        visited[i] = 0;
                   } 
                   p--; pos = path[p]; path[p] = 0;
                   x--; id = ID[x]; ID[x] = 0;
                   MP[pos][id] = 0; MP[id][pos] = 0;
                   visited[v] = id; v++; count--;
               } else {
                   visited[v] = id; v++;
               }
           }
           if (count == (NB - 1)){
               done = true;
           }
       }
       MP[pos][0] = 1; MP[0][pos] = 1; 
    }
    
    public void printSolution(){
        //Kamus Lokal
	int x;
        
        //Algoritma
        for (i = 0; i < NB; i++){
            x = path[i] + 1;
            System.out.print(x);
            System.out.print(" - ");
        }
        System.out.println("1");
    }
    
    //Pembuatan graf
    public static boolean sisiSolusi(int[][] MPenanda, int b, int k){
        if (MPenanda[b][k] == 1){
            return true;
        } else {
            return false;
        }
    }
    
    public void visualisasiGraf(int mat[][], int ukuran){
        Graph graph = new SingleGraph("Graf 1");

        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.display();

        // Inisialisasi seluruh simpul
        for(int i = 0; i < ukuran; i++){
          Node temp = graph.addNode(i + "");
          temp.addAttribute("ui.label", (i + 1) + "");
        }

        // Inisialisasi seluruh sisi
        // Jika sisi tersebut adalah jalur solusi beri warna berbeda
        for(int i = 0; i < ukuran; i++){
          for(int j = i + 1; j < ukuran; j++){
              Edge edge = graph.addEdge(i + "" + j, i + "", j + "");
              edge.addAttribute("ui.label", mat[i][j] + "");
              if(sisiSolusi(MP, i, j))
                edge.setAttribute("ui.class", "warnaSisi");
            }
        }
    }

      protected static String styleSheet =
          "node {" +
          " size: 10px; fill-color: blue; text-size: 20px; text-color: blue;" +
          "}" +
          "edge {" +
          " size: 2px; fill-color: #CCC; stroke-width: 1px; stroke-mode: plain; text-size: 20px;" +
          "}" +
          "edge.warnaSisi {" +
          " fill-color: #222; stroke-width: 1px; stroke-mode: plain;" +
          "}";
    
    public static void main(String[] args) throws IOException { 
        //Kamus
        TSP t = new TSP();
        double lb;
        long start, finish;
        long time = 0;
        double finaltime;

        //Algoritma
        //Pembacaan File dan pembuatan matriks boolean
        t.bacaFile();

        //Menampilkan matriks sudoku awal
        System.out.println("Matriks Ketetanggaan :");
        t.tampilMatriks(); 
        lb = t.bound() / 2.0;
        System.out.println("Bound = " + lb);
 
        //Menghitung waktu start
        start = System.nanoTime();

        //Mengisi sudoku
        System.out.println("Graf Awal :");
        //t.MPenanda();
        //t.visualisasiGraf(t.T, t.NB);
        System.out.println("Solusi Travelling Salesperson Problem :");
        t.findSolution();
        t.printSolution();
        System.out.println();
        System.out.println("Rute :" + t.lowbound);
        t.visualisasiGraf(t.T, t.NB);
        System.out.println();

        //Menghitung waktu selesai
        finish = System.nanoTime();
        time = (finish - start);
        finaltime = (double) time / 1000000;

        //Jumlah assignment dan waktu eksekusi
        System.out.println("Jumlah assignment = " + t.assign + " kali");
        System.out.println("Waktu eksekusi = " + finaltime + " ms");
    }
}