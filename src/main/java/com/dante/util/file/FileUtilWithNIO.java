package com.dante.util.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FileUtilWithNIO {

	@Test()
	public void readFileByByte() throws IOException {

		String path = "D:/Test/output.txt";
		Charset encoding = StandardCharsets.UTF_8;

		byte[] encoded = Files.readAllBytes(Paths.get(path));
		String content = new String(encoded, encoding);
		System.out.println(content);

	}
	
	@Test
	public void readFileByLine() {

		String path = "D:/Test/output.txt";
		Charset encoding = StandardCharsets.UTF_8;
		// java7: Get content of file by line (just aSCII)
		List<String> lines = new ArrayList<String>();

		/**
		 * StringBuilder is not thread safe
		 */
		StringBuilder builder = new StringBuilder();
		try {
			lines = Files.readAllLines(Paths.get(path), encoding);
			for (String string : lines) {
				builder.append(string + "\n");
			}
			System.out.println(builder.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
