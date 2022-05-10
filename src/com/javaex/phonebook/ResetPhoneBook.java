package com.javaex.phonebook;
import java.io.*;

//PhoneDB.txt 새로 생성하는 프로그램
public class ResetPhoneBook {
	public static void main(String[] args) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./PhoneDB.txt"), "UTF-8"));
		
		bw.write("이효리,010-2222-3333,031-2323-4441");
		bw.newLine();
		bw.write("정우성,010-2323-2323,02-5555-5555");
		bw.newLine();
		bw.write("이정재,010-9999-9999,02-8888-8888");
		bw.newLine();
		bw.flush();
		
		System.out.println("\"PhoneDB.txt\" created");
		
		bw.close();
	}
}
