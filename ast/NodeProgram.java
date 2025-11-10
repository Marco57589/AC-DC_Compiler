package ast;

import java.util.ArrayList;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo di programma nell'AST.
 * 
 * extends {@link NodeAST}
 * @author Marco Yuri Papa 20051241
 */
public class NodeProgram extends NodeAST {
	ArrayList<NodeDecSt> decSts;

	/**
     * Costruttore per creare un nodo di programma.
     * 
     * @param decSts La lista di dichiarazioni e istruzioni.
     */
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}

	/**
     * Restituisce la lista di dichiarazioni e istruzioni.
     * 
     * @return La lista di dichiarazioni e istruzioni (ArrayList<NodeDecSt>).
     */
	public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}

	/**
     * toString del nodeProgram.
     * 
     * @return Una stringa che descrive il nodo di programma.
     */
	@Override
	public String toString() {
		return "NodeProgram [decSts=" + decSts.toString() + "]";
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