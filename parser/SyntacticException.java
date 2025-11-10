package parser;

import java.io.IOException;

import scanner.LexicalException;

/**
 * Definizione SyntacticException per gli errori Sintattici
 * 
 * extends {@link Exception},
 * @author Marco Yuri Papa 20051241
 */
public class SyntacticException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore "SyntacticException" da IoException
     * @param message Eccezzione i/o
     */
    public SyntacticException(IOException e) {
        super(e);
    }

    /**
     * Costruttore "SyntacticException" con un messaggio specifico.
     * @param message Messaggio dell'errore
     */
    public SyntacticException(String message) {
        super(message);
    }
    
    /**
     * Costruttore "SyntacticException" con un messaggio specifico e l'errore LexicalException.
     * @param message Messaggio dell'errore
     * @param e Eccezzione LexicalException
     */
    public SyntacticException(String message, LexicalException e) {
        super(message, e);
    }
}
