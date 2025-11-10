package ast;

import visitor.IVisitor;

/**
 * Rappresenta un nodo di operazione binaria nell'AST.
 * 
 * extends {@link NodeExpr}
 * @author Marco Yuri Papa
 */
public class NodeBinOp extends NodeExpr {
	private NodeExpr left;
	private LangOper op;
	private NodeExpr right;

	
	/**
     * Costruttore della classe NodeBinOp.
     * 
     * @param left Espressione a sinistra dell'operatore.
     * @param op Operatore binario.
     * @param right Espressione a destra dell'operatore.
     */
	public NodeBinOp(NodeExpr left, LangOper op, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	/**
     * Restituisce l'operatore binario.
     * 
     * @return L'operatore.
     */
	public LangOper getOp() {
		return op;
	}

	/**
     * Imposta un nuovo operatore binario.
     * 
     * @param op Nuovo operatore binario.
     */
	public void setOp(LangOper op) {
		this.op = op;
	}

	/**
     * Restituisce l'espressione a sinistra dell'operatore.
     * 
     * @return L'oggetto rappresentante l'operando sinistro.
     */
	public NodeExpr getLeft() {
		return left;
	}

	/**
     * Restituisce l'espressione a destra dell'operatore.
     * 
     * @return L'oggetto rappresentante l'operando destro.
     */
	public NodeExpr getRight() {
		return right;
	}

	/**
     * toString del nodeBinOp.
     * 
     * @return Una stringa che descrive il NodeBinOp.
     */
	@Override
	public String toString() {
		return "NodeBinOp [op:" + op + ", left:" + left + ", right:" + right + "]";
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
