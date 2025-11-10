package visitor;

import ast.*;
import symbolTable.*;

/**
 * Classe che definisce un "CodeGeneratorVisitor" del codice in linguaggio 'dc'.
 * 
 * - Traduce i nodi dell'AST in istruzioni 'dc'.
 * - Utilizza registri (a-z) per memorizzare i valori delle variabili.
 * - Istruzioni supportate:
 *   - 'l': (load) carica il valore di un registro sullo stack.
 *   - 's': (store) memorizza il valore dallo stack in un registro.
 *   - 'p': (print) stampa il valore TOS senza rimuoverlo.
 *   - 'P': (pop) rimuove il valore in cima allo stack.
 *   - 'k': (komma aka precision) imposta la precisione per le operazioni floating-point.
 *   
 * implements {@link IVisitor}
 * @author Marco Yuri Papa 20051241
 */
public class CodeGeneratorVisitor implements IVisitor {

	private String codiceDc; // mantiene il codice della visita
	private String result; //risultato codeGeneartion

	/**
	 * Inizializza il visitor per la generazione del codice.
	 * - Resetta i registri disponibili con 'Registri.init()'.
	 * - Inizializza 'codiceDc' e 'result' come stringhe vuote.
	 */
	public CodeGeneratorVisitor() {
		Registri.init();
		codiceDc = "";
		result = "";
	}

	/**
	 * Restituisce il codice 'dc' per il nodo corrente.
	 * 
	 * @return Il codice generato del nodo in 'dc'.
	 */
	public String getCodiceDc() {
		return codiceDc;
	}

	/**
	 * Restituisce il codice generato.
	 * 
	 * @return Il codice completo in 'dc'.
	 */
	public String getResult() {
		return result;
	}

	//processamento dei nodi (tabella pag 8)

	/**
	 * Visita un nodo di dichiarazione (NodeDecl).
	 *
	 * - Assegna un nuovo registro alla variabile dichiarata.
	 * - Se è presente un'inizializzazione memorizza il valore nel registro con 's' (store).
	 * 
	 * @param nodeDecl Nodo di dichiarazione da visitare.
	 */
	@Override
	public void visit(NodeDecl nodeDecl) {
		Attributes a = SymbolTable.lookup(nodeDecl.getId().getName());
		char r = 0;

		try {
			r = Registri.newRegistro();
			a.setRegistro(r);

			if(nodeDecl.getInit() != null) {
				nodeDecl.getInit().accept(this);
				String init = codiceDc;
				nodeDecl.getId().accept(this);

				codiceDc = init+" s"+codiceDc;
			}
		} catch (CodeGeneratorException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Visita un nodo di assegnamento (NodeAssign).
	 *
	 * - Valuta l'espressione a destra e mette il risultato sullo stack.
	 * - Memorizza il valore nel registro della variabile con 's' (store).
	 * 
	 * @param nodeAssign Nodo di assegnamento da visitare.
	 */
	@Override
	public void visit(NodeAssign nodeAssign) {
		nodeAssign.getExpr().accept(this);
		String expr = codiceDc;
		nodeAssign.getId().accept(this);
		
		codiceDc = expr+" s"+codiceDc;
	}
	
	/**
	 * Visita un nodo di stampa (NodePrint).
	 *
	 * - Carica il valore dal registro allo stack usando 'l' (load) e il codiceDc.
	 * - Stampa il valore con 'p`' (p non rimuove dallo stack).
	 * - Esegue il pop del TOS (top of stack) con 'P'.
	 * 
	 * @param node Nodo di stampa da visitare.
	 */
	@Override
	public void visit(NodePrint nodePrint) {
		nodePrint.getId().accept(this);
		codiceDc = "l"+codiceDc+" p P";
	}

	/**
	 * Visita un nodo di operazione binaria (NodeBinOp).
	 *
	 * - Applica le operazioni e le salva in notazione polacca (opLeft, OpRight, operazione)
	 * - Per la divisione floating-point 'DIV_FLOAT', imposta la precisione con 'k'.
	 * 
	 * @param nodeBinOp Nodo di operazione binaria da visitare.
	 */
	@Override
	public void visit(NodeBinOp nodeBinOp) {
		nodeBinOp.getLeft().accept(this);
		String left = codiceDc;
		nodeBinOp.getRight().accept(this);
		String right = codiceDc;

		switch(nodeBinOp.getOp()) {
			case PLUS:
				codiceDc = left+" "+right+" +";
				break;
	
			case MINUS:
				codiceDc = left+" "+right+" -";
				break;
	
			case TIMES: 
				codiceDc = left+" "+right+" *";
				break;
	
			case DIVIDE:
				codiceDc = left+" "+right+" /";
				break;
	
			case DIV_FLOAT: //con le dovute modifiche se si tratta di divisione float.
				codiceDc = "5 k "+left+" "+right+" / 0 k"; //precisione impostata a 5k come da esempio slide
				break;
		}

	}

	/**
	 * Visita un nodo di dereferenziazione (NodeDeref).
	 *
	 * - Carica il valore dal registro sullo stack usando 'l' (load) e il codiceDc.
	 * 
	 * @param node Nodo di dereferenziazione da visitare.
	 */
	@Override
	public void visit(NodeDeref nodeDeref) {
		nodeDeref.getId().accept(this);
		codiceDc = "l"+codiceDc;	
	}

	/**
	 * Visita un nodo di costante (NodeCost).
	 *
	 * - Salva il valore della costante in 'codiceDc' senza caricarlo sullo stack
	 * 
	 * @param node Nodo di costante da visitare.
	 */
	@Override
	public void visit(NodeCost nodeCost) {
		codiceDc = nodeCost.getValue();
	}

	//---------

	/**
	 * Visita un nodo di identificatore (NodeId).
	 *
	 * - Carica il registro associato alla variabile sullo stack.
	 * 
	 * @param node Nodo di identificatore da visitare.
	 */
	@Override
	public void visit(NodeId nodeId) {
		codiceDc = String.valueOf(SymbolTable.lookup(nodeId.getName()).getRegistro());
	}


	/**
	 * Visita un nodo di programma (NodeProgram).
	 *
	 * - CodeGenerator per tutte le istruzioni del programma.
	 * - Salva in result il codice generato.
	 * 
	 * @param node Nodo di programma da visitare.
	 */
	@Override
	public void visit(NodeProgram nodeProgram) {
		for (NodeDecSt decSt : nodeProgram.getDecSts()) {
			decSt.accept(this);
			if(codiceDc != "") {
				result += codiceDc+" ";
				codiceDc = "";
			}			
		}
	}

}
