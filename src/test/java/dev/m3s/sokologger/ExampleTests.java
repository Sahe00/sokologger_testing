package dev.m3s.sokologger;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class ExampleTests {

	@Test
	void exampleTest() {
		System.out.println("Hello world");
		assertEquals(getClass(), getClass());
	}

	/**
	 * Example test using reflection to access methods that are not public in a
	 * class. Reflection can also be used to access other non-public members in a
	 * class. More info about reflection can be found in the documentation
	 *
	 * @see https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html
	 *
	 * @throws NoSuchMethodException     If <code>getDeclaredMethod</code> couldn't
	 *                                   find a
	 *                                   method with the specified name or
	 *                                   parameters
	 * @throws SecurityException         If a SecurityManager if present and
	 *                                   prevented an action
	 * @throws IllegalAccessException    If the method is enforcing java access
	 *                                   control and is inaccessible
	 * @throws InvocationTargetException If the underlying method threw an exception
	 */
	@Test
	void reflectionExample()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		// We get the sum method from the Player class. The method has two parameters:
		// Integer oldValue and Integer newValue
		Method sum = Player.class.getDeclaredMethod("sum", Integer.class, Integer.class);
		// Since the method is private, we must set it to be accessible to use it.
		sum.setAccessible(true);
		// We create a new instance of the Player class to use in the test
		Player player = new Player(1);
		// We invoke the sum method. The first parameter is the player class we created.
		// The second and third parameters are the values we want to add together.
		// This is the same as doing `player.sum(1, 2)` if the method was public.
		assertEquals(3, sum.invoke(player, 1, 2));
	}
}
