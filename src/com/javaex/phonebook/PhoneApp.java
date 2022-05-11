package com.javaex.phonebook;
import java.io.*;
import java.util.*;

public class PhoneApp {
	public static void main(String[] args) throws IOException {
		ArrayList<Person> pList = new ArrayList<Person>();
		
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
			pList.add(new Person(sArr[0], sArr[1], sArr[2]));
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
					for(int i=0; i<pList.size(); i++) {
					    System.out.println((i+1) + ".\t" + pList.get(i).displayInfo());
					}
				}
				else if(menuNum == 2) {
					System.out.println("<2.등록>");
					System.out.print(">이름: ");
					String name = sc.nextLine().replace(",", "");
					if(name.equals("")) {
						name = "(없음)";
					}
					System.out.print(">휴대전화: ");
					String hp = sc.nextLine().replace(",", "");
					if(hp.equals("")) {
						hp = "(없음)";
					}
					System.out.print(">회사전화: ");
					String company = sc.nextLine().replace(",", "");
					if(company.equals("")) {
						company = "(없음)";
					}
					
					if(name.contains("없음") && hp.contains("없음") && company.contains("없음")) {
						continue;
					}
					
					boolean duplicate = false;
					for(int i=0; i<pList.size(); i++) {
						if(pList.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
							System.out.print(">" + name + "은 이미 존재합니다. 덮어쓰시겠습니까 (y/n)? ");
							String confirm = sc.nextLine().toLowerCase();
							if(confirm.equals("yes") || confirm.equals("y")) {
								pList.remove(i);
							} else {
								System.out.println("[ 취소되었습니다. ]");
								duplicate = true;
							}
						}
					}
					if(!duplicate) {
						System.out.println("[ " + name + "(을)를 추가했습니다. ]");
						pList.add(new Person(name, hp, company));
						//실시간 파일 덮어쓰기 (함수는 아래에)
						updateTxt(pList);
					}
				}
				else if(menuNum == 3) {
					System.out.println("<3.삭제>");
					for(int i=0; i<pList.size(); i++) {
					    System.out.println((i+1) + ".\t" + pList.get(i).displayInfo());
					}
					System.out.print(">번호(0은 취소): ");
					String del = sc.nextLine();
					try {
						int delNum = Integer.parseInt(del);
						if(delNum == 0) {
							continue;
						} else {
							System.out.print(">정말로 삭제하겠습니까 (y/n)? ");
							String confirm = sc.nextLine().toLowerCase();
							if(confirm.equals("yes") || confirm.equals("y")) {
								String delName = pList.get(delNum-1).getName();
								pList.remove(delNum-1);
								//실시간 파일 덮어쓰기 (함수는 아래에)
								updateTxt(pList);
								System.out.println("[ " + delName + "의 연락처를 삭제했습니다. ]");
							} else {
								System.out.println("[ 취소되었습니다. ]");
								continue;
							}
						}
					} catch(Exception e) {
						System.out.println("[ 오류: 잘못 입력하셨습니다. ]");
					    continue;
					}
				}
				else if(menuNum == 4) {
					System.out.println("<4.검색>");
					System.out.println();
					System.out.println("1.이름   2.휴대폰번호   3.회사번호");
					System.out.println("-----------------------------------------------");
					String category = sc.nextLine();
					if(category.equals("1")) {
						System.out.print("이름 검색: ");
					} else if(category.equals("2")) {
						System.out.print("휴대폰번호 검색: ");
					} else if(category.equals("3")) {
						System.out.print("회사번호 검색: ");
					} else {
						System.out.println("[ 입력 오류: 다시 시도하세요. ]");
						continue;
					}
					
					String search = sc.nextLine().toLowerCase();
					
					if(category.equals("1")) {
						search = search.toLowerCase();
					} else {
						search = search.replace("-", "").replace(" ", "");
						System.out.println(search);
					}
					
					boolean found = false;
					
					for(int i=0; i<pList.size(); i++) {
						String match;
						if(category.equals("1")) {
							match = pList.get(i).getName().toLowerCase();
						} else if(category.equals("2")) {
							match = pList.get(i).getHp().replace("-", "").replace(" ", "");
						} else {
							match = pList.get(i).getCompany().replace("-", "").replace(" ", "");
						}
						
						if(match.contains(search)) {
							System.out.println((i+1) + ".\t" + pList.get(i).displayInfo());
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
