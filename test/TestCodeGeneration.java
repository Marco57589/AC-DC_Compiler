package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

class TestCodeGeneration {
	private boolean logType = false;

	@BeforeEach
	void setUp() {
		System.out.println("---------------------------------------------------------------------");
		SymbolTable.init(); //ripulisce la tabella
	}

	@Test
	void tc1() {
		String fileName = "src/test/data/testCodeGenerator/1_assign.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);
			var cgVisit = new CodeGeneratorVisitor();
			program.accept(cgVisit);

			System.out.println("\n****Test1 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(cgVisit.getResult(), "1 6 / sa la p P ");
			/*
			 	int temp = 1 / 6; 
				print temp;
				
				- Notazione polacca: 1 6 /
				-"sa" fa il pop dello stack e lo mette in a
				-"la" fa il push sullo stack del registro a
				p: stampa
				P: pop

			 */

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}
	
	@Test
	void tc2() {
		String fileName = "src/test/data/testCodeGenerator/2_divsioni.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);
			var cgVisit = new CodeGeneratorVisitor();
			program.accept(cgVisit);

			System.out.println("\n****Test2 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(cgVisit.getResult(), "0 sa la 1 + sa 6 sb 5 k 1.0 6 / 0 k la lb / + sc la p P lb p P lc p P ");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}
	
	@Test
	void tc3() {
		String fileName = "src/test/data/testCodeGenerator/3_generale.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);
			var cgVisit = new CodeGeneratorVisitor();
			program.accept(cgVisit);

			System.out.println("\n****Test3 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(cgVisit.getResult(), "5 3 + sa la 0.5 + sb la p P 5 k lb 4 / 0 k sb lb p P lb 1 - sc lc lb * sc lc p P ");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}
	
	@Test
	void tc4() {
		String fileName = "src/test/data/testCodeGenerator/4_registriFiniti.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);
			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();
			var tcVisit = new TypeCheckingVisitor(logType);
			program.accept(tcVisit);
			var cgVisit = new CodeGeneratorVisitor();
			program.accept(cgVisit);

			System.out.println("\n****Test3 Result: Messaggio di errore generato: " + tcVisit.getResType().getMsg());

			assertEquals(cgVisit.getResult(), "6 2 / sa la p P 9 2 / sb lb p P ");

		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}
	}

}
