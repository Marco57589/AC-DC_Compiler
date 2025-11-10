package ast;

import visitor.IVisitor;

/**
 * Rappresenta un nodo costante nell'AST.
 * 
 * extends {@link NodeExpr}
 * @author Marco Yuri Papa
 */
public class NodeCost extends NodeExpr {
	String value;
	LangType type;

	/**
     * Costruttore della classe NodeCost.
     * 
     * @param value Valore della costante.
     * @param type Tipo della costante.
     */
	public NodeCost(String value, LangType type) {
		this.value = value;
		this.type = type;
	}

	/**
     * Restituisce il valore del nodo.
     * 
     * @return Valore del nodo.
     */
	public String getValue() {
		return value;
	}

	/**
     * Restituisce il tipo della costante.
     * 
     * @return Il tipo della costante.
     */
	public LangType getType() {
		return type;
	}

	/**
     * toString del nodeCost.
     * 
     * @return Una stringa che descrive il NodeCost.
     */
	@Override
	public String toString() {
		return "NodeCost [value:" + value + ", type:" + type + "]";
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