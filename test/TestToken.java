package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {

	@Test
	void testConstructorWithAllFields() {
		Token token = new Token(TokenType.ID, 5, "foo");

		assertEquals(TokenType.ID, token.getTipo());
		assertEquals(5, token.getRiga());
		assertEquals("foo", token.getVal());
	}

	@Test
	void testConstructorWithoutValue() {
		Token token = new Token(TokenType.TYINT, 10);
		
		assertEquals(TokenType.TYINT, token.getTipo());
		assertEquals(10, token.getRiga());
		assertNull(token.getVal());
	}

	@Test
	void testGetters() {
		Token token = new Token(TokenType.PRINT, 3, "print");
		
		assertEquals(TokenType.PRINT, token.getTipo());
		assertEquals(3, token.getRiga());
		assertEquals("print", token.getVal());
	}

	@Test
	void testToString() {
		Token token = new Token(TokenType.OP_ASSIGN, 7, "+=");
		String s = "Token [riga=7, tipo=OP_ASSIGN, val=+=]";
		
		assertEquals(s, token.toString());
	}

	@Test
	void testToStringWithNullValue() {
		Token token = new Token(TokenType.EOF, 15);
		String s = "Token [riga=15, tipo=EOF, val=null]";
		
		assertEquals(s, token.toString());
	}
	
}
