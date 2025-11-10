package ast;

import visitor.IVisitor;

/**
 * Rappresenta un nodo di stampa (print) dell'AST.
 * 
 * extends {@link NodeStm}
 * @author Marco Yuri Papa
 */
public class NodePrint extends NodeStm {
	NodeId id;

	/**
     * Costruttore della classe NodePrint.
     * 
     * @param id Identificatore della variabile da stampare.
     */
	public NodePrint(NodeId id) {
		this.id = id;
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
     * toString del nodePrint.
     * 
     * @return Una stringa che descrive il NodePrint.
     */
	@Override
	public String toString() {
		return "NodePrint [id:" + id + "]";
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
