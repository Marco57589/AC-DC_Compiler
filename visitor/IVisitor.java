package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;

/**
 * Interfaccia per un visitor che visita i nodi dell'AST.
 * 
 * Ho scelto di utilizzare il pattern "Visitor" rispetto all'altro in quanto
 * ho ritenuto più "semplice" (anche più modulare) aggiungere metodi direttamente nei nodi dell'ast
 * rispetto al modificare le classi esistenti. (rendendo più semplice l'eventuale integrazioni di nuove funz.)
 *  
 *  @author Marco Yuri Papa 20051241
 */
public interface IVisitor {

	/**
	 * Visita un nodo di assegnamento (NodeAssign).
	 * 
	 * @param nodeAssign Nodo di assegnamento da visitare.
	 */
	public abstract void visit(NodeAssign nodeAssign);

	/**
	 * Visita un nodo di operazione binaria (NodeBinOp).
	 * 
	 * @param nodeBinOp Nodo di operazione binaria da visitare.
	 */
	public abstract void visit(NodeBinOp nodeBinOp);

	/**
	 * Visita un nodo di costante (NodeCost).
	 * 
	 * @param nodeCost Nodo di costante da visitare.
	 */
	public abstract void visit(NodeCost nodeCost);

	/**
	 * Visita un nodo di dichiarazione (NodeDecl).
	 * 
	 * @param nodeDecl Nodo di dichiarazione da visitare.
	 */
	public abstract void visit(NodeDecl nodeDecl);

	/**
	 * Visita un nodo di dereferenziazione (NodeDeref).
	 * 
	 * @param nodeDeref Nodo di dereferenziazione da visitare.
	 */
	public abstract void visit(NodeDeref nodeDeref);

	/**
	 * Visita un nodo di identificatore (NodeId).
	 * 
	 * @param nodeId Nodo di identificatore da visitare.
	 */
	public abstract void visit(NodeId nodeId);

	/**
	 * Visita un nodo di stampa (NodePrint).
	 * 
	 * @param nodePrint Nodo di stampa da visitare.
	 */
	public abstract void visit(NodePrint nodePrint);

	/**
	 * Visita un nodo di programma (NodeProgram).
	 * 
	 * @param nodeProgram Nodo di programma da visitare.
	 */
	public abstract void visit(NodeProgram nodeProgram);

}