package primitives;

import main.NetPrologoExtension;

import org.jpl7.Term;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.Reporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import utils.Utils;

// Dereference a specific variable from the last loaded solution of the active query.
public class DereferenceVarJPL implements Reporter {

	public Syntax getSyntax() {
		return SyntaxJ.reporterSyntax(new int[] {Syntax.StringType()}, Syntax.WildcardType());
    }
	
	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException{
    	String varName;
    	try{
        	varName=arg0[0].getString();
        }catch( LogoException e ){
        	throw new ExtensionException(e.getMessage());
        }
		Object obj=NetPrologoExtension.dereferenceVarJPL(varName);
		Object ret;
		try {
			// Type conversion from Prolog to NetLogo
			ret = Utils.plTermTOnlTermJPL((Term)obj);
		} catch (LogoException e) {
			throw new ExtensionException(e.getMessage());
		}
		return ret;
	}
}
