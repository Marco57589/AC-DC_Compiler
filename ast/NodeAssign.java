package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo di assegnamento nell'AST.
 * 
 * extends {@link NodeStm}
 * @author Marco Yuri Papa 20051241
 */
public class NodeAssign extends NodeStm {
	NodeId id;
	NodeExpr expr;

	/**
     * Costruttore per creare un nodo di assegnamento.
     * 
     * @param id Identificatore a cui viene assegnato il valore.
     * @param expr L'espressione da assegnare.
     */
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	/**
     * Restituisce l'identificatore del nodo.
     * 
     * @return L'identificatore (NodeId).
     */
	public NodeId getId() {
		return id;
	}

	/**
     * Restituisce l'espressione del nodo.
     * 
     * @return L'espressione (NodeExpr).
     */
	public NodeExpr getExpr() {
		return expr;
	}

	/**
     * toString per rappresentazione il nodo con una stringa.
     * 
     * @return La striga del nodo.
     */
	@Override
	public String toString() {
		return "NodeAssign [id:" + id + ", expr:" + expr + "]";
	}

	/**
     * Implementazione 'accept' per supportare il Visitor.
     * 
     * @param visitor Il visitor che visita il nodo.
     */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
