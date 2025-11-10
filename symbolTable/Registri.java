package symbolTable;

import java.util.ArrayList;

/**
 * Classe per la gestione dei registri nel codeGenerator.
 * 
 *  @author Marco Yuri Papa 20051241
 */

public class Registri {

    //Lista dei registri disponibili (ident. da lettere)
	static ArrayList<Character> letters; //character a-z


	/**
     * Riempio/definisco la lista dei registri disponibili con dei caratteri da a-zA-Z.
     */
	public static void init() {
		letters = new ArrayList<Character>();
		//riempio la lista a-zA-Z con la stessa tecnica usata nello scanner
		for(char c = 'a'; c <= 'z'; c++) { // character a-z
			letters.add(c);
		}
		for (char c = 'A'; c <= 'Z'; c++) {  // character A-Z
			letters.add(c);
		}
	}
	
	/**
     * Assegno un nuovo registro dalla lista dei registri disponibili.
     *  (es: assegno a, dalla lista dei disponibili rimuovo a (rimangono dalla b-z) cosi via...
     * 
     * @return Il registro assegnatos.
     * @throws CodeGeneratorException Se non ci sono più registri disponibili.
     */
	public static char newRegistro() throws CodeGeneratorException {
		if(letters.isEmpty() || letters.size()==0) //se la lista delle lettere (Dei registri) é vuota
			throw new CodeGeneratorException("Numero massimo di registri raggiunto.");
		return letters.remove(0);
	}
	
}