package it.unitn.grammar.parser;


import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class ParserAPIs {

    private final static String PCG_MODEL = "parser/models/englishPCFG.ser.gz";        

    private final TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");

    private final LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);

    /**
     * Parse input strings to lexical tree
     * @param str
     * @return
     */
    public Tree parse(String str) {                
        List<CoreLabel> tokens = tokenize(str);
        Tree tree = parser.apply(tokens);
        return tree;
    }

    
    private List<CoreLabel> tokenize(String str) {
        Tokenizer<CoreLabel> tokenizer =
            tokenizerFactory.getTokenizer(
                new StringReader(str));    
        return tokenizer.tokenize();
    }
    
    
    /**
     * Get a list a dependencies of the parsed lexical tree
     * @param tree
     * @return
     */
    public List<TypedDependency> getDependencies(Tree tree) {                
    	TreebankLanguagePack tlp = parser.treebankLanguagePack(); // PennTreebankLanguagePack for English
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
        List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
        return tdl;
    }
    
    
    /**
     * complement the Tree class to get corresponding words of a (sub-)tree
     * @param tree
     * @return
     */
    public String getLeafWords(Tree tree){
    	String sentence ="";
//    	if(tree.isLeaf()){
//    		if(tree.toString().matches("[a-zA-Z0-9]+")){
//    			sentence = " "+tree.toString();
//    		}
//    		else{
//    			sentence = tree.toString();
//    		}
//    	}
//    	else{
//    		for (Tree child: tree.children()){
//    			sentence += getLeafWords(child);
//    		}
//    	}
    	List<Tree> leaves = tree.getLeaves();
    	for (Tree leaf : leaves) { 
            if(!sentence.equals("") && leaf.label().value().matches("[a-zA-Z0-9]+")){
            	sentence += " "+leaf.label().value();
            }
            else{
            	sentence += leaf.label().value();
            }
    	}
    	return sentence;
    }
    

    public static void main(String[] args) { 
//        String str = "A smart meter is an electrical device that records consumption of electrical energy and enables the measurement of energy in both directions";
    	String str = "This is a pure test.";
        ParserAPIs parser = new ParserAPIs(); 
        Tree tree = parser.parse(str);  

        List<Tree> leaves = tree.getLeaves();
        // Print words and Pos Tags
        for (Tree leaf : leaves) { 
            Tree parent = leaf.parent(tree);
            System.out.print(leaf.label().value() + "-" + parent.label().value() + " ");
            System.out.println(leaf.depth());
            
        }
        System.out.println();
        
        System.out.println(tree.nodeString());
        System.out.println(tree.value());
        System.out.println(parser.getLeafWords(tree));
        tree.pennPrint();
        
        
        // other printers
//        tree.pennPrint();
//        TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
//        tp.printTree(tree);
        
        
    }
    
    
   
}