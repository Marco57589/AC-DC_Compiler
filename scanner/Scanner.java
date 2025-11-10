package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import token.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Definizione dello scanner per l'analisi lessicale.
 * Lo scanner legge un file di input e genera token a partire dal contenuto.
 * 
 * @author Marco Yuri Papa 20051241
 */
public class Scanner {
	
	final char EOF = (char) -1; 
	private int riga;
	private PushbackReader buffer;
	private Token nextTk;
	private boolean log;
	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	// letters: insieme lettere 
	// digits: cifre 

	/*
		skip={' ','\n','\t','\r'}
		eof=(char)-1
		op={'+','-’,’*’,’/’}
		digits={‘0’,'1',…,’9’}
		letters={'a','b',…,'z’}
	 */

	private Set<Character> skpChars; //\n \t \r ' '
	private Set<Character> letters; //character a-z+A-Z
	private Set<Integer> digits; //digits 0-9

	private HashMap<Character, TokenType> operTkType; // operTkType: mapping fra caratteri '+', '-', '*', '/'  e il TokenType corrispondente
	private HashMap<Character, TokenType> delimTkType; // delimTkType: mapping fra caratteri '=', ';' e il e il TokenType corrispondente
	private HashMap<String, TokenType> keyWordsTkType; // keyWordsTkType: mapping fra le stringhe "print", "float", "int" e il TokenType  corrispondente

	/**
	 * Costruttore scanner che inizializza il lettore e le strutture dati.
	 * 
	 * @param fileName Nome del file da leggere
	 * @throws FileNotFoundException Se il file non viene trovato
	 */
	public Scanner(String fileName) throws FileNotFoundException {
		this(fileName, false);  // Chiama il costruttore con il parametro log impostato su false
	}

	/**
	 * Costruttore dello scanner che inizializza il lettore e le strutture dati.
	 * 
	 * @param fileName Nome del file da leggere
	 * @param log Flag per abilitare i log di debug (stampe dei passaggi)
	 * @throws FileNotFoundException Se il file non viene trovato
	 */
	public Scanner(String fileName, boolean log) throws FileNotFoundException {
		this.buffer = new PushbackReader(new FileReader(fileName));
		this.log = log;  // Imposta il valore di log
		riga = 1;

		skpChars = new HashSet<>();
		letters = new HashSet<>();
		digits = new HashSet<>();

		skpChars.add(' ');
		skpChars.add('\n');
		skpChars.add('\t');
		skpChars.add('\r');

		for (int i = 0; i < 10; i++) {  // digits 0-9
			digits.add(i);
		}

		for (char c = 'a'; c <= 'z'; c++) {  // character a-z
			letters.add(c);
		}
		for (char c = 'A'; c <= 'Z'; c++) {  // character A-Z
			letters.add(c);
		}

		operTkType = new HashMap<>();
		delimTkType = new HashMap<>();
		keyWordsTkType = new HashMap<>();

		// '+', '-', '*', '/'
		operTkType.put('+', TokenType.PLUS);
		operTkType.put('-', TokenType.MINUS);
		operTkType.put('*', TokenType.TIMES);
		operTkType.put('/', TokenType.DIVIDE);

		// '=', ';'
		delimTkType.put(';', TokenType.SEMI);
		delimTkType.put('=', TokenType.ASSIGN);

		// "print", "float", "int"
		keyWordsTkType.put("print", TokenType.PRINT);
		keyWordsTkType.put("float", TokenType.TYFLOAT);
		keyWordsTkType.put("int", TokenType.TYINT);
	}

	/**
	 * Restituisce il prossimo token senza consumarlo.
	 * 
	 * @return Il prossimo token
	 * @throws LexicalException Se si verifica un errore lessicale
	 */
	public Token peekToken() throws LexicalException {
		if(nextTk == null) {
			nextTk = nextToken();
			return nextTk;
		}else {
			return nextTk;
		}
	}

	/**
	 * Consuma e restituisce il prossimo token.
	 * 
	 * @return Il prossimo token
	 * @throws LexicalException Se si verifica un errore lessicale
	 */
	public Token nextToken() throws LexicalException {
		try {
			if (nextTk != null) {
				Token token = nextTk;
				nextTk = null;
				if(log) {
					System.out.println(token);
				}
				return token;
			}

			char nextChar = peekChar();
			//System.out.println("Carattere letto: '" + nextChar + "'");

			if (nextChar == EOF) {
				//System.out.println("EOF trovato");
				return new Token(TokenType.EOF, riga); // EOF trovato, ritorna il token EOF
			} else {
				nextChar = readChar();
			}

			while (skpChars.contains(nextChar)) {
				//System.out.println("Carattere di skip trovato: '" + nextChar + "'");
				nextChar = readChar();
				if (nextChar == '\n') {
					riga++;
				}
			}

			if (nextChar == EOF) {
				//System.out.println("EOF trovato durante lo skip");
				return new Token(TokenType.EOF, riga);
			}

			if (letters.contains(nextChar)) {
				//System.out.println("Lettera trovata: '" + nextChar + "'");
				return scanId(nextChar);
			}

			if (operTkType.containsKey(nextChar)) {
				//System.out.println("Operatore trovato: '" + nextChar + "'");
				return scanOperator(nextChar);
			}

			if (delimTkType.containsKey(nextChar)) {
				//System.out.println("Delimitatore trovato: '" + nextChar + "'");
				return new Token(delimTkType.get(nextChar), riga, Character.toString(nextChar));
			}

			if (digits.contains(Character.getNumericValue(nextChar))) {
				//System.out.println("Numero trovato: '" + nextChar + "'");
				return scanNumber(nextChar);
			}

			System.out.println("Carattere non valido trovato: '" + nextChar + "' (Unicode: " + (int) nextChar + ")");
			throw new LexicalException("Carattere non valido alla riga " + riga + ": " + nextChar);

		} catch (IOException e) {
			throw new LexicalException("Errore I/O alla riga " + riga + ": " + e.getMessage());
		}
	}


	/**
	 * Scansiona un identificatore a partire dal primo carattere.
	 * 
	 * @param firstChar Il primo carattere dell'identificatore
	 * @return Il token identificatore
	 * @throws IOException Se si verifica un errore di I/O
	 */
	private Token scanId(char firstChar) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(firstChar);

		while (true) {
			char nextChar = peekChar();

			if (letters.contains(nextChar) || Character.isDigit(nextChar)) {
				sb.append(readChar());
			} else {
				break;
			}
		}

		String s = sb.toString();

		if (keyWordsTkType.containsKey(s)) {
			return new Token(keyWordsTkType.get(s), riga, s);
		}

		return new Token(TokenType.ID, riga, s);
	}

	/**
	 * Scansiona un operatore a partire dal primo carattere.
	 * Gestisce anche gli opAssign (+=, -=).
	 * 
	 * @param firstChar Il primo carattere dell'operatore
	 * @return Il token operatore
	 * @throws LexicalException Se si verifica un errore lessicale
	 */
	private Token scanOperator(char firstChar) throws LexicalException {
		try {
			char nextChar = peekChar();
			if (nextChar == '=') { //se il prossimo valore é un uguale (e il firsChar é un operatore)
				//allora é un opAssign
				readChar();
				switch (firstChar) { //definisco l'opAssign in base al carattere
					case '+': 
						return new Token(TokenType.OP_ASSIGN, riga, "+=");
					case '-': 
						return new Token(TokenType.OP_ASSIGN, riga, "-=");
					case '*': 
						return new Token(TokenType.OP_ASSIGN, riga, "*=");
					case '/': 
						return new Token(TokenType.OP_ASSIGN, riga, "/=");
				}
			}
			return new Token(operTkType.get(firstChar), riga, Character.toString(firstChar));

		} catch (IOException e) {
			throw new LexicalException("Operatore sconosciuto -> '"+firstChar+"' <- nella riga "+riga);
		}
	}

	/**
	 * Scansiona un numero a partire dal primo carattere.
	 * Gestisce interi e floatingPoint
	 * 
	 * @param firstChar Il primo carattere del numero
	 * @return Il token numero
	 * @throws LexicalException Se si verifica un errore lessicale
	 */
	private Token scanNumber(char firstChar) throws LexicalException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(firstChar);
			boolean isFloat = false;

			char nextChar = peekChar();

			while (Character.isDigit(nextChar)) {
				sb.append(readChar());
				nextChar = peekChar();
			}

			//verifico che nexChar sia un punto decimale
			if (nextChar == '.') {
				isFloat = true;
				sb.append(readChar());
				nextChar = peekChar();

				//Funzionalità aggiuntive: controllo che ci siano cifre dopo il punto
				if (!Character.isDigit(nextChar)) {
					sb.append('0'); // Se non ci sono, aggiunge uno '0' (es. "98." -> "98.0")
				} else {
					while (Character.isDigit(nextChar)) {
						sb.append(readChar());
						nextChar = peekChar();
					}
				}
			}
			
			// Controllo un'altra volta per un eventuale secondo punto decimale
			if (nextChar == '.') {	
				throw new LexicalException("Errore durante la lettura del numero: numero con più di un punto decimale alla riga " + riga);
			}

			String numero = sb.toString();
			if (isFloat) {
				return new Token(TokenType.FLOAT, riga, numero); // Numero float
			} else {
				return new Token(TokenType.INT, riga, numero); // Numero intero
			}

		} catch (IOException e) {
			throw new LexicalException("Errore durante la lettura del numero: " + e.getMessage());
		}
	}

	/**
	 * Consuma il prossimo carattere.
	 * 
	 * @return Carattere letto
	 * @throws IOException Se si verifica un errore di I/O
	 */
	private char readChar() throws IOException {
		char c = (char) buffer.read();
		return c;
	}

	/**
	 * Legge il prossimo carattere dal flusso di input senza consumarlo.
	 * 
	 * @return Carattere successivo
	 * @throws IOException Se si verifica un errore di I/O
	 */
	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}

}
