package primitives;

import main.NetPrologoExtension;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.Reporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

//Run a new query and store its first N solutions.
public class RunQueryNsolutions implements Reporter {

    public Syntax getSyntax() {
        return SyntaxJ.reporterSyntax(new int[] {Syntax.NumberType(),Syntax.StringType()}, Syntax.NumberType());
    }
  
	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException{
        String call;
        int numSolutions;
        try{
        	numSolutions = arg0[0].getIntValue();
            call = arg0[1].getString();
        }catch( LogoException e ){
        	throw new ExtensionException(e.getMessage());
        }
        
    	return Double.valueOf(NetPrologoExtension.runQueryNsolutions(numSolutions, call)); 
	}
}
