package com.dante.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.dante.util.file.entity.Company;
import com.dante.util.file.entity.Employee;

public class FileReaderAndWriter {

	File file;
	String content;

	@Before
	public void buildFile() {
		file = new File("D:\\Nguyen\\test\\output.txt");
		content = "Test With Unit Test";
		
		if(file == null || !file.exists()) {
			System.out.println("File's not exist!!!");
			System.out.format("Creating file[%s]%n", file.getName());
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println(String.format("File = [%s], Content = [%s]", file.getAbsolutePath(), content));
	}
	
	@Test
	public void writeFileByByteStream() {
		
		/**
		 * Use buffered to write file
		 * If want to write file without buffer, review code at copyFileByByteStream
		 */
		try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));) {
			byte[] b = content.getBytes();
			stream.write(b);
			stream.flush();
		} catch (Exception e) {
			
		}
	}

	/**
	 * Old version will use finally block to close stream
	 */
	@Test
	public void writeFileByCharacterStream() {

		/**
		 * Use buffered to write string to file
		 * If want to write int to file, review code at copyFileByCharacterStream
		 */
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile()); 
			bw = new BufferedWriter(fw);
			bw.write(content);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * New version: Try with resource (no need to close stream at finally block)
	 * The resource will be closed automatically regardless of whether try statement completes normally or abruptly
	 */
	@Test
	public void writeFileByCharacterStreamByNewWay() {

		/**
		 * Use buffered to write string to file
		 * If want to write int to file, review code at copyFileByCharacterStream
		 */
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void writeFileByDataStream() {
		
		double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
		int[] units = { 12, 8, 13, 29, 50 };
		String[] descs = {
		    "Java T-shirt",
		    "Java Mug",
		    "Duke Juggling Dolls",
		    "Java Pin",
		    "Java Key Chain"
		};
		
		try(DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
			for (int i = 0; i < prices.length; i ++) {
				stream.writeDouble(prices[i]);
				stream.writeInt(units[i]);
				stream.writeUTF(descs[i]); // write String
			}
		} catch (Exception e) {
			
		}
	}

	@Test
	public void writeFilebyObjectStream() {
		BigDecimal[] prices = { new BigDecimal(19.99), new BigDecimal(9.99), new BigDecimal(15.99), new BigDecimal(3.99), new BigDecimal(4.99) };
		int[] units = { 12, 8, 13, 29, 50 };
		String[] descs = {
		    "Java T-shirt",
		    "Java Mug",
		    "Duke Juggling Dolls",
		    "Java Pin",
		    "Java Key Chain"
		};
		
		try(ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
			
			stream.writeObject(Calendar.getInstance());
			for (int i = 0; i < prices.length; i ++) {
				stream.writeObject(prices[i]);
				stream.writeInt(units[i]);
				stream.writeUTF(descs[i]);
			}
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void writeFilebyObjectStreamWithEntity() {
		
		Company company = new Company();
		company.setName("League Of Legend");
		company.setIncome(100000);
		company.setEmployee(new Employee(2, "Zed", "Assassin"));
		try(ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
			stream.writeObject(company);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void readFileByByteStream() {
		/**
		 * Programs use byte streams to perform input and output of 8-bit bytes. All byte stream classes are descended from InputStream and OutputStream.
		 * 
		 * Notice that both CopyBytes and CopyCharacters use an int variable to read to and write from. 
		 * However, in CopyCharacters, the int variable holds a character value in its last 16 bits; 
		 * in CopyBytes, the int variable holds a byte value in its last 8 bits.
		 */
		
		try(FileInputStream fin = new FileInputStream("D:/Nguyen/test/input.txt");) {

			int size = fin.available();
			for (int i = 0; i < size; i++) {
				System.out.print((char) fin.read());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	@Test
	public void readFileByCharaterStream() {
		
		/**
		 * All character stream classes are descended from Reader and Writer. 
		 * As with byte streams, there are character stream classes that specialize in file I/O: FileReader and FileWriter
		 */
		try(FileReader in = new FileReader("D:/Nguyen/test/input.txt");
			BufferedReader bufferedReader = new BufferedReader(in);) {

			/**
			 * Print character
			 * Ex: 	E
			 * 		a
			 * 		t
			 */
			int test;
			while ((test = in.read()) != -1) {
				System.out.println("My Character: " + (char) test); // it will print character, not string line.
			}

			/**
			 * Print string line (it is "line-oriented I/O")
			 * Ex: Eat
			 */
            String line;
            while ((line = bufferedReader.readLine()) != null) {
            	 System.out.println("My String Line: " + line);
            }

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void readFileByDataStream() {
		
		double price;
		int unit;
		String desc;
		double total = 0.0;
		
		/**
		 * Make sure that output types and input types are matched in this way
		 * Example: 
		 * 			write: 
		 * 			out.writeInt()
		 * 			out.writeDboule()
		 * 
		 * 			read: must read like write order above
		 * 			in.writeInt()
		 * 			in.writeDboule()
		 */
		try(DataInputStream stream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
			
			/**
			 * Or use while(stream.available() > 0)
			 */
			while (true) {
		        price = stream.readDouble();
		        unit = stream.readInt();
		        desc = stream.readUTF(); // read string
		        System.out.format("You ordered [%d] units of [%s] at [$%.2f]%n", unit, desc, price);
		        total += unit * price;
		    }
		} catch (EOFException e) {
			System.out.println(e);
			System.out.println("WTF??");
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} catch (IOException e1) {
			System.out.println(e1);
		}
	}
	
	@Test
	public void readFileByObjectStream() {

		Calendar date = null;
		BigDecimal price;
		int unit;
		String desc;
		BigDecimal total = new BigDecimal(0);

		/**
		 * Make sure that output types and input types are matched in this way
		 * Example: write: out.writeInt() out.writeDboule()
		 * 
		 * read: must read like write order above in.writeInt() in.writeDboule()
		 */

		try (ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {

			date = (Calendar) stream.readObject();
			System.out.format("On %tA, %<tB %<te, %<tY:%n", date);
			
			/**
			 * Or use while(stream.available() > 0)
			 */
			while (true) {
				try {
					price = (BigDecimal) stream.readObject();
					unit = stream.readInt();
					desc = stream.readUTF();
					System.out.format("You ordered %d units of %s at $%.2f%n", unit,desc, price);
					total = total.add(price.multiply(new BigDecimal(unit)));
				} catch(EOFException e) {
					System.out.println("Break, stop if it's at end line!!");
					break;
				}
			}
			
		} catch (EOFException e) {
			System.out.println(e);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		
		 System.out.format("For a TOTAL of: $%.2f%n", total);

	}
	
	@Test
	public void readFileByObjectStreamWithEntity() {

		Company company = new Company();
		try (ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {

		
			while (true) {
				try {
					company = (Company) stream.readObject();
				} catch(EOFException e) {
					System.out.println("Break, stop if it's at end line!!");
					break;
				}
			}
			
		} catch (EOFException e) {
			System.out.println(e);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		
		Employee employee = company.getEmployee();
		System.out.format("Company[%s] has income[%d]%n", company.getName(), company.getIncome());
		System.out.format("EmplyeeId[%d] - EmployeeName[%s] - EmplyeeRole[%s]%n", employee.getId(), employee.getName(), employee.getRole());
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
