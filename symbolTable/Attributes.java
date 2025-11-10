package symbolTable;

import ast.LangType;

/**
 * Classe che rappresenta gli attributi associati a un identificatore nella SymbolTable.
 * 
 *  @author Marco Yuri Papa 20051241
 */
public class Attributes{
	private LangType tipo;
	private String nome;
	private char registro;
	
	/**
     * Costruttore per creare un oggetto Attributes.
     * 
     * @param tipo Il tipo dell'identificatore.
     * @param nome Il nome dell'identificatore.
     * @param registro Il registro associato all'identificatore.
     */
	public Attributes(LangType tipo, String nome, char registro) {
		this.tipo = tipo;
		this.nome = nome;
		this.registro = registro;
	}

	/**
     * Restituisce il tipo dell'identificatore.
     * 
     * @return Il tipo.
     */
	public LangType getTipo() {
		return tipo;
	}

	/**
     * Restituisce il nome dell'identificatore.
     * 
     * @return Il nome.
     */
	public String getNome() {
		return nome;
	}

	/**
     * Restituisce il registro.
     * 
     * @return Il registro.
     */
	public char getRegistro() {
		return registro;
	}

	/**
     * Imposta il registro associato all'identificatore.
     * 
     * @param registro Il registro da associare.
     */
	public void setRegistro(char registro) {
		this.registro = registro;
	}

	/**
     * toString degli attributi.
     * 
     * @return Una stringa che rappresenta gli attributi.
     */
	@Override
	public String toString() {
		return "Attributes [tipo: "+tipo+", nome: '"+nome+"', registro: "+registro+"] \n";
	}
}