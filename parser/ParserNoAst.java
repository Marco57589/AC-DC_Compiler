package parser;

import scanner.LexicalException;

import scanner.Scanner;
import token.Token;
import token.TokenType;

/**
 * Classe ParserNoASst per l'analisi sintattica del linguaggio.
 * Questa classe leggere i token senza costruire un AST
 * 
 * @author Marco Yuri Papa 20051241
 */
public class ParserNoAst {
	private Scanner scanner;

	public ParserNoAst(Scanner sc) {
		this.scanner = sc;
	}

	/**
	 * Verifica se il prossimo token corrisponde al tipo atteso e lo consuma.
	 * 
	 * @param type Tipo di token atteso.
	 * @return Il token consumato (Token).
	 * @throws SyntacticException Se il token non corrisponde al tipo atteso.
	 */
	private Token match(TokenType type) throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();

			if (type.equals(tk.getTipo())) { 
				return scanner.nextToken();
			} else {
				throw new SyntacticException("Aspettato token " +tk.getTipo()+" token alla riga "+tk.getRiga());
			}
		}catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}

	}

	/**
	 * Avvia l'analisi sintattica del programma.
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 * @throws LexicalException Se si verifica un errore lessicale.
	 */
	public void parse() throws SyntacticException, LexicalException{ //che ritorna parsePrg
		this.parsePrg();
	}

	/**
	 * Analizza il programma principale.
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parsePrg() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
			switch (tk.getTipo()) {
				case TYFLOAT, TYINT, ID, PRINT, EOF: //// DSs $ { TYFLOAT, TYINT,ID, PRINT,EOF }
					parseDSs();
					match(TokenType.EOF);
					return;
	
				default:
					throw new SyntacticException("token " +tk.getTipo()+" alla riga "+tk.getRiga()+ "non e’ inizio di programma!");
			}
		} catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}

	/**
	 * Analizza una sequenza di dichiarazioni e istruzioni.
	 * 
	 * DSs → Dcl DSs
	 * DSs → Stm DSs
	 * DSs → ϵ
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseDSs() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	            case TYFLOAT, TYINT: // Dcl DSs { TYFLOAT, TYINT }
	                parseDcl();
	                parseDSs();
	                break;
	            case ID, PRINT: // Stm DSs { ID, PRINT }
	                parseStm();
	                parseDSs();
	                break;
	            case EOF: // ϵ {EOF }
	                return;
	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in DSs.");
	        }
	    } catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}

	/**
	 * Analizza una dichiarazione.
	 * 
	 * Dcl → Ty id DclP
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseDcl() throws SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();

			switch (tk.getTipo()) {
				case TYFLOAT, TYINT: ////Ty id DclP { TYFLOAT, TYINT }
					//match(tk.getTipo());
					parseTy();
					match(TokenType.ID);
					parseDclP();
					break;
			default:
				throw new SyntacticException("token " +tk.getTipo()+" alla riga "+tk.getRiga()+ "non e’ inizio di programma!");
			}
		}catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}

	/**
	 * Analizza l'inizializzazione di una dichiarazione.
	 * 
	 * DclP → ;
	 * DclP → = Exp;
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseDclP() throws SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();

			switch (tk.getTipo()) {
				case SEMI: // ; { SEMI}
					match(TokenType.SEMI);
					break;
	
				case ASSIGN: // = Exp; { ASSIGN }
					match(TokenType.ASSIGN);
					parseExp();
					match(TokenType.SEMI);
					break;
					
				default:
					throw new SyntacticException("token " +tk.getTipo()+" alla riga "+tk.getRiga()+ "non e’ inizio di programma!");
			}
		}catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}
	
	/**
	 * Analizza un'istruzione.
	 * 
	 * Stm → id Op Exp;
	 * Stm → print id;
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseStm() throws SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();

			switch (tk.getTipo()) {
				case ID: // Stm id Op Exp; { ID }
					match(TokenType.ID);
					parseOp();
					parseExp();
					match(TokenType.SEMI);
					break;
					
				case PRINT: // print id; { PRINT }
					match(TokenType.PRINT);
					match(TokenType.ID);
					match(TokenType.SEMI);
					break;
					
				default:
					throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in DSs.");
			}
		}catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}

	/**
	 * Analizza un'espressione.
	 * 
	 * Exp → Tr ExpP
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseExp() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();

			switch (tk.getTipo()) {
				case ID, FLOAT, INT: // Tr ExpP { ID, FLOAT, INT}
					parseTr();
					parseExpP();
					break;

			default:
				throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in DSs.");
			}
		}catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}
	
	/**
	 * Analizza la continuazione di un'espressione.
	 * 
	 * ExpP → + Tr ExpP
	 * ExpP → - Tr ExpP
	 * ExpP → ϵ
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseExpP() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	            case PLUS: //  +Tr ExpP { PLUS }
	                match(TokenType.PLUS);
	                parseTr();
	                parseExpP();
	                break;

	            case MINUS: // -Tr ExpP { MINUS }
	                match(TokenType.MINUS);
	                parseTr();
	                parseExpP();
	                break;

	            case SEMI: // ϵ {SEMI}
	                return;

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in TrP.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}

	/**
	 * Analizza un termine.
	 * 
	 * Tr → Val TrP
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseTr() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	            case ID, FLOAT, INT: // Tr Val TrP { ID, FLOAT, INT}
	                parseVal();
	                parseTrP();
	                break;

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in Tr.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}
	
	/**
	 * Analizza la continuazione di un termine.
	 * 
	 * TrP → * Val TrP
	 * TrP → / Val TrP
	 * TrP → ϵ
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseTrP() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	            case TIMES: // *Val TrP { TIMES}
	                match(TokenType.TIMES);
	                parseVal();
	                parseTrP();
	                break;
 
	            case DIVIDE: // /Val TrP { DIVIDE}
	                match(TokenType.DIVIDE);
	                parseVal();
	                parseTrP();
	                break;

	            case PLUS, MINUS, SEMI: // ϵ { MINUS, PLUS, SEMI}
	                //pred. vuota
	                return;

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in TrP.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}
	
	/**
	 * Analizza un tipo.
	 * 
	 * Ty → TYFLOAT
	 * Ty → TYINT
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseTy() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	            case TYFLOAT: // Ty float { TYFLOAT }
	                match(TokenType.TYFLOAT);
	                break;

	            case TYINT: // Ty int { TYINT }
	                match(TokenType.TYINT);
	                break;

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in Ty.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}

	/**
	 * Analizza un valore.
	 * 
	 * Val → INT
	 * Val → FLOAT
	 * Val → ID
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseVal() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();
	
	        switch (tk.getTipo()) {
	            case INT: // Val intVal { INT }
	                match(TokenType.INT);
	                break;
	
	            case FLOAT: // Val floatVal { FLOAT}
	                match(TokenType.FLOAT);
	                break;
	
	            case ID: // Val id {ID}
	                match(TokenType.ID);
	                break;
	
	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in Val.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}
	
	/**
	 * Analizza un operatore.
	 * 
	 * Op → ASSIGN
	 * Op → OP_ASSIGN
	 * 
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 */
	private void parseOp() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
		        case ASSIGN: // Op = {ASSIGN}
		            match(TokenType.ASSIGN);
		            break;
		        case OP_ASSIGN: // Op opAss {OP_ASSIGN}
		            match(TokenType.OP_ASSIGN);
		            break;
		        default:
		        	throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in Op.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}
	
}
