package visitor;

import ast.*;
import symbolTable.Attributes;
import symbolTable.SymbolTable;
import symbolTable.TypeDescriptor;

/**
 * Visitor per il type checking dell'AST.
 * 
 * Verifica la correttezza dei tipi, usa una SymbolTable per memorizzare e recuperare 
 * i tipi delle variabili.
 * 
 *  implements {@link IVisitor}
 *  @author Marco Yuri Papa 20051241
 */
public class TypeCheckingVisitor implements IVisitor {
	private TypeDescriptor resType;
	private boolean log; //true attivi, false disattivi

	/**
     * Restituisce il risultato del type checking.
     * 
     * @return Il TypeDescriptor risultante.
     */
	public TypeDescriptor getResType() {
		return resType;
	}

	/**
     * Costruttore senza logging.
     */
	public TypeCheckingVisitor() {
	    this(false);
	}

	 /**
     * Costruttore con parametro per il log.
     * 
     * @param log TRUE: abilitato FALSE altrimenti.
     */
	public TypeCheckingVisitor(boolean log) {
	    SymbolTable.init();  // inizializza la symboltable
	    this.log = log;
	}

	/**
	 * Visita un nodo di assegnamento (NodeAssign).
	 * - Verifica la compatibilità dei tipi nell'assegnamento (FLOAT-INT - INT-FLOAT),
	 * se i tipi sono incompatibili imposta resType come errore.
	 * 
	 * @param node Nodo di assegnamento da visitare.
	 */
	@Override
	public void visit(NodeAssign nodeAssign) {
		nodeAssign.getId().accept(this);
		TypeDescriptor idTD = resType; //restype aggiornato dopo l'accept dell'id

		nodeAssign.getExpr().accept(this);
		TypeDescriptor exprTD = resType; //restype aggiornato dopo l'accept dell'expr

		if (idTD.getTipo() == TipoTD.ERROR || exprTD.getTipo() == TipoTD.ERROR) { //se uno dei due tipi é error
			//allora assegno a resType idTp o exprTD in base al tipo di errore
			resType = (idTD.getTipo() == TipoTD.ERROR) ? idTD : exprTD; //se l'errore é idTd aasegno idTD, altrimenti exprTD
			return;
		}
		if (exprTD.compatibile(idTD)) { //compatibile
			resType = new TypeDescriptor(TipoTD.OK);
		} else { //non compatibile
			resType = new TypeDescriptor(TipoTD.ERROR, "tipi incopatibili "+idTD.getTipo() +" è diverso da"+ exprTD.getTipo()+"\n");
		}
		
		if(log) {
			System.out.println("log: NodeAssign - resType: " + resType.getTipo() + " - " + resType.getMsg());
		}
	}


	/**
	 * Visita un nodo di operazione binaria (NodeBinOp).
	 * Verifica i tipi degli operandi e determina il tipo risultante.
	 * 
	 * - Se gli operandi sono interi il risultato è intero (altrimenti float).
	 * - Se l'operazione è una divisione imposta DIV_FLOAT per operandi float.
	 * 
	 * @param nodeBinOp Nodo di operazione binaria da visitare.
	 */
	@Override
	public void visit(NodeBinOp nodeBinOp) {
	    nodeBinOp.getLeft().accept(this);
	    TypeDescriptor leftTD = resType;  // sx type

	    nodeBinOp.getRight().accept(this);
	    TypeDescriptor rightTD = resType;  // dx type

	    if (leftTD.getTipo() == TipoTD.ERROR) {
	        resType = leftTD;
	        return;
	    }

	    if (rightTD.getTipo() == TipoTD.ERROR) {
	        resType = rightTD;
	        return;
	    }

	    if (rightTD.getTipo() == TipoTD.INT && leftTD.getTipo() == TipoTD.INT) {
	        resType = new TypeDescriptor(TipoTD.INT);
	    } else {
	        resType = new TypeDescriptor(TipoTD.FLOAT);
	        if (nodeBinOp.getOp() == LangOper.DIVIDE) {
	            nodeBinOp.setOp(LangOper.DIV_FLOAT);
	        }
	    }
	    
	    if(log) {
			System.out.println("log: NodeBinOp - resType: " + resType.getTipo() + " - " + resType.getMsg());
		}
	}

	/**
	 * Visita un nodo di costante (NodeCost).
	 * - Restituisce il tipo della costante (INT o FLOAT).
	 * 
	 * @param node Nodo di costante da visitare.
	 */
	@Override
	public void visit(NodeCost nodeCost) {
		switch (nodeCost.getType()) {
			case FLOAT:
				resType = new TypeDescriptor(TipoTD.FLOAT);
				break;
			
			case INT:
				resType = new TypeDescriptor(TipoTD.INT);
				break;
		}
	}

	/**
	 * Visita un nodo di dichiarazione (NodeDecl).
	 * - Verifica se la variabile è già dichiarata nella symbolTable.
	 * - Se é presente un'inizializzazione, verifica la compatibilità dei tipi.
	 * - Se corretto imposta resType a OK.
	 * 
	 * @param nodeDecl Nodo di dichiarazione da visitare.
	 */
	@Override
	public void visit(NodeDecl nodeDecl) {
		
		if (SymbolTable.lookup(nodeDecl.getId().getName()) != null) {
			resType = new TypeDescriptor(TipoTD.ERROR, "La seguente variabile ["+nodeDecl.getId().getName() + "] é già stata dichiarata.");
			return;
		}

		SymbolTable.enter(nodeDecl.getId().getName(), new Attributes(nodeDecl.getType(), nodeDecl.getId().getName(), ' '));

		TypeDescriptor idTD;
		switch (nodeDecl.getType()) {
			case INT:
				idTD = new TypeDescriptor(TipoTD.INT);
				break;
			case FLOAT:
				idTD = new TypeDescriptor(TipoTD.FLOAT);
				break;
			default:
				idTD = new TypeDescriptor(TipoTD.ERROR);
				break;
		}

		if (nodeDecl.getInit() != null) {
			nodeDecl.getInit().accept(this);
			TypeDescriptor exprTD = resType;

			if (exprTD.compatibile(idTD)) {
				resType = new TypeDescriptor(TipoTD.OK);
			} else {
				resType = new TypeDescriptor(TipoTD.ERROR, "Tipi incompatibili tra " + idTD.getTipo() + " e " + exprTD.getTipo());
			}
			
		} else {
			resType = new TypeDescriptor(TipoTD.OK);
		}
		
		if(log) {
			System.out.println("log: NodeDecl - resType: " + resType.getTipo() + " - " + resType.getMsg());
		}
	}

	/**
	 * Visita un nodo de-referenziazione (NodeDeref).
	 * - Verifica il tipo della variabile dereferenziata, se il tipo non è INT o FLOAT, 
	 * imposta resType come errore.
	 * 
	 * @param nodeDeref Nodo di dereferenziazione da visitare.
	 */
	@Override
	public void visit(NodeDeref nodeDeref) {
		nodeDeref.getId().accept(this);
		TypeDescriptor idTD = resType;

		if (idTD.getTipo() == TipoTD.ERROR) {
			resType = idTD;

		} else if (idTD.getTipo() != TipoTD.INT && idTD.getTipo() != TipoTD.FLOAT) {
			resType = new TypeDescriptor(TipoTD.ERROR, "Errore deref" + idTD.getTipo());
		} 
		else {
			resType = idTD;
		}
		
		if(log) {
			System.out.println("log: NodeDeref - resType: " + resType.getTipo() + " - " + resType.getMsg());
		}
	}

	/**
	 * Visita un nodo identificatore (NodeId).
	 * - Recupera il tipo della variabile dalla symbol table e verifica il tipo, se la variabile non è dichiarata
	 * imposta resType come errore.
	 * 
	 * @param nodeId Nodo di identificatore da visitare.
	 */
	@Override
	public void visit(NodeId nodeId) {
		Attributes attr = SymbolTable.lookup(nodeId.getName());

		if (attr == null) {
			resType = new TypeDescriptor(TipoTD.ERROR, "La seguente variabile non è stata dichiarata [" + nodeId.getName()+"]");
		
		}else {
			switch (attr.getTipo()) {
				case INT:
					resType = new TypeDescriptor(TipoTD.INT);
					break;
				case FLOAT:
					resType = new TypeDescriptor(TipoTD.FLOAT);
					break;
				default:
					resType = new TypeDescriptor(TipoTD.ERROR, "Tipo non riconosciuto per " + nodeId.getName());
					break;
			}
		}
		
		if(log) {
			System.out.println("log: NodeId - resType: " + resType.getTipo() + " - " + resType.getMsg());
		}
	}

	/**
	 * Visita un nodo di print (NodePrint).
	 * Verifica il tipo della variabile da stampare, se il tipo è errato
	 * imposta resType come errore.
	 * 
	 * @param nodePrint Nodo di stampa da visitare.
	 */
	@Override
	public void visit(NodePrint nodePrint) {
		nodePrint.getId().accept(this);

		if (resType.getTipo() == TipoTD.ERROR) {
			if (resType.getMsg() == null || resType.getMsg().isEmpty()) { //problematica "sovrascrivimento" dell'errore
				resType = new TypeDescriptor(TipoTD.ERROR, "valore di tipo errato.");
			}
		} else {
			resType = new TypeDescriptor(TipoTD.OK);
		}
		
		if(log) {
			System.out.println("log: NodePrint - resType: " + resType.getTipo() + " - " + resType.getMsg());
		}
	}

	/**
	 * Visita un nodo prg (NodeProgram).
	 * - Verifica tutte le dichiarazioni e istruzioni del programma, se viene rilevato un errore
	 * interrompe l'analisi.
	 * 
	 * @param nodeProgram Nodo di programma da visitare.
	 */
	@Override
	public void visit(NodeProgram nodeProgram) {
		for (NodeDecSt decSt : nodeProgram.getDecSts()) {
			decSt.accept(this);
			if (resType.getTipo() == TipoTD.ERROR) {
				return;
			}
		}
		
		if(log) {
			System.out.println("log: NodeProgram - resType: " + resType.getTipo() + " - " + resType.getMsg());
		}
	}
}
