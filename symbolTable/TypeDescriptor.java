package symbolTable;

import ast.TipoTD;

/**
 * Classe TypeDescriptor per l'ast.
 * 
 * Contiene il Tipo (TipoTD) e un messaggio (opzionle) per errori o info.
 * 
 * @author Marco Yuri Papa 20051241
 */
public class TypeDescriptor {
	private TipoTD tipo;
	private String msg;

	/**
     * Costruttore TypeDescriptor senza messaggio.
     * 
     * @param tipo Il tipo da associare al TD.
     */
	public TypeDescriptor(TipoTD tipo){
		this.tipo = tipo;
	}

	/**
     * Costruttore TypeDescriptor con messaggio.
     * 
     * @param tipo Il tipo da associare al TD.
     * @param msg  Messaggio opzionale.
     */
	public TypeDescriptor(TipoTD tipo, String msg){
		this.tipo = tipo;
		this.msg = msg;
	}

	/**
	 * Restituisce il tipo del TD.
	 * 
	 * @return Il tipo (TipoTD).
	 */
	public TipoTD getTipo() {
		return tipo;
	}
	
	/**
     * Restituisce msg.
     * 
     * @return Il messaggio.
     */
	public String getMsg() {
		return msg;
	}

	/**
     * Verifica la compatibilità tra due tipi.
     * Un INT è compatibile con un FLOAT, ma non viceversa.
     * 
     * @param tD Il TypeDescriptor da confrontare.
     * @return True se i tipi sono compatibili, false altrimenti.
     */
	public boolean compatibile(TypeDescriptor tD) {
		if (this.getTipo() == TipoTD.FLOAT && tD.getTipo() == TipoTD.INT) {
			return false;
		}
		return true;
	}
}