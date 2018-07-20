package com.dante.util.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

public class FileUtil extends FileReaderAndWriter {

	@Test
	public void getCurrentProjectPath() {
		System.out.println(System.getProperty("user.dir"));
	}

	@Test
	public void getParentFile() {
		File file = new File("D:/Nguyen/test/user1", "user2/test3.txt");
		File parentFile = file.getParentFile();
		System.out.println(parentFile.getName());
	}

	@Test
	public void createFolder() {
		File file = new File("D:/Nguyen/test/user1", "user2/test3");
		if (!file.exists()) {
			System.out.println("Creating folder ...");
			file.mkdirs();
		}

		System.out.println(file.getAbsolutePath());
	}

	@Test
	public void createFile() throws IOException {
		File file = new File("D:/Nguyen/test/user1", "user2/test3.txt");
		if (!file.exists()) {
			System.out.println("Creating File ...");
			file.createNewFile();
		}

		System.out.println(file.getAbsolutePath());
	}

	@Test
	public void CopyFileByByteStream() {

		File input = new File("D:/Nguyen/test/input.txt");
		File output = new File("D:/Nguyen/test/output.txt");
		
		if(input == null || input.length() < 1 || !input.exists()) {
			System.out.println("File's not exist!!!");
			return;
		}

	        try( FileInputStream in = new FileInputStream(input);
	        	FileOutputStream out = new FileOutputStream(output);) {
	            int c;

	            while ((c = in.read()) != -1) {
	                out.write(c);
	            }

	        } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 

	}

	@Test
	public void copyFileByCharacterStream(){
		
		File input = new File("D:/Nguyen/test/input.txt");
		File output = new File("D:/Nguyen/test/output.txt");

		try(FileReader in = new FileReader(input);
			FileWriter out = new FileWriter(output)) {

			// copy file A to file B with binary digit (it will convert string to digit)
			int line;
			while ((line = in.read()) != -1) {
				out.write(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void checkExistFileInFolder() {
		
		/**
		 * Check file list in a folder
		 */
		File folder = new File("D:/Nguyen/test");
		String[] errorFiles = folder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("_error" + ".txt");
			}
		});
		
		System.out.println("size: " + errorFiles.length);
		
//		return errorFiles.length > 0;
	}

	@Test
	public void getFileAndFolderName() {
		File folder = new File("D:/Nguyen/test");

		/**
		 * Get All File and Folder
		 */
		String[] useList = folder.list();
		for (String string : useList) {
			System.out.println("All File and Folder: " + string);
		}
		
		
		/**
		 * Get Specific File and Folder by Name
		 * Ex: 	Specific File and Folder: input.txt
		 * 		Specific File and Folder: output.txt
		 */
		String[] useSpecificList = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File pathname, String name) {
				return name.endsWith(".txt") && !name.endsWith("_hehe.txt");
			}
		});

		for (String string : useSpecificList) {
			System.out.println("Specific File and Folder: " + string);
		}
	}
	
	@Test
	public void getFileOrFolder() {
		File folder = new File("D:/Nguyen/test");
		File[] useListFile = folder.listFiles();

		/**
		 * Get All File or Folder
		 */
		for (int i = 0; i < useListFile.length; i++) {
			if (useListFile[i].isFile()) {
				System.out.format("File[%d] - %s%n", i,  useListFile[i].getName());
			} else if (useListFile[i].isDirectory()) {
				System.out.format("Folder[%d] - %s%n", i, useListFile[i].getName());
			}
		}

		/**
		 * Get specific File or Folder by path
		 * Ex: 	Specific File: D:\Nguyen\test\input.txt
		 * 		Specific File: D:\Nguyen\test\output.txt
		 */
		File[] useSpecificListFile = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".txt") && !pathname.getName().endsWith("_hehe.txt");
			}
		});

		for (File file : useSpecificListFile) {
			System.out.println("Specific File: " + file);
		}
	}

	@Test
	public void getSpecificFile() {
		File folder = new File("D:/Nguyen/test");
		
		/**
		 * Get Files by file.list()
		 */
		String[] useList = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File pathname, String name) {
				return name.endsWith(".txt") && !name.endsWith("_hehe.txt");
			}
		});

		for (String string : useList) {
			System.out.println(string);
		}
		System.out.println("");

		/**
		 * Get Files by file.listFiles()
		 */
		File[] useListFile = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".txt") && !pathname.getName().endsWith("_hehe.txt");
			}
		});

		for (File file : useListFile) {
			System.out.println("File: " + file);
		}

	}

	@Test
	public void zipFile() throws IOException {

		List<File> files = new ArrayList<File>(){
			{
				add(new File("D:/Nguyen/test/input.txt"));
				add(new File("D:/Nguyen/test/output.txt"));
			}
		};
		File zipfile = new File("D:/Nguyen/test/TestZip");

		ZipOutputStream zout = null;
		FileInputStream fis = null;
		OutputStream out = null;

		try {
			out = new FileOutputStream(zipfile);
			zout = new ZipOutputStream(out);
			for (File file : files) {
				byte[] buffer = new byte[1024];
				fis = new FileInputStream(file);
				zout.putNextEntry(new ZipEntry(file.getName()));
				int length;
				while ((length = fis.read(buffer, 0, 1024)) > 0) {
					zout.write(buffer, 0, length);
				}
				zout.closeEntry();
				fis.close();
			}
		} finally {
			fis.close();
			zout.close();
			out.close();
		}

	}
	
	/**
	 * ======================= Byte Stream(Unbuffered streams): 
	 * 
	 * Programs use byte streams to perform input and output of 8-bit bytes
	 * Byte streams should only be used for the most primitive I/O.
	 * If a file contains character data, the best approach is to use character streams
	 * 
	 * ======================= Character Stream(Unbuffered streams):
	 * 
	 * In copyFileByCharacterStream above, the int variable holds a character value in its last 16 bits; 
	 * In CopyFileByByteStream above, the int variable holds a byte value in its last 8 bits.
	 * Use BufferedReader if want to read file by line because of "line-oriented I/O"
	 * If a file contains character data, the best approach is to use character streams
	 * 
	 * ======================= Buffered Stream:
	 * 
	 * Most of the examples we've seen so far use unbuffered I/O. 
	 * This means each read or write request is handled directly by the underlying OS. 
	 * This can make a program much less efficient, since each such request often triggers disk access, network activity, 
	 * or some other operation that is relatively expensive.
	 * Use Buffered Stream to reduce it.
	 * 
	 * There are four buffered stream classes used to wrap unbuffered streams: 
	 * BufferedInputStream and BufferedOutputStream create buffered byte streams, 
	 * while BufferedReader and BufferedWriter create buffered character streams.
	 * 
	 * ====================== FLUSH:
	 * Why use flush on stream?
	 * when we give any command ,the streams of that command is stored in the memory location called buffer(a temporary memory location) in our computer.
	 * when all the temporary memory location are full then we use flush() which flushes all the streams of data and executes them completely 
	 * and gives a new space to new streams in buffer temporary location
	 * 
	 * NOTE:

		NIO allows you to manage multiple channels using only a single (or fewer) threads, 
		but the cost is that parsing the data might be somewhat more complicated than when reading data from a blocking stream using standard IO.
		
		If you need to manage thousands of open connections simultaneously, which each only send a little data, for instance a chat server, 
		implementing the server in NIO is probably an advantage. Similarly, if you need to keep a lot of open connections to other computers, 
		e.g. in a P2P network, using a single thread to manage all of your outbound connections might be an advantage.
		
		If you have fewer connections with very high bandwidth, sending a lot of data at a time, standard IO server implementation should be your choice.

	 * ======================= Data Stream:
	 * Support read and write String and primitive type (boolean, char, byte, short, int, long, float, and double)
	 * Then DataStreams opens an output stream. Since a DataOutputStream can only be created as a wrapper for an existing byte stream object not character stream
	 * stream.readUTF(); ==> read String
	 * 
	 * ======================= Object Stream:
	 * All the primitive data I/O methods covered in Data Streams are also implemented in object streams
	 * Just as data streams support I/O of primitive data types, object streams support I/O of objects. 
	 * Most, but not all, standard classes support serialization of their objects. 
	 * Those that do implement the marker interface Serializable.
	 * 
	 * IMPORTANT: Class must implementeds Serializable to use Object Stream.
	 */
}
