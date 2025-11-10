package ast;

import visitor.IVisitor;

/**
 * Rappresenta un nodo identificatore nell'AST.
 * 
 * extends {@link NodeAST}
 * @author Marco Yuri Papa
 */
public class NodeId extends NodeAST {
	String name;

	/**
     * Costruttore della classe NodeId.
     * 
     * @param name Nome dell'identificatore.
     */
	public NodeId(String name) {
		this.name = name;
	}
	
	/**
     * Restituisce il nome dell'identificatore.
     * 
     * @return Nome dell'identificatore.
     */
	public String getName() {
		return name;
	}

	/**
     * toString del nodo.
     * 
     * @return Il nome dell'identificatore.
     */
	@Override
	public String toString() {
		return name;
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