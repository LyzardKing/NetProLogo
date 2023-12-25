package primitives;

import main.NetPrologoExtension;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.Reporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

//Opens a new Prolog query.
public class RunQueryJPL implements Reporter {

    public Syntax getSyntax() {
        return SyntaxJ.reporterSyntax(new int[] {Syntax.StringType()}, Syntax.BooleanType());
    }
  
	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException{
        String call;
        try{
            call = arg0[0].getString();
        }catch( LogoException e ){
        	throw new ExtensionException(e.getMessage());
        }
        
    	return NetPrologoExtension.runQueryJPL(call); 
	}
}
