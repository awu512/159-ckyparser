// Aidan Wu : Assignment 4

package nlp.parser;

import java.util.ArrayList;

/**
 * A CKY parser.
 * 
 * @author Aidan Wu
 * @version 2/28/2023
 *
 */

public class CKYParser {
    private ArrayList<GrammarRule> lexRules;
    private ArrayList<GrammarRule> unaryRules;
    private ArrayList<GrammarRule> binRules;

    private ParseTree[] terminals;

    /**
     * Construct a new CKYParser from a grammar file
     * 
     * @param filename the path of the grammar file
     */
    public CKYParser(String filename) {
        // read grammar from file
        ArrayList<GrammarRule> allRules = GrammarRule.readFile(filename);

        // initalize rule lists
        lexRules = new ArrayList<>();
        unaryRules = new ArrayList<>();
        binRules = new ArrayList<>();

        // separate into three lists
        for (GrammarRule rule : allRules) {
            if (rule.isLexical()) {
                lexRules.add(rule);
            } else if (rule.numRhsElements() == 1) {
                unaryRules.add(rule);
            } else {
                binRules.add(rule);
            }
        }
    }

    /**
     * Parse a Sentence using the CKY algorithm
     * 
     * @param s the sentence to be parsed
     * @return the ParseTree of the sentence
     */
    public ParseTree parseSentence(Sentence s) {
        CKYTable table = new CKYTable(s.len()); // initialize the table

        // create terminal nodes
        terminals = new ParseTree[s.len()];
        for (int k = 0; k < s.len(); k++) {
            terminals[k] = new ParseTree(s.getAt(k), true, 0);
        }

        // initialize table position counters
        int x = 0;
        int y = 0;

        // iterate through diagonals
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.size() - i; j++) {
                CKYEntry entry = table.getAt(x, y);

                if (x == y) {
                    // initialize the main diagonal with lexical rules
                    for (GrammarRule rule : this.lexRules) {

                        // check if rule RHS is same as word at index x
                        if (rule.getRhs().get(0).equals(s.getAt(x))) { 

                            // create node for this rule
                            ParseTree pt = new ParseTree(rule.getLhs(), false, rule.getWeight());
                            pt.addChild(terminals[x]); // add terminal as child

                            // check if there is a higher weight rule with the same label in this node
                            if (
                                !(entry.containsKey(pt.getLabel())) || 
                                entry.get(pt.getLabel()).getWeight() < pt.getWeight()
                            ) {
                                entry.add(pt); // add to CKYEntry
                            }

                        }
                    }

                } else {

                    // iterate all valid left & below combinations
                    for (int c = y; c < x; c++) {
                        // get entries
                        CKYEntry left = table.getAt(c, y);
                        CKYEntry below = table.getAt(x, c+1);

                        // iterate through non-lexical rules
                        for (GrammarRule rule : this.binRules) {
                            
                            // get labels from this rule's RHS
                            String leftLabel = rule.getRhs().get(0);
                            String belowLabel = rule.getRhs().get(1);

                            // check if left and below entries correspond to RHS labels
                            if (left.containsKey(leftLabel) && below.containsKey(belowLabel)) {

                                // get left and below ParseTrees
                                ParseTree leftNode = left.get(leftLabel);
                                ParseTree belowNode = below.get(belowLabel);

                                // create node for this rule
                                ParseTree pt = new ParseTree(
                                    rule.getLhs(), 
                                    false, 
                                    rule.getWeight() + leftNode.getWeight() + belowNode.getWeight()
                                );

                                pt.addChild(leftNode); // add the left entry's rule as a child
                                pt.addChild(belowNode); // add the below entry's rule as a child

                                // check if there is a higher weight rule with the same label in this node
                                if (
                                    !(entry.containsKey(pt.getLabel())) || 
                                    entry.get(pt.getLabel()).getWeight() < pt.getWeight()
                                ) {
                                    entry.add(pt); // add to CKYEntry
                                }
                            }

                        }
                            
                    }
                }

                // iterate unary rules
                for (GrammarRule rule : this.unaryRules) {

                    String rhs = rule.getRhs().get(0); // get RHS of unary rule

                    // check if this entry has a node corresponding to the RHS
                    if (entry.containsKey(rhs)) {

                        // create node for this rule
                        ParseTree pt = new ParseTree(
                            rule.getLhs(), 
                            false, 
                            rule.getWeight() + entry.get(rhs).getWeight()
                        );

                        pt.addChild(entry.get(rhs)); // add the corresponding rule as a child

                        // check if there is a higher weight rule with the same label in this node
                        if (
                            !(entry.containsKey(pt.getLabel())) || 
                            entry.get(pt.getLabel()).getWeight() < pt.getWeight()
                        ) {
                            entry.add(pt); // add to CKYEntry
                        }

                    }

                }

                x++; y++; // move down diagonal
            }

            // move to the next diagonal
            x = table.size() - y + 1;
            y = 0;
        }

        return table.getAt(table.size()-1, 0).get("S");
    }

    public static void main(String[] args) {
        CKYParser parser = new CKYParser(args[0]);
        ArrayList<Sentence> sentences = Sentence.fromFile(args[1]);

        ArrayList<ParseTree> parsed = new ArrayList<>();
        
        for (Sentence s : sentences) {
            parsed.add(parser.parseSentence(s));
        }
        
        for (ParseTree pt : parsed) {
            if (pt == null) {
                System.out.println("NULL");
            } else {
                System.out.println(pt + "\t" + pt.getWeight());
            }
        }
    }
}
