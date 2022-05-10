package com.javaex.phonebook;
import java.util.*;
import java.io.*;

public class Test {
	   public static void main(String[] args) throws IOException {
		      
		      List<Person> pList = new ArrayList<Person>();
		      //파일 읽어옴.
		      Reader rd = new FileReader("./phoneDB.txt");
		      BufferedReader br = new BufferedReader(rd);
		      Scanner sc = new Scanner(System.in);
		      
		      String name = null;
		      String hp = null;
		      String company = null;
		      
		      
		      
		      while(true){
		         String str = br.readLine();
		         if(str == null) {
		            break;
		         }
		         String[] sArray = str.split(",");
		         name = sArray[0];
		         hp = sArray[1];
		         company = sArray[2];
		         pList.add(new Person(name, hp, company));
		      }
		      
		      
		      while(true) {
		      
		      System.out.println("***************************************************");
		      System.out.println("*       전화 번호 관리 프로그램           *");
		      System.out.println("***************************************************");
		      
		      System.out.println("1. 리스트   2. 등록    3. 삭제   4. 검색   5. 종료");
		      System.out.println("---------------------------------------------------");
		      System.out.print(">메뉴번호 :");
		      int num = sc.nextInt();
		         
		         if(num == 5) {
		            System.out.println("*************************************");
		            System.out.println("*           감사합니다.*             ");
		            System.out.println("*************************************");
		            break;
		         }else if(num == 1) {
		            System.out.println("<1.리스트>");
		            for(int i = 0; i < pList.size(); i++){
		            System.out.println(i+1 + ".  " + pList.get(i).getName() + "   " + pList.get(i).getHp() + "  " + pList.get(i).getCompany());
		            } 
		         }else if(num == 2) {
		            Writer wt = new FileWriter("./phoneDB.txt");
		            BufferedWriter bw = new BufferedWriter(wt);
		            System.out.println("<2.등록>");
		            System.out.print("이름 :");
		            name = sc.next();
		            System.out.print("휴대전화 :");
		            hp = sc.next();
		            System.out.print("회사전화 :");
		            company = sc.next();
		            System.out.println("[등록되었습니다.]");
		            pList.add(new Person(name, hp, company));   //입력값추가
		            
		            for( Person plus : pList) {   //pList를 person에 담아 saveStr에 새로 저장한다.
		               String saveStr = plus.getName() + "," + plus.getHp() + "," + plus.getCompany();
		               bw.write(saveStr);
		               bw.newLine();
		            }
		            bw.close();
		            
		         }else if(num == 3) {
		            Writer wt = new FileWriter("./phoneDB.txt");
		            BufferedWriter bw = new BufferedWriter(wt);
		            
		            System.out.println("<3.삭제>");
		            System.out.print(">번호 :");
		            num = sc.nextInt();
		            pList.remove(num-1);//ArrayList는 0부터시작이라 -1해야 입력값과 동일해짐
		            System.out.println("[삭제되었습니다.]");
		            
		            for( Person minus : pList) {
		               String saveStr = minus.getName() + "," + minus.getHp() + "," + minus.getCompany();
		               bw.write(saveStr);
		               bw.newLine();
		            }
		            bw.close();
		         }else if(num == 4) {
		            System.out.println("<4.검색>");
		            System.out.print(">이름 :");
		            String search = sc.next();
		            for(int i = 0; i < pList.size(); i++) {
		            if(pList.get(i).getName().contains(search)) {   //contains메소드는 특정 문자열 검색가능한 기능이 있음.
		               System.out.println(i+1 + ".  " + pList.get(i).getName() + "   " + pList.get(i).getHp() + "  " + pList.get(i).getCompany());
		               }
		            }
		         }else {
		            System.out.println("[다시 입력해주세요.]");
		         }
		         
		      }//while문 끝
		      
		      sc.close();
		      br.close();
		   }

}