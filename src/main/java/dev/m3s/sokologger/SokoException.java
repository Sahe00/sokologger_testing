package dev.m3s.sokologger;

public class SokoException extends Exception {
	public SokoException(String message) {
		super(message);
	}

	public SokoException() {
		super();
	}

	public SokoException(Throwable throwable) {
		super(throwable);
	}

	public SokoException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
