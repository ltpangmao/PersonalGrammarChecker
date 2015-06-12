package it.unitn.grammar.parser;

import java.util.List;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphFactory;
import edu.stanford.nlp.semgraph.semgrex.SemgrexMatcher;
import edu.stanford.nlp.semgraph.semgrex.SemgrexPattern;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

public class Test {

	public static void main(String[] args) {
		String str = "A smart meter is an electrical device that records consumption of electrical energy and enables the measurement of energy in both directions";
		// String str = "This is a pure test.";
		ParserAPIs parser = new ParserAPIs();
		Tree tree = parser.parse(str);

//		List<Tree> leaves = tree.getLeaves();
		// Print words and Pos Tags
//		for (Tree leaf : leaves) {
//			Tree parent = leaf.parent(tree);
//			System.out.print(leaf.label().value() + "-"
//					+ parent.label().value() + " ");
//			System.out.println(leaf.depth());
//		}
		
//		System.out.println();
//		System.out.println(tree.nodeString());
//		System.out.println(tree.value());
//		System.out.println(tree.toString());
//		System.out.println(parser.getPartialSentence(tree));
		tree.pennPrint();

		
		

//		SemanticGraph graph = SemanticGraphFactory.generateUncollapsedDependencies(tree);
//		System.out.println(graph);		
//		TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
//		tp.printTree(tree);
		
//		TreebankLanguagePack tlp = lp.treebankLanguagePack(); // PennTreebankLanguagePack for English
//		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//		GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//		List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
//		System.out.println(tdl);
		   
		

//		SemgrexPattern semgrex = SemgrexPattern.compile("{}=A << {}=B");
//	    SemgrexMatcher matcher = semgrex.matcher(graph);

		
		TregexPattern tregex = TregexPattern.compile("/VB.?/=vb $+ (NP=np ?$+ PP=pp)");
		TregexMatcher matcher = tregex.matcher(tree);
		
		while (matcher.find()) {
//			System.err.println(matcher.getMatch().pennString());
//	    	System.err.println(matcher.getNode("test"));
	    	System.out.print(parser.getLeafWords(matcher.getNode("vb"))+" ");
	    	System.out.print(parser.getLeafWords(matcher.getNode("np"))+" ");
	    	if(matcher.getNode("pp")!=null){
	    		System.err.println("nima");
	    		System.out.print(parser.getLeafWords(matcher.getNode("pp"))+" ");
	    	}
	    	System.out.println();
	    }
	}

}
