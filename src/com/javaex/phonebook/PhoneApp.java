package com.javaex.phonebook;
import java.io.*;
import java.util.*;

public class PhoneApp {
	public static void main(String[] args) throws IOException {
		ArrayList<Person> pArray = new ArrayList<Person>();
		
		InputStream in = new FileInputStream("./PhoneDB.txt");
		InputStreamReader isr = new InputStreamReader(in, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			String s = br.readLine();
			
			if(s == null) {
				break;
			}
			
			String[] sArr = s.split(",");
			pArray.add(new Person(sArr[0], sArr[1], sArr[2]));
		}
		
		System.out.println("**********************************************");
		System.out.println("*           전화번호 관리 프로그램           *");
		System.out.println("**********************************************");
		
		while(true) {
			System.out.println();
			System.out.println("1.리스트   2.등록   3.삭제   4.검색   5.종료");
			System.out.println("-----------------------------------------------");
			System.out.print(">메뉴번호: ");
			
			String menu = sc.nextLine();
			try {
				int menuNum = Integer.parseInt(menu);
				
				if(menuNum == 5) {
					break;
				} else if(menuNum == 1) {
					System.out.println("<1.리스트>");
					for(int i=0; i<pArray.size(); i++) {
					    System.out.println((i+1) + ".\t" + pArray.get(i).displayInfo());
					}
				}
				else if(menuNum == 2) {
					System.out.println("<2.등록>");
					System.out.print(">이름: ");
					String name = sc.nextLine();
					System.out.print(">휴대전화: ");
					String hp = sc.nextLine();
					System.out.print(">회사전화: ");
					String company = sc.nextLine();
					
					pArray.add(new Person(name, hp, company));
					//실시간 파일 덮어쓰기 (함수는 아래에)
					updateTxt(pArray);
				}
				else if(menuNum == 3) {
					System.out.println("<3.삭제>");
					System.out.print(">번호(0은 취소): ");
					String del = sc.nextLine();
					try {
						int delNum = Integer.parseInt(del) - 1;
						if(delNum == 0) {
							continue;
						} else {
							String delName = pArray.get(delNum).getName();
							pArray.remove(delNum);
							//실시간 파일 덮어쓰기 (함수는 아래에)
							updateTxt(pArray);
							System.out.println(delName + "의 연락처를 삭제했습니다.");
						}
					} catch(Exception e) {
						System.out.println("[ 오류: 잘못 입력하셨습니다. ]");
					    continue;
					}
				}
				else if(menuNum == 4) {
					System.out.println("<4.검색>");
					System.out.print("이름 검색: ");
					String search = sc.nextLine();
					boolean found = false;
					
					for(int i=0; i<pArray.size(); i++) {
						String name = pArray.get(i).getName();
						
						if(name.contains(search)) {
							System.out.println((i+1) + ".\t" + pArray.get(i).displayInfo());
							found = true;
							break;
						}
					}
					
					//검색결과가 없으면 출력
					if(!found) {
						System.out.println("검색 결과가 없습니다.");
					}
				} else {
					System.out.println("[ 다시 입력해주세요. ]");
				}
			} catch(Exception e) {
				System.out.println("[ 입력 오류: 다시 시도하세요. ]");
				continue;
			}
		}
		
		System.out.println("**********************************************");
		System.out.println("*                 감사합니다                 *");
		System.out.println("**********************************************");
		
		sc.close();
		br.close();
	}
	
	//txt파일 덮어쓰는 함수 (실시간)
	public static void updateTxt(ArrayList<Person> pArray) throws IOException {
		OutputStream out = new FileOutputStream("./PhoneDB.txt");
		OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		
		for(int i=0; i<pArray.size(); i++) {
			bw.write(pArray.get(i).toTxt());
		    bw.newLine();
		}
		
		bw.flush();
		bw.close();
	}
}
