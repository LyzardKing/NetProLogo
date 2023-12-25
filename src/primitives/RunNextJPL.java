package primitives;

import main.NetPrologoExtension;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.Reporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

//Load the next solution of the currently active Prolog query.
public class RunNextJPL implements Reporter {

	public Syntax getSyntax() {
        return SyntaxJ.reporterSyntax(Syntax.BooleanType());
    }
	
	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {		
		return NetPrologoExtension.runNextJPL(); 
	}
}
