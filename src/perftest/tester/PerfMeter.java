package tester;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.time.StopWatch;

public class PerfMeter {
	public static void run(Object o, int rounds, String method) {
		Method testMethod = null;
		try {
			testMethod = o.getClass().getMethod(method);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StopWatch stopwatch = new StopWatch();
		stopwatch.reset();
		stopwatch.start();
		while (stopwatch.getTime() < 1200) // A Warmup of 1000-1500 mS
		// stabilizes the CPU cache and pipeline.
		{
			try {
				testMethod.invoke(o);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.gc();
		}
		stopwatch.stop();
		long totalmem;
		System.out.println("Round;Runtime ms;Memory KB");
		for (int repeat = 0; repeat < rounds; ++repeat) {
			stopwatch.reset();
			stopwatch.start();
			try {
				testMethod.invoke(o);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stopwatch.stop();
			totalmem = getUsedMemoryKB();
			System.gc();
			System.out.println((1+repeat)+";"+stopwatch.getTime() + ";" + totalmem);
		}
	}
	
	private static long getUsedMemoryKB(){
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
	}
}
