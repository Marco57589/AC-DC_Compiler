package symbolTable;
import java.util.HashMap;
import java.util.Map.Entry;


/**
 * Classe che definisce una SymbolTable.
 * 
 * Usiamo un HashMap per salvere gli identificatori e i loro valori.
 * 
 *  @author Marco Yuri Papa 20051241
 */
public class SymbolTable {

	// 2/5
	private static HashMap<String, Attributes> st;

	/**
     * Inizializza la SymbolTable, creando una nuova HashMap vuota.
     */
	public static void init() {
		st = new HashMap<>();
	}

	/**
     * Inserisce un id e i suoi attributi nella SymbolTable.
     * 
     * @param id L'identificatore da inserire.
     * @param entry Gli attributi associati all'identificatore.
     * @return True se l'inserimento ha successo, false se l'identificatore è già presente.
     */
	public static boolean enter(String id, Attributes entry) {
		if(!st.containsKey(id)) {
			st.put(id, entry);
			return true;
		}else {
			return false;
		}
	}

	/**
     * Ricerca un identificatore nella SymbolTable.
     * 
     * @param id L'identificatore da cercare.
     * @return Gli attributi associati all'identificatore, o null se non trovato.
     */
	public static Attributes lookup(String id) {
		return st.get(id);
	}

	/**
     * To String della SymbolTable.
     * 
     * @return Una stringa che descrive il contenuto della SymbolTable.
     */
	public static String toStr() {
		String res = "";
		for (Entry<String, Attributes> i : st.entrySet()) {
			res += "[Key: "+i.getKey()+" Tipo: "+i.getValue().getTipo()+"]\n";
		}
		return res;
	}

	/**
     * Restituisce il numero di elementi nella SymbolTable.
     * 
     * @return Numero elementi nella SymbolTable.
     */
	public static int size() {
		return st.size();
	}
}
