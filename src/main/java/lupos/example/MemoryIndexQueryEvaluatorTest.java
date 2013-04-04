package lupos.example;

import java.util.Iterator;
import java.util.LinkedList;

import lupos.datastructures.bindings.Bindings;
import lupos.datastructures.items.Variable;
import lupos.datastructures.items.literal.LiteralFactory;
import lupos.datastructures.items.literal.URILiteral;
import lupos.datastructures.queryresult.QueryResult;
import lupos.engine.evaluators.MemoryIndexQueryEvaluator;

/**
 * This class just demonstrates a simple example using the luposdate query evaluator indexing data in main memory
 * by querying previously inserted data.
 */
public class MemoryIndexQueryEvaluatorTest {
	
	/**
	 * The main entry point: Will be executed when the program is running 
	 * @param args the command line arguments will be ignored
	 * @throws Exception in case of any errors
	 */
	public static void main(String[] args) throws Exception{
		// use a hash map as dictionary in main memory
		LiteralFactory.setType(LiteralFactory.MapType.HASHMAP);
		// instantiate a new query evaluator working in main memory
		MemoryIndexQueryEvaluator evaluator = new MemoryIndexQueryEvaluator();
		// first use an empty index
		evaluator.prepareInputData(new LinkedList<URILiteral>(), new LinkedList<URILiteral>());
		// insert a first triple using a SPARUL query
		evaluator.getResult("INSERT DATA { <subject> <predicate> <object> }");
		// set up a next query
		QueryResult result = evaluator.getResult("SELECT * WHERE { <subject> <predicate> ?object }");
		// get iterator for iterating one time through the result of the query
		Iterator<Bindings> iterator = result.oneTimeIterator();
		while(iterator.hasNext()) {
			// get the next solution of the query
			Bindings bindings = iterator.next();
			// iterate through all bound variables
			for(Variable variable: bindings.getVariableSet()){
				// print out the bound value of the variable
				System.out.println("Variable " + variable + " is bound to value " + bindings.get(variable));
			}
		}
	}
}
