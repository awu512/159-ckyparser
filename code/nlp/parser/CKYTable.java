// Aidan Wu : Assignment 4

package nlp.parser;

import java.util.ArrayList;

/**
 * A class representing the table utilized 
 * in the CKY parsing algorithm.
 * 
 * @author Aidan Wu
 * @version 2/28/2023
 *
 */

public class CKYTable {
    private ArrayList<ArrayList<CKYEntry>> table;

    /**
     * Initializes a size by size CKYTable
     * 
     * @param size the width/height of the table
     */
    public CKYTable(int size) {
        this.table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<CKYEntry> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                if (i > j) {
                    row.add(null);
                } else {
                    row.add(new CKYEntry());
                }
            }
            table.add(row);
        }
    }

    /**
     * Adds a Parse tree to the CKYEntry at (x,y)
     * 
     * @param pt the ParseTree to add
     * @param x the x location to add at
     * @param y the y location to add at
     */
    public void addAt(ParseTree pt, int x, int y) {
        CKYEntry entry = getAt(x, y);
        if (entry == null) {
            entry = new CKYEntry();
        }

        entry.add(pt);
    }

    /**
     * Gets the CKYEntry at (x,y)
     * 
     * @param x the x location
     * @param y the y location
     * @return the CKYEntry at the specified location
     */
    public CKYEntry getAt(int x, int y) {
        return table.get(y).get(x);
    }

    /**
     * Returns the width/height of the table
     * 
     * @return the size of the table
     */
    public int size() {
        return table.size();
    }
}
