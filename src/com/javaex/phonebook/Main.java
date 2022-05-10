package com.javaex.phonebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
   
      public static void main(String[] args) throws IOException {
         
         //해밍 디스턴스를 구해라.ATGC
      
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         
         String str = br.readLine();   //첫 줄 숫자 두개를 받는다.
         StringTokenizer st = new StringTokenizer(str);   // 받은 숫자를 분리한다.
         int N = Integer.parseInt(st.nextToken());   // 첫번째 숫자는 N : DNA 개수
         int M = Integer.parseInt(st.nextToken());   // 두번째 숫자는 M : DNA 길이
         
         String[][] s = new String[N][M];   //이차원 배열을 사용해 보겠다.
         
         for(int i=0; i<N; i++) {   
            String DNA[] = br.readLine().split("");   //정렬된 N개의 DNA를 글자마다 분해한다.
            for(int j=0; j<M; j++) {
               s[i][j] = DNA[j];   //줄당, 분해한 글자마다 배치하여 이차원 배열로 만든다.
            }
         }
         
         int[] ACGT = new int[4];   //ㅋ
         int hd = 0;
         for(int j=0; j<M; j++) {   //column 별로 확인한다.
            ACGT[0]=0;//초기화
            ACGT[1]=0;
            ACGT[2]=0;
            ACGT[3]=0;
            for(int i=0; i<N; i++) {
               if(s[i][j].equals("A")) {
                     ACGT[0]++;
               } else if(s[i][j].equals("C")) {
                   ACGT[1]++;
               } else if(s[i][j].equals("G")) {
                   ACGT[2]++;
               }else if(s[i][j].equals("T")) {
                   ACGT[3]++;
               }
            }//한 열이 끝나면
            int Max = 0;
            for(int nc=0; nc<4; nc++) { //nc: 뉴클레오티드 nucleotid
               if(ACGT[Max]<ACGT[nc]) {   //더 큰 경우만 치환 할거고 ACGT 순서이므로 알파벳 순서OK
                  Max = nc;   // 어떤게 크냐고...(ACGT구분)
               }
            }
            switch(Max) {   
               case 0:
                  System.out.print("A");
                  break;
               case 1:
                  System.out.print("C");
                  break;
               case 2:
                  System.out.print("G");
                  break;
               case 3:   
                  System.out.print("T");
                  break;
            }
            hd = hd+N-ACGT[Max];   //열마다 더하기 / 행개수 - 많은거 개수 : 다른거 개수 
         }
         System.out.println("\n"+hd);
         br.close();
   }
}
