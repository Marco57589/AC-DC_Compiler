package test;


import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;

class TestParserAst {

	private boolean logType = false;
	
	@Test
	void ast1() {
		String fileName = "src/test/data/testParserAst/ast1.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);

			Parser parser = new Parser(scanner, logType);
			NodeProgram program = parser.parse();

			System.out.println("\n*\tAST ottenuto:\t*");
			System.out.println(program);
		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}

	}
	
	@Test
	void ast2() {
		String fileName = "src/test/data/testParserAst/ast2.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);

			Parser parser = new Parser(scanner);
			NodeProgram program = parser.parse();

			System.out.println("\n*\tAST ottenuto:\t*");
			System.out.println(program);
		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}

	}

	@Test
	void astTypeChecking1() {
		String fileName = "src/test/data/testTypeChecking/3_idNonDec.txt";

		try {
			Scanner scanner = new Scanner(fileName, logType);

			Parser parser = new Parser(scanner);
			NodeProgram program = parser.parse();

			System.out.println("\n*\tAST ottenuto:\t*");
			System.out.println(program);
		} catch (LexicalException | SyntacticException | FileNotFoundException e) {
			System.err.println("Errore durante l'analisi: " + e.getMessage());
			fail(e);
		}

	}

}
