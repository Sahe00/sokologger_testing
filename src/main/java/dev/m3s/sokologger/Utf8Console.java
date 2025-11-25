package dev.m3s.sokologger;

import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class Utf8Console {
	/**
	 * Get the native windows apis for changing console code page
	 */
	interface Kernel32 extends Library {
		Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

		/**
		 * Set the output code page for the console
		 *
		 * @param wCodePageID Identifier of the code page to be set
		 * @return 0 on fail
		 */
		boolean SetConsoleOutputCP(int wCodePageID);

		/**
		 * Set the input code page for the console
		 *
		 * @param wCodePageID Identifier of the code page to be set
		 * @return 0 on fail
		 */
		boolean SetConsoleCP(int wCodePageID);
	}

	/**
	 * Change console on Windows to use UTF-8 encoding instead of the default
	 * Windows-1252 encoding. Will also reset the stdout and stderr to use UTF-8.
	 */
	public static void enableUtf8ConsoleOnWindows() {
		if (!System.getProperty("os.name").toLowerCase().contains("win"))
			return;

		if (System.getProperty("skip.utf8console") != null)
			return;

		// code page 65001 = UTF-8
		Kernel32.INSTANCE.SetConsoleOutputCP(65001);
		Kernel32.INSTANCE.SetConsoleCP(65001);

		System.setOut(new PrintStream(new BufferedOutputStream(
				new FileOutputStream(FileDescriptor.out)), true, StandardCharsets.UTF_8));
		System.setErr(new PrintStream(new BufferedOutputStream(
				new FileOutputStream(FileDescriptor.err)), true, StandardCharsets.UTF_8));
	}
}
