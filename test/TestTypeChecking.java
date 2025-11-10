package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ast.NodeProgram;
import ast.TipoTD;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.TypeCheckingVisitor;

class TestTypeChecking {
	private boolean logType = false;

	@BeforeEach
	void setUp() {
		System.out.println("---------------------------------------------------------------------");
		SymbolTable.init(); //ripulisce la tabella
	}

	@Test
	void tc1() {
		String fileName = "src/test/data/testTypeChecking/1_dicRipetute.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);

			System.out.println("\n****Test1 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(TipoTD.ERROR, tcVisit.getResType().getTipo());

			assertTrue(tcVisit.getResType().getMsg().contains("é già stata dichiarata"), "Aspettato errore di dichiarazione ripetuta.");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}


	@Test
	void tc2() {
		String fileName = "src/test/data/testTypeChecking/2_idNonDec.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);

			System.out.println("\n****Test2 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(TipoTD.ERROR, tcVisit.getResType().getTipo());

			assertTrue(tcVisit.getResType().getMsg().contains("La seguente variabile non è stata dichiarata [b]"), "Aspettato errore di variabile non dichiarata.");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}

	@Test
	void tc3() {
		String fileName = "src/test/data/testTypeChecking/3_idNonDec.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);

			System.out.println("\n****Test3 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(TipoTD.ERROR, tcVisit.getResType().getTipo());

			assertTrue(tcVisit.getResType().getMsg().contains("La seguente variabile non è stata dichiarata [c]"), "Aspettato errore di variabile non dichiarata.");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}

	@Test
	void tc4() {
		String fileName = "src/test/data/testTypeChecking/4_tipoNonCompatibile.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);

			System.out.println("\n****Test4 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(TipoTD.ERROR, tcVisit.getResType().getTipo());

			assertTrue(tcVisit.getResType().getMsg().contains("tipi incopatibili INT è diverso daFLOAT"), "Aspettato errore tipi incompatibili.");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}

	@Test
	void tc5() {
		String fileName = "src/test/data/testTypeChecking/5_corretto.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);

			System.out.println("\n****Test5 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(TipoTD.OK, tcVisit.getResType().getTipo());

			assertNull(tcVisit.getResType().getMsg(), "Nessun messaggio di errore atteso.");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}

	@Test
	void tc6() {
		String fileName = "src/test/data/testTypeChecking/6_corretto.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);

			System.out.println("\n****Test6 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(TipoTD.OK, tcVisit.getResType().getTipo());

			assertNull(tcVisit.getResType().getMsg(), "Nessun messaggio di errore atteso.");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}

	@Test
	void tc7() {
		String fileName = "src/test/data/testTypeChecking/7_corretto.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);

			System.out.println("\n****Test7 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(TipoTD.OK, tcVisit.getResType().getTipo());

			assertNull(tcVisit.getResType().getMsg(), "Nessun messaggio di errore atteso.");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}

}
