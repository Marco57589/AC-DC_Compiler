package parser;

import java.util.ArrayList;

import ast.*;
import scanner.LexicalException;

import scanner.Scanner;
import token.Token;
import token.TokenType;

/**
 * Classe Parser per l'analisi sintattica del linguaggio.
 * Questa classe leggere i token e costruisce un AST
 * 
 * @author Marco Yuri Papa 20051241
 */
public class Parser {
	private Scanner scanner;
	private boolean log; //true attivi, false disattivi


	/**
	 * Costruttore del parser che inizializza lo scanner.
	 * Il logType viene settato di default su false
	 * 
	 * @param sc Scanner da usare nel parser.
	 */
	public Parser(Scanner sc) {
	    this(sc, false);
	}

	/**
	 * Costruttore del parser che inizializza lo scanner e il flag dei log.
	 * 
	 * @param sc Scanner da utilizzare per l'analisi
	 * @param log Flag per abilitare il debug log
	 */
	public Parser(Scanner sc, boolean log) {
	    this.scanner = sc;
	}

	/**
	 * Verifica se il prossimo token corrisponde al tipo atteso e lo restituisce.
	 * 
	 * @param type Tipo di token atteso.
	 * @return Il token abbinato.
	 * @throws SyntacticException Se il token non corrisponde al tipo atteso.
	 */
	private Token match(TokenType type) throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();

			if (type.equals(tk.getTipo())) { //verifico se il token successivo corrisponde con quello atteso
				return scanner.nextToken();
			} else {
				throw new SyntacticException("Aspettato token " +tk.getTipo()+" token alla riga "+tk.getRiga()+": "+type.toString());
			}
		}catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}

	}

	/**
	 * Avvia l'analisi sintattica e restituisce il nodo radice del programma.
	 * 
	 * @return Il nodo radice del programma.
	 * @throws SyntacticException Se si verifica un errore sintattico.
	 * @throws LexicalException Se si verifica un errore lessicale.
	 */
	public NodeProgram parse() throws SyntacticException, LexicalException{
		return this.parsePrg();
	}

	/**
	 * Costruisce il nodo root dell'AST del programma.
	 * 
	 * DSs $ 
	 * 
	 * @return Il nodo del programma
	 * @throws SyntacticException Se si verifica un errore sintattico
	 */
	private NodeProgram parsePrg() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
			switch (tk.getTipo()) {
				// DSs $ { TYFLOAT, TYINT,ID, PRINT,EOF }
				case TYFLOAT, TYINT, ID, PRINT, EOF:
					ArrayList<NodeDecSt> decSts = parseDSs();
					match(TokenType.EOF);
			        return new NodeProgram(decSts);
	
				default:
					throw new SyntacticException("token " +tk.getTipo()+" alla riga "+tk.getRiga()+ "non e’ inizio di programma!");
			}
		} catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}

	/**
	 * Analizza e restituisce una lista di dichiarazioni e istruzioni.
	 * 
	 * DSs → Dcl DSs
	 * DSs → Stm DSs
     * DSs → ϵ
	 * 
	 * @return Un elenco di nodi di dichiarazione
	 * @throws SyntacticException Se si verifica un errore sintattico
	 */
	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException {
	    Token tk;
	    try {

	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	        	// Dcl DSs { TYFLOAT, TYINT }
	            case TYFLOAT, TYINT:
	    			NodeDecl decl = parseDcl();
		        	ArrayList<NodeDecSt> decSts = parseDSs();
	    			decSts.add(0, decl);
	    			return decSts;
	    		// Stm DSs { ID, PRINT }	
	            case ID, PRINT:
	            	NodeStm stm = parseStm();
	        		ArrayList<NodeDecSt> decSts1 = parseDSs();
	                decSts1.add(0, stm);	 
	                return decSts1;
	            // ϵ {EOF }
	            case EOF:
	                return new ArrayList<NodeDecSt>();
	                
	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in DSs.");
	        }
	    } catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}

	/**
	 * Analizza una dichiarazione (Dcl) e restituisce il nodo corrispondente.
	 * 
	 * Dcl → Ty id DclP
	 * 
	 * @return Il nodo di Decl
	 * @throws SyntacticException Se si verifica un errore sintattico
	 */
	private NodeDecl parseDcl() throws SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();

			switch (tk.getTipo()) {
				//Ty id DclP { TYFLOAT, TYINT }
				case TYFLOAT, TYINT:
	                LangType type = parseTy();
					NodeId id = new NodeId(match(TokenType.ID).getVal());
                    NodeExpr init = parseDclP();
                    if(log) {
                        System.out.println("Inizializzazione per " + id.getName() + ": " + init);
                    }
                    return new NodeDecl(id, type, init);
                    
				default:
					throw new SyntacticException("token " +tk.getTipo()+" alla riga "+tk.getRiga()+ "non e’ inizio di programma!");
			}
		}catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}

	/**
     * Analizza l'inizializzazione di una dichiarazione (DclP).
	 * 
	 * DclP → ;
	 * DclP → = Exp;
	 * @return Il nodo Exp se presente, altrimenti null
	 * @throws SyntacticException Se si verifica un errore sintattico
	 */
	private NodeExpr parseDclP() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	        	// ; { SEMI}
	            case SEMI:
	                match(TokenType.SEMI);
	                return null;
	            // = Exp; { ASSIGN }
	            case ASSIGN:
	                match(TokenType.ASSIGN);
	                NodeExpr expr = parseExp();
	                match(TokenType.SEMI);
	                return expr;

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non e’ inizio di programma!");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}
	
	/**
     * Analizza un'istruzione (Stm) e restituisce il nodo corrispondente.
	 * 
	 * Stm → id OpExp;
     * Stm → print id;
     * 
	 * @return Il nodo istruzione
	 * @throws SyntacticException Se si verifica un errore sintattico
	 */
	private NodeStm parseStm() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	        	// Stm id Op Exp; { ID }
	            case ID:
	            	NodeId id = new NodeId(match(TokenType.ID).getVal());
	                Token op = parseOp();
	                NodeExpr exp = parseExp();

	                switch (op.getVal()) {
	                    case "+=":
	                        exp = new NodeBinOp(new NodeDeref(id), LangOper.PLUS, exp);
	                        break;
	                        
	                    case "-=":
	                        exp = new NodeBinOp(new NodeDeref(id), LangOper.MINUS, exp);
	                        break;
	                        
	                    case "*=":
	                        exp = new NodeBinOp(new NodeDeref(id), LangOper.TIMES, exp);
	                        break;
	                        
	                    case "/=":
	                        exp = new NodeBinOp(new NodeDeref(id), LangOper.DIVIDE, exp);
	                        break;
	                }

	                match(TokenType.SEMI);
	                return new NodeAssign(id, exp);
	            // print id; { PRINT }
	            case PRINT:
	                match(TokenType.PRINT);
	                id = new NodeId(match(TokenType.ID).getVal());
	                match(TokenType.SEMI);
	                return new NodePrint(id);

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in DSs.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}

	/**
     * Analizza un'espressione (Exp) e restituisce il nodo corrispondente.
	 * 
	 * Exp → Tr ExpP
	 * 
	 * @return Il nodo di espressione
	 * @throws SyntacticException Se si verifica un errore sintattico
	 */
	private NodeExpr parseExp() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();

			switch (tk.getTipo()) {
				// Tr ExpP { ID, FLOAT, INT}
				case ID, FLOAT, INT:
					NodeExpr tr = parseTr();
					NodeExpr expP = parseExpP(tr);
					return expP;

				default:
					throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in DSs.");
			}
		}catch(LexicalException le) {
			throw new SyntacticException("Lexical Exception", le);
		}
	}
	
	/**
     * Analizza la continuazione di un'espressione (ExpP) e restituisce il nodo risultante.
	 * 
	 * ExpP → -Tr ExpP
	 * ExpP → +Tr ExpP
	 * ExpP → ϵ
	 * 
	 * @param left Il nodo di espressione a sinistra
	 * @return Il nodo di espressione risultante
	 * @throws SyntacticException Se si verifica un errore sintattico
	 */
	private NodeExpr parseExpP(NodeExpr left) throws LexicalException, SyntacticException {
		Token tk = scanner.peekToken();
		switch(tk.getTipo()) {
			//  +Tr ExpP { PLUS }
			 case PLUS:
				 match(TokenType.PLUS);
				 NodeExpr expr = parseTr();
				 return parseExpP(new NodeBinOp(left,LangOper.PLUS,expr));
			// -Tr ExpP { MINUS }
			 case MINUS:
				 match(TokenType.MINUS);
				 NodeExpr expr1 = parseTr();
				 return parseExpP(new NodeBinOp(left,LangOper.MINUS,expr1));
			// ϵ {SEMI}
			 case SEMI:
				 return left;
			 
			 default:
				 throw new SyntacticException("Token " + tk + " non valido per ExpP.");
		}
	}


	/**
	 * Analizza un'espressione Tr (termine) e restituisce il nodo corrispondente.
	 * 
	 * Tr → Val TrP
	 * 
	 * @return Nodo dell'espressione (NodeExpr).
	 * @throws SyntacticException Se il token non è valido per un'espressione Tr.
	 */
	private NodeExpr parseTr() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	        	// Tr Val TrP { ID, FLOAT, INT}
	            case ID, FLOAT, INT:
	            	NodeExpr val = parseVal();
					NodeExpr trP = parseTrP(val);
					return trP;

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in Tr.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}
	
	/**
	 * Analizza un'espressione TrP (termine con operazioni) e restituisce il nodo corrispondente.
	 * 
	 * TrP → /Val TrP
	 * TrP → *Val TrP
	 * TrP → ϵ
	 * 
	 * @param left Nodo sinistro dell'espressione.
	 * @return Nodo dell'espressione (NodeExpr).
	 * @throws SyntacticException Se il token non è valido per un'espressione TrP.
	 */
	private NodeExpr parseTrP(NodeExpr left) throws SyntacticException {
	    Token tk;
	    
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	        	// *Val TrP { TIMES}
	            case TIMES:
	            	match(TokenType.TIMES);
	            	NodeExpr val = parseVal();
	    			return parseTrP(new NodeBinOp(left, LangOper.TIMES, val));
	    		// /Val TrP { DIVIDE}
	            case DIVIDE:
	            	match(TokenType.DIVIDE);
	            	NodeExpr val1 = parseVal();
	    			return parseTrP(new NodeBinOp(left, LangOper.DIVIDE, val1));
	    		// ϵ { MINUS, PLUS, SEMI}
	            case PLUS, MINUS, SEMI:
	                return left;

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in TrP.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}
	
	/**
	 * Analizza un tipo (TYFLOAT o TYINT) e restituisce il tipo corrispondente.
	 * 
	 * Ty → float
	 * Ty → int
	 * 
	 * @return Tipo del linguaggio (LangType).
	 * @throws SyntacticException Se il token non è un tipo valido.
	 */
	private LangType parseTy() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	        	// Ty float { TYFLOAT }
	            case TYFLOAT:
	                match(TokenType.TYFLOAT);
	                return LangType.FLOAT;
	            // Ty int { TYINT }
	            case TYINT:
	                match(TokenType.TYINT);
	                return LangType.INT;

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in Ty.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}

	/**
	 * Analizza un valore (INT, FLOAT o ID) e restituisce il nodo corrispondente.
	 * 
	 * Val → intVal | floatVal | id
	 * 
	 * @return Nodo del valore (NodeExpr).
	 * @throws SyntacticException Se il token non è un valore valido.
	 */
	private NodeExpr parseVal() throws SyntacticException {
	    Token tk;
	    try {
	        tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	        	// Val intVal { INT }
	            case INT:
	            	if(log) {
		                System.out.println("Riconosciuto INT: " + tk.getVal());
	            	}
	                return new NodeCost(match(TokenType.INT).getVal(), LangType.INT);
	            // Val floatVal { FLOAT}
	            case FLOAT:
	            	if(log) {
		                System.out.println("Riconosciuto FLOAT: " + tk.getVal());
	            	}
	                return new NodeCost(match(TokenType.FLOAT).getVal(), LangType.FLOAT);
	            // Val id {ID}
	            case ID:
	            	if(log) {
		                System.out.println("Riconosciuto ID: " + tk.getVal());
	            	}
	                return new NodeDeref(new NodeId(match(TokenType.ID).getVal()));

	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in Val.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}
	
	/**
	 * Analizza un operatore (ASSIGN o OP_ASSIGN) e restituisce il token corrispondente.
	 * 
	 * Op → ASSIGN
	 * Op → OP_ASSIGN
	 * 
	 * @return Token dell'operatore.
	 * @throws SyntacticException Se il token non è un operatore valido.
	 */
	private Token parseOp() throws SyntacticException {
	    try {
	        Token tk = scanner.peekToken();

	        switch (tk.getTipo()) {
	        	// Op = {ASSIGN}
	            case ASSIGN:
	                return match(TokenType.ASSIGN);
	            // Op opAss {OP_ASSIGN}
	            case OP_ASSIGN:
	                return match(TokenType.OP_ASSIGN);
	                
	            default:
	                throw new SyntacticException("token " + tk.getTipo() + " alla riga " + tk.getRiga() + " non valido in Op.");
	        }
	    } catch (LexicalException le) {
	        throw new SyntacticException("Lexical Exception", le);
	    }
	}

	
}
