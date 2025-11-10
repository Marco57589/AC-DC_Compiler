package token;

/**
 * Classe che definisce un "Token" nel contesto di uno scanner o di un parser.
 * 
 * @author Marco Yuri Papa 20051241
 */
public class Token {

	/** Riga del token nel codice. */
	private int riga;

	/** Tipo del token (definito nell'enum TokenType). */
	private TokenType tipo;

	/** Valore del token. */
	private String val;

	/**
	 * Costruttore principale per creare un token con valore.
	 * @param tipo Tipo del token
	 * @param riga Riga in cui si trova il token
	 * @param val Valore del token
	 */
	public Token(TokenType tipo, int riga, String val) {
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}

	/**
	 * Costruttore per creare un token senza valore.
	 * @param tipo Tipo del token
	 * @param riga Riga in cui si trova il token
	 */
	public Token(TokenType tipo, int riga) {
		this(tipo, riga, null);
	}

	/**
	 * La riga in cui si trova il token.
	 * @return Riga del token
	 */
	public int getRiga() {
		return riga;
	}

	/**
	 * Get tipo del token.
	 * @return Tipo del token
	 */
	public TokenType getTipo() {
		return tipo;
	}

	/**
	 * Get valore del token.
	 * @return Valore del token (può essere null)
	 */
	public String getVal() {
		return val;
	}

	/**
	 * Restituisce una rappresentazione testuale del token.
	 * @return Stringa rappresentante il token
	 */
	@Override
	public String toString() {
		return "Token: \n\t -riga:" + riga + "\n\t -tipo: " + tipo + ",\n\t -val: " + val;
	}
}