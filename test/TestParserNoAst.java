package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import parser.ParserNoAst;
import parser.SyntacticException;
import scanner.Scanner;
import token.TokenType;

class TestParserNoAst {

	@Test
	void testParserCorretto1() {
		System.out.println("testparsercorretto1");
		String fileName = "src/test/data/testParser/testParserCorretto1.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();
			
			while (scanner.peekToken().getTipo() != TokenType.EOF) {
				System.out.println(scanner.nextToken());
			}

			System.out.println("Parsing completato come previsto per testParserCorretto1.txt.");
		} catch (Exception e) {
			fail("Errore durante il parsing di testParserCorretto1.txt: " + e.getMessage());
		}
	}

	@Test
	void testParserCorretto2() {
		System.out.println("testparsercorretto2");

		String fileName = "src/test/data/testParser/testParserCorretto2.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();

			System.out.println("Parsing completato con successo per testParserCorretto2.txt.");
		} catch (Exception e) {
			fail("Errore durante il parsing di testParserCorretto2.txt: " + e.getMessage());
		}
	}

	@Test
	void testParserEcc_0() {
		String fileName = "src/test/data/testParser/testParserEcc_0.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();

			fail("Il parsing non ha lanciato l'eccezione prevista per testParserEcc_0.txt.");
		} catch (SyntacticException e) {
			System.out.println("Parsing fallito come previsto per testParserEcc_0.txt: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testParserEcc_0.txt: " + e.getMessage());
		}
	}

	@Test
	public void testParserEcc_1() {
		String fileName = "src/test/data/testParser/testParserEcc_1.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();

			fail("Il parsing non ha lanciato l'eccezione prevista per testParserEcc_1.txt.");
		} catch (SyntacticException e) {
			System.out.println("Parsing fallito come previsto per testParserEcc_1.txt: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testParserEcc_1.txt: " + e.getMessage());
		}
	}

	@Test
	public void testParserEcc_2() {
		String fileName = "src/test/data/testParser/testParserEcc_2.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();
			
			fail("Il parsing non ha lanciato l'eccezione prevista per testParserEcc_2.txt.");
		} catch (SyntacticException e) {
			System.out.println("Parsing fallito come previsto per testParserEcc_2.txt: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testParserEcc_2.txt: " + e.getMessage());
		}
	}

	@Test
	public void testParserEcc_3() {
		String fileName = "src/test/data/testParser/testParserEcc_3.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();
			
			fail("Il parsing non ha lanciato l'eccezione prevista per testParserEcc_3.txt.");
		} catch (SyntacticException e) {
			System.out.println("Parsing fallito come previsto per testParserEcc_3.txt: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testParserEcc_3.txt: " + e.getMessage());
		}
	}

	@Test
	public void testParserEcc_4() {
		String fileName = "src/test/data/testParser/testParserEcc_4.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();
			
			fail("Il parsing non ha lanciato l'eccezione prevista per testParserEcc_4.txt.");
		} catch (SyntacticException e) {
			System.out.println("Parsing fallito come previsto per testParserEcc_4.txt: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testParserEcc_4.txt: " + e.getMessage());
		}
	}

	@Test
	public void testParserEcc_5() {
		String fileName = "src/test/data/testParser/testParserEcc_5.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();
			
			fail("Il parsing non ha lanciato l'eccezione prevista per testParserEcc_5.txt.");
		} catch (SyntacticException e) {
			System.out.println("Parsing fallito come previsto per testParserEcc_5.txt: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testParserEcc_5.txt: " + e.getMessage());
		}
	}

	@Test
	public void testParserEcc_6() {
		String fileName = "src/test/data/testParser/testParserEcc_6.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();
			
			fail("Il parsing non ha lanciato l'eccezione prevista per testParserEcc_6.txt.");
		} catch (SyntacticException e) {
			System.out.println("Parsing fallito come previsto per testParserEcc_6.txt: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testParserEcc_6.txt: " + e.getMessage());
		}
	}

	@Test
	public void testParserEcc_7() {
		String fileName = "src/test/data/testParser/testParserEcc_7.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();
			
			fail("Il parsing non ha lanciato l'eccezione prevista per testParserEcc_7.txt.");
		} catch (SyntacticException e) {
			System.out.println("Parsing fallito come previsto per testParserEcc_7.txt: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testParserEcc_7.txt: " + e.getMessage());
		}
	}

	@Test
	public void testSoloDich() {
		String fileName = "src/test/data/testParser/testSoloDich.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();
			
			System.out.println("Parsing completato con successo per testSoloDich.txt.");
		} catch (SyntacticException e) {
			fail("Parsing fallito per testSoloDich.txt: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testSoloDich.txt: " + e.getMessage());
		}
	}

	@Test
	public void testSoloDichPrint() {
		String fileName = "src/test/data/testParser/testSoloDichPrint.txt";

		try {
			Scanner scanner = new Scanner(fileName);
			ParserNoAst parser = new ParserNoAst(scanner);
			parser.parse();
			
			System.out.println("Parsing completato come previsto per testSoloDichPrint.txt.");
		} catch (SyntacticException e) {
			fail("Il parsing non avrebbe dovuto fallire per testSoloDichPrint.txt, ma è stata lanciata un'eccezione: " + e.getMessage());
		} catch (Exception e) {
			fail("Errore inaspettato durante il parsing di testSoloDichPrint.txt: ");
		}
	}

}
