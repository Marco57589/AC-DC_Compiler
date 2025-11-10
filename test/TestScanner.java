package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestScanner {
	private boolean logType = false;

	@Test
	void testCaratteriNonCaratteri() {
		String fileName = "src/test/data/testScanner/caratteriNonCaratteri.txt";
		try {
			Scanner scanner = new Scanner(fileName, logType);

			LexicalException exception = assertThrows(LexicalException.class, () -> {
				Token token;
				while ((token = scanner.nextToken()).getTipo() != TokenType.EOF) {
					System.out.println("Token trovato: " + token);
				}
			});

			assertTrue(exception.getMessage().contains("Carattere non valido"));

			System.out.println("LexicalException rilevata: " + exception.getMessage());
		} catch (Exception e) {
			fail("Errore durante il test: " + e.getMessage());
		}
	}

	@Test
	void testFloatNumbers() {
		String fileName = "src/test/data/testScanner/testFloat.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Token token;

			//token (098.8095)
			token = scanner.nextToken();
			assertEquals(TokenType.FLOAT, token.getTipo());
			assertEquals("098.8095", token.getVal());

			//token (0.)
			token = scanner.nextToken();
			assertEquals(TokenType.FLOAT, token.getTipo());
			assertEquals("0.0", token.getVal());

			//token (98.)
			token = scanner.nextToken();
			assertEquals(TokenType.FLOAT, token.getTipo());
			assertEquals("98.0", token.getVal());

			//token (89.99999)
			token = scanner.nextToken();
			assertEquals(TokenType.FLOAT, token.getTipo());
			assertEquals("89.99999", token.getVal());

			//token sia EOF
			token = scanner.nextToken();
			assertEquals(TokenType.EOF, token.getTipo());
			assertEquals(5, token.getRiga());

		} catch (Exception e) {
			fail("Errore durante il test: " + e.getMessage());
		}
	}

	@Test
	void testErroriNumbers() {
		String fileName = "src/test/data/testScanner/erroriNumbers.txt";
		try {
			Scanner scanner = new Scanner(fileName, logType);
			Token token;

			// 0
			token = scanner.nextToken();
			assertEquals(TokenType.INT, token.getTipo());
			assertEquals("0", token.getVal());

			// 33
			token = scanner.nextToken();
			assertEquals(TokenType.INT, token.getTipo());
			assertEquals("33", token.getVal());

			// 123.121212
			token = scanner.nextToken();
			assertEquals(TokenType.FLOAT, token.getTipo());
			assertEquals("123.121212", token.getVal());


			// 123.123.123
			LexicalException exception = assertThrows(LexicalException.class, () -> {
				scanner.nextToken();
			});

			assertTrue(exception.getMessage().contains("Errore durante la lettura del numero"));			


		} catch (Exception e) {
			fail("Errore durante il test: " + e.getMessage());
		}
	}

	@Test
	void testEOF() {
		String fileName = "src/test/data/testScanner/testEOF.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Token token = scanner.nextToken();

			System.out.println(token.toString());
			assertEquals(TokenType.EOF, token.getTipo(), "Il primo token non è EOF come atteso.");
			assertEquals(3, token.getRiga(), "La riga del token EOF dovrebbe essere 4.");
		} catch (Exception e) {
			fail("Errore durante il test: " + e.getMessage());
		}
	}

	@Test
	void testGenerale() {
		String fileName = "src/test/data/testScanner/testGenerale.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Token token;

			token = scanner.nextToken();
			assertEquals(TokenType.TYINT, token.getTipo(), "Il primo token non è TYINT.");
			assertEquals("int", token.getVal(), "Il valore del primo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.ID, token.getTipo(), "Il secondo token non è ID.");
			assertEquals("temp", token.getVal(), "Il valore del secondo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.SEMI, token.getTipo(), "Il terzo token non è SEMI.");
			assertEquals(";", token.getVal(), "Il valore del terzo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.ID, token.getTipo(), "Il quarto token non è ID.");
			assertEquals("temp", token.getVal(), "Il valore del quarto token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.OP_ASSIGN, token.getTipo(), "Il quinto token non è OP_ASSIGN.");
			assertEquals("+=", token.getVal(), "Il valore del quinto token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.FLOAT, token.getTipo(), "Il sesto token non è TYFLOAT.");
			assertEquals("5.0", token.getVal(), "Il valore del sesto token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.SEMI, token.getTipo(), "Il settimo token non è SEMI.");
			assertEquals(";", token.getVal(), "Il valore del settimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.TYFLOAT, token.getTipo(), "L'ottavo token non è TYFLOAT.");
			assertEquals("float", token.getVal(), "Il valore dell'ottavo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.ID, token.getTipo(), "Il nono token non è ID.");
			assertEquals("b", token.getVal(), "Il valore del nono token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.SEMI, token.getTipo(), "Il decimo token non è SEMI.");
			assertEquals(";", token.getVal(), "Il valore del decimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.ID, token.getTipo(), "L'undicesimo token non è ID.");
			assertEquals("b", token.getVal(), "Il valore dell'undicesimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.ASSIGN, token.getTipo(), "Il dodicesimo token non è ASSIGN.");
			assertEquals("=", token.getVal(), "Il valore del dodicesimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.ID, token.getTipo(), "Il tredicesimo token non è ID.");
			assertEquals("temp", token.getVal(), "Il valore del tredicesimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.PLUS, token.getTipo(), "Il quattordicesimo token non è PLUS.");
			assertEquals("+", token.getVal(), "Il valore del quattordicesimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.FLOAT, token.getTipo(), "Il quindicesimo token non è TYFLOAT.");
			assertEquals("3.2", token.getVal(), "Il valore del quindicesimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.SEMI, token.getTipo(), "Il sedicesimo token non è SEMI.");
			assertEquals(";", token.getVal(), "Il valore del sedicesimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.PRINT, token.getTipo(), "Il diciassettesimo token non è PRINT.");
			assertEquals("print", token.getVal(), "Il valore del diciassettesimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.ID, token.getTipo(), "Il diciottesimo token non è ID.");
			assertEquals("b", token.getVal(), "Il valore del diciottesimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.SEMI, token.getTipo(), "Il diciannovesimo token non è SEMI.");
			assertEquals(";", token.getVal(), "Il valore del diciannovesimo token non è corretto.");

			token = scanner.nextToken();
			assertEquals(TokenType.EOF, token.getTipo(), "Il ventesimo token non è EOF.");
			assertEquals(7, token.getRiga(), "La riga del token EOF dovrebbe essere 5.");

		} catch (Exception e) {
			fail("Errore inaspettato durante il test: " + e.getMessage());
		}
	}


	@Test
	void testId() {
		String fileName = "src/test/data/testScanner/testId.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);

			Token token = scanner.nextToken(); //token (jskjdsfhkjdshkf)
			System.out.println(token.toString());
			assertEquals(TokenType.ID, token.getTipo(),"Il primo token non è un identificatore valido.");
			assertEquals("jskjdsfhkjdshkf", token.getVal(), "Il valore dell'identificatore dovrebbe essere 'jskjdsfhkjdshkf'.");

			token = scanner.nextToken(); //token (printl)
			System.out.println(token.toString());
			assertEquals(TokenType.ID, token.getTipo(), "Il secondo token non è una parola chiave valida.");
			assertEquals("printl", token.getVal(), "Il valore del token dovrebbe essere 'printl'.");

			token = scanner.nextToken(); //token (ffloat)
			System.out.println(token.toString());
			assertEquals(TokenType.ID, token.getTipo(), "Il terzo token non è una parola chiave valida.");
			assertEquals("ffloat", token.getVal(), "Il valore del token dovrebbe essere 'ffloat'.");

			token = scanner.nextToken(); //token (hhhjj)
			System.out.println(token.toString());
			assertEquals(TokenType.ID, token.getTipo(), "Il quarto token non è un identificatore valido.");
			assertEquals("hhhjj", token.getVal(), "Il valore dell'identificatore dovrebbe essere 'hhhjj'.");

			token = scanner.nextToken(); // Verifica l'EOF
			System.out.println(token.toString());
			assertEquals(TokenType.EOF, token.getTipo(), "Il token successivo dovrebbe essere EOF.");

		} catch (Exception e) {
			fail("Errore durante il test: " + e.getMessage());
		}
	}


	@Test
	void testIdKeyWords() {
		String fileName = "src/test/data/testScanner/testIdKeyWords.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);

			Token token = scanner.nextToken(); //int (tyint)
			System.out.println(token.toString());
			assertEquals(TokenType.TYINT, token.getTipo(), "Il primo token non è la parola chiave 'int'.");
			assertEquals("int", token.getVal(), "Il valore della parola chiave dovrebbe essere 'int'.");

			token = scanner.nextToken(); //inta (id)
			System.out.println(token.toString());
			assertEquals(TokenType.ID, token.getTipo(), "Il secondo token non è un identificatore valido.");
			assertEquals("inta", token.getVal(), "Il valore dell'identificatore dovrebbe essere 'inta'.");

			token = scanner.nextToken(); //float (tyfloat)
			System.out.println(token.toString());
			assertEquals(TokenType.TYFLOAT, token.getTipo(), "Il terzo token non è la parola chiave 'float'.");
			assertEquals("float", token.getVal(), "Il valore della parola chiave dovrebbe essere 'float'.");

			token = scanner.nextToken(); //print (print)
			System.out.println(token.toString());
			assertEquals(TokenType.PRINT, token.getTipo(), "Il quarto token non è la parola chiave 'print'.");
			assertEquals("print", token.getVal(), "Il valore della parola chiave dovrebbe essere 'print'.");

			token = scanner.nextToken(); //nome (id)
			System.out.println(token.toString());
			assertEquals(TokenType.ID, token.getTipo(), "Il quinto token non è un identificatore valido.");
			assertEquals("nome", token.getVal(), "Il valore dell'identificatore dovrebbe essere 'nome'.");

			token = scanner.nextToken(); //intnome (id)
			System.out.println(token.toString());
			assertEquals(TokenType.ID, token.getTipo(), "Il sesto token non è un identificatore valido.");
			assertEquals("intnome", token.getVal(), "Il valore dell'identificatore dovrebbe essere 'intnome'.");

			token = scanner.nextToken();  //int (tyint)
			System.out.println(token.toString());
			assertEquals(TokenType.TYINT, token.getTipo(), "Il settimo token non è la parola chiave 'int'.");
			assertEquals("int", token.getVal(), "Il valore della parola chiave dovrebbe essere 'int'.");

			token = scanner.nextToken();  //nome (id)
			System.out.println(token.toString());
			assertEquals(TokenType.ID, token.getTipo(), "L'ottavo token non è un identificatore valido.");
			assertEquals("nome", token.getVal(), "Il valore dell'identificatore dovrebbe essere 'nome'.");

			token = scanner.nextToken();  //eof (eof)
			System.out.println(token.toString());
			assertEquals(TokenType.EOF, token.getTipo(), "Il token successivo dovrebbe essere EOF.");

		} catch (Exception e) {
			fail("Errore durante il test: " + e.getMessage());
		}
	}

	@Test
	void testIntNumbers() {
		String fileName = "src/test/data/testScanner/testInt.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);

			Token token = scanner.nextToken(); //0050
			System.out.println(token.toString());
			assertEquals(TokenType.INT, token.getTipo(), "Il primo token non è un numero intero.");
			assertEquals("0050", token.getVal(), "Il valore del numero intero dovrebbe essere '0050'.");

			token = scanner.nextToken();  //698
			System.out.println(token.toString());
			assertEquals(TokenType.INT, token.getTipo(), "Il secondo token non è un numero intero.");
			assertEquals("698", token.getVal(), "Il valore del numero intero dovrebbe essere '698'.");

			token = scanner.nextToken(); //560099
			System.out.println(token.toString());
			assertEquals(TokenType.INT, token.getTipo(), "Il terzo token non è un numero intero.");
			assertEquals("560099", token.getVal(), "Il valore del numero intero dovrebbe essere '560099'.");

			token = scanner.nextToken(); //1234
			System.out.println(token.toString());
			assertEquals(TokenType.INT, token.getTipo(), "Il quarto token non è un numero intero.");
			assertEquals("1234", token.getVal(), "Il valore del numero intero dovrebbe essere '1234'.");

			token = scanner.nextToken();  //eof
			System.out.println(token.toString());
			assertEquals(TokenType.EOF, token.getTipo(), "Il token successivo dovrebbe essere EOF.");

		} catch (Exception e) {
			fail("Errore durante il test: " + e.getMessage());
		}
	}

	@Test
	void testKeyWords() {
		String fileName = "src/test/data/testScanner/testKeyWords.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);

			Token token = scanner.nextToken(); //print (print)
			System.out.println(token.toString());
			assertEquals(TokenType.PRINT, token.getTipo(), "Il primo token non è la parola chiave 'print'.");
			assertEquals("print", token.getVal(), "Il valore della parola chiave dovrebbe essere 'print'.");

			token = scanner.nextToken(); //float (tyfloat)
			System.out.println(token.toString());
			assertEquals(TokenType.TYFLOAT, token.getTipo(), "Il secondo token non è la parola chiave 'float'.");
			assertEquals("float", token.getVal(), "Il valore della parola chiave dovrebbe essere 'float'.");

			token = scanner.nextToken(); //int (tyint)
			System.out.println(token.toString());
			assertEquals(TokenType.TYINT, token.getTipo(), "Il terzo token non è la parola chiave 'int'.");
			assertEquals("int", token.getVal(), "Il valore della parola chiave dovrebbe essere 'int'.");

			token = scanner.nextToken(); //eof
			System.out.println(token.toString());
			assertEquals(TokenType.EOF, token.getTipo(), "Il token successivo dovrebbe essere EOF.");

		} catch (Exception e) {
			fail("Errore durante il test: " + e.getMessage());
		}
	}

	@Test
	void testOpsDels() {
		String fileName = "src/test/data/testScanner/testOpsDels.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);

			Token token = scanner.nextToken(); //+ plus
			System.out.println(token.toString());
			assertEquals(TokenType.PLUS, token.getTipo(), "Il primo token non è l'operatore '+'.");
			assertEquals("+", token.getVal(), "Il valore dell'operatore dovrebbe essere '+'.");

			token = scanner.nextToken(); // /= op_assign
			System.out.println(token.toString());
			assertEquals(TokenType.OP_ASSIGN, token.getTipo(), "Il secondo token non è l'operatore '/='.");
			assertEquals("/=", token.getVal(), "Il valore dell'operatore dovrebbe essere '/='.");

			token = scanner.nextToken(); //- minus
			System.out.println(token.toString());
			assertEquals(TokenType.MINUS, token.getTipo(), "Il terzo token non è l'operatore '-'.");
			assertEquals("-", token.getVal(), "Il valore dell'operatore dovrebbe essere '-'.");

			token = scanner.nextToken(); //* times
			System.out.println(token.toString());
			assertEquals(TokenType.TIMES, token.getTipo(), "Il quarto token non è l'operatore '*'.");
			assertEquals("*", token.getVal(), "Il valore dell'operatore dovrebbe essere '*'.");

			token = scanner.nextToken(); // / divide
			System.out.println(token.toString());
			assertEquals(TokenType.DIVIDE, token.getTipo(), "Il quinto token non è l'operatore '/'.");
			assertEquals("/", token.getVal(), "Il valore dell'operatore dovrebbe essere '/'.");

			token = scanner.nextToken(); //+= op_assign
			System.out.println(token.toString());
			assertEquals(TokenType.OP_ASSIGN, token.getTipo(), "Il sesto token non è l'operatore '+='.");
			assertEquals("+=", token.getVal(), "Il valore dell'operatore dovrebbe essere '+='.");

			token = scanner.nextToken(); // = assign
			System.out.println(token.toString());
			assertEquals(TokenType.ASSIGN, token.getTipo(), "Il settimo token non è l'operatore '='.");
			assertEquals("=", token.getVal(), "Il valore dell'operatore dovrebbe essere '='.");

			token = scanner.nextToken(); //-= op_assign
			System.out.println(token.toString());
			assertEquals(TokenType.OP_ASSIGN, token.getTipo(), "L'ottavo token non è l'operatore '-='.");
			assertEquals("-=", token.getVal(), "Il valore dell'operatore dovrebbe essere '-='.");

			token = scanner.nextToken(); //- minus
			System.out.println(token.toString());
			assertEquals(TokenType.MINUS, token.getTipo(), "Il nono token non è l'operatore '-'.");
			assertEquals("-", token.getVal(), "Il valore dell'operatore dovrebbe essere '-'.");

			token = scanner.nextToken(); // = assign
			System.out.println(token.toString());
			assertEquals(TokenType.ASSIGN, token.getTipo(), "Il decimo token non è l'operatore '='.");
			assertEquals("=", token.getVal(), "Il valore dell'operatore dovrebbe essere '='.");

			token = scanner.nextToken(); //*= (op_assing)
			System.out.println(token.toString());
			assertEquals(TokenType.OP_ASSIGN, token.getTipo(), "L'undicesimo token non è l'operatore '*='.");
			assertEquals("*=", token.getVal(), "Il valore dell'operatore dovrebbe essere '*='.");

			token = scanner.nextToken(); //; (semi)
			System.out.println(token.toString());
			assertEquals(TokenType.SEMI, token.getTipo(), "Il dodicesimo token non è il delimitatore ';'.");
			assertEquals(";", token.getVal(), "Il valore del delimitatore dovrebbe essere ';'.");

			token = scanner.nextToken(); //eof
			System.out.println(token.toString());
			assertEquals(TokenType.EOF, token.getTipo(), "Il token successivo dovrebbe essere EOF.");

		} catch (Exception e) {
			fail("Errore durante il test: " + e.getMessage());
		}
	}

	@Test
	void testPeekToken () {
		String fileName = "src/test/data/testScanner/testGeneralePeekToken.txt";

		try {
			Scanner s = new Scanner(fileName, logType);
			System.out.println("peektokens");
			assertEquals(s.peekToken().getTipo(), TokenType.TYINT );
			assertEquals(s.nextToken().getTipo(), TokenType.TYINT );
			assertEquals(s.peekToken().getTipo(), TokenType.ID );
			assertEquals(s.peekToken().getTipo(), TokenType.ID );

			Token t = s.nextToken();
			assertEquals(t.getTipo(), TokenType.ID);
			assertEquals(t.getRiga(), 1);
			assertEquals(t.getVal(), "temp");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}