package token;

/**
 * Enumeratore che definisce i tipi dei Token
 * 
 * @author Marco Yuri Papa 20051241
 */
public enum TokenType {	//(vedi tabella slide)
    // Costante/letterale
    INT,      // [0-9]+
    FLOAT,    // [0-9]+.([0-9]{0,5})

    // Identificatore
    ID,       // [a-z][a-z0-9]*

    // Parola chiave
    TYINT,    // int
    TYFLOAT,  // float
    PRINT,    // print

    // Operatori
    OP_ASSIGN, // +=, -=, *=, /=
    ASSIGN,    // =
    PLUS,      // +
    MINUS,     // -
    TIMES,     // *
    DIVIDE,    // /

    // Delimitatori
    SEMI,     // ;

    // Fine input
    EOF       // (char) -1
}