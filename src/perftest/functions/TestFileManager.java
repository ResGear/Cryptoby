package functions;

import java.io.IOException;
import org.junit.*;

import filemgr.CryptobyFileManager;
import tester.PerfMeter;

public class TestFileManager {

	int cycles = 10;
	byte[] file1;
	byte[] file10;
	byte[] file100;
	int mb1;
	int mb10;
	int mb100;
	String filePath;

	@Before
	public void setUp()
	{ 
		filePath = "target/perfTestFile";
		mb1 = 1024 * 1024;
		mb10 = mb1 * 10;
		mb100 = mb10 * 10;
	}

	public void testWrite1ToFile() {
		for(int i = 0; i < cycles; i++)
		{
			try {
				CryptobyFileManager.putBytesToFile (filePath, file1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void testWrite10ToFile() {
		for(int i = 0; i < cycles; i++)
		{
			try {
				CryptobyFileManager.putBytesToFile (filePath, file10);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void testWrite100ToFile() {
		for(int i = 0; i < cycles; i++)
		{
			try {
				CryptobyFileManager.putBytesToFile (filePath, file100);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void testRead1FromFile(){
		try {
			file1 = CryptobyFileManager.getBytesFromFile(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testRead10FromFile(){
		try {
			file10 = CryptobyFileManager.getBytesFromFile(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testRead100FromFile(){
		try {
			file100 = CryptobyFileManager.getBytesFromFile(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void testRun(){
		int rounds = 20;

		System.out.println("Test Performance Cryptoby FileManager");
		System.out.println("Rounds: "+rounds);
		System.out.println("Cycle per Round: "+cycles);
		
		System.out.println("\nWrite 1MB to File");
		file1 = new byte[mb1];
		PerfMeter.run(this, rounds, "testWrite1ToFile");
		System.out.println("\nRead 1MB from File");
		PerfMeter.run(this, rounds, "testRead1FromFile");
		file1 = null;
		
		System.out.println("\nWrite 10MB to File");
		file10 = new byte[mb10];
		PerfMeter.run(this, rounds, "testWrite10ToFile");
		System.out.println("\nRead 10MB from File");
		PerfMeter.run(this, rounds, "testRead10FromFile");
		file100 = null;
		
		file100 = new byte[mb100];
		System.out.println("\nWrite 100MB to File");
		PerfMeter.run(this, rounds, "testWrite100ToFile");
		System.out.println("\nRead 100MB from File");
		PerfMeter.run(this, rounds, "testRead100FromFile");
		file100 = null;
		
		try {
			CryptobyFileManager.putBytesToFile (filePath, new byte[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
