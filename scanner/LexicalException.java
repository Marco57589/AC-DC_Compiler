package scanner;

import java.io.IOException;

/**
 * Definizione LexicalException per gli errori lessicali
 * 
 * extends {@link Exception},
 * @author Marco Yuri Papa 20051241
 */
public class LexicalException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore "LexicalException" da IoException
     * @param message Eccezzione i/o
     */
    public LexicalException(IOException e) {
        super(e);
    }

    /**
     * Costruttore "LexicalException" con un messaggio specifico.
     * @param message Messaggio dell'errore
     */
    public LexicalException(String message) {
        super(message);
    }
}
