package primitives;

import main.NetPrologoExtension;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.Reporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

//Load the next solution for a certain solutions store
public class NextSolutionInStore implements Reporter {

	public Syntax getSyntax() {
        return SyntaxJ.reporterSyntax(new int[] {Syntax.NumberType()},Syntax.BooleanType());
    }
	
	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {		
    	int storeId;
    	try{
        	storeId = arg0[0].getIntValue();  
        }catch( LogoException e ){
        	throw new ExtensionException(e.getMessage());
        }
		
		return NetPrologoExtension.nextSolutionInStore(storeId);
	}
}
