package ast;

import visitor.IVisitor;

/**
 * Rappresenta un nodo di dichiarazione di variabile nell'AST.
 * 
 * extends {@link NodeDecSt}
 * @author Marco Yuri Papa
 */
public class NodeDecl extends NodeDecSt {
	
	private NodeId id;
	private LangType type;
	private NodeExpr init;

	/**
     * Costruttore della classe NodeDecl.
     * 
     * @param id Identificatore della variabile dichiarata.
     * @param type Tipo della variabile.
     * @param init Espressione di inizializzazione.
     */
	public NodeDecl(NodeId id, LangType type, NodeExpr init) {
		this.id = id;
		this.type = type;
		this.init = init;
	}

	/**
     * Restituisce l'identificatore della variabile dichiarata.
     * 
     * @return L'identitificatore della variabile.
     */
	public NodeId getId() {
		return id;
	}

	/**
     * Restituisce il tipo della variabile dichiarata.
     * 
     * @return Il tipo della variabile.
     */
	public LangType getType() {
		return type;
	}

	/**
     * Restituisce l'espressione di inizializzazione della variabile.
     * 
     * @return L'espressione di init della variabile.
     */
	public NodeExpr getInit() {
		return init;
	}

	/**
     * toString del nodeDecl.
     * 
     * @return Una stringa che descrive il NodeDecl.
     */
	@Override
	public String toString() {
		return "NodeDecl [id:" + id + ", type:" + type + ", init:" + init + "]";
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