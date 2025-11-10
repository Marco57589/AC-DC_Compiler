package ast;

import visitor.IVisitor;

/**
 * Rappresenta un'operazione di dereferenziazione nell'AST.
 * 
 * extends {@link NodeExpr}
 * @author Marco Yuri Papa
 */
public class NodeDeref extends NodeExpr {
	private NodeId id;

	/**
     * Costruttore della classe NodeDeref.
     * 
     * @param id Identificatore della variabile da dereferenziare.
     */
	public NodeDeref(NodeId id) {
		this.id = id;
	}

	/**
     * Restituisce l'identificatore della variabile.
     * 
     * @return L'identificatore della variabile.
     */
	public NodeId getId() {
		return id;
	}

	/**
     * toString del nodeDeref.
     * 
     * @return Una stringa che descrive il nodo di dereferenziazione.
     */
	@Override
	public String toString() {
		return "NodeDeref [id:" + id + "]";
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
