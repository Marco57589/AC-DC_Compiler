package symbolTable;

import java.io.IOException;

/**
 * Definizione CodeGeneratorException per gli errori nella generazione del codice
 * 
 * extends {@link Exception},
 * @author Marco Yuri Papa 20051241
 */
public class CodeGeneratorException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore "CodeGeneratorException" da IoException
     * @param message Eccezzione i/o
     */
    public CodeGeneratorException(IOException e) {
        super(e);
    }

    /**
     * Costruttore "CodeGeneratorException" con un messaggio specifico.
     * @param message Messaggio dell'errore
     */
    public CodeGeneratorException(String message) {
        super(message);
    }
}
