package ast;

import visitor.IVisitor;

/**
 * Classe astratta che rappresenta un nodo "generico" dell'AST.
 * 
 * @author Marco Yuri Papa 20051241
 */
public abstract class NodeAST {
	/**
    * Metodo astratto per accettare un visitor.
    * 
    * @param visitor Il visitor che visita il nodo.
    */
	public abstract void accept(IVisitor visitor); //esteso alle altre classi NodeAST
	//NodeDecST, NodeExpr, NodeSTM
}
