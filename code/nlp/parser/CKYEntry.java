// Aidan Wu : Assignment 4

package nlp.parser;

import java.util.HashMap;

/**
 * A wrapper class around HashMap<String, ParseTree> representing a CKY table entry.
 * Keys are ParseTree node labels. Values are the ParseTrees themselves.
 * 
 * @author Aidan Wu
 * @version 2/28/2023
 *
 */

public class CKYEntry extends HashMap<String, ParseTree> {
    /**
     * Constructs a new empty CKYEntry
     */
    public CKYEntry() {
        super();
    }

    /**
     * Add a ParseTree to the entry
     * 
     * @param pt the ParseTree to add
     */
    public void add(ParseTree pt) {
        super.put(pt.getLabel(), pt);
    }
}
