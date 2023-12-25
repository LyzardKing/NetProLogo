package primitives;

import main.NetPrologoExtension;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.Command;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

//Close current query.
public class Close implements Command {
	public Syntax getSyntax() {
        return SyntaxJ.commandSyntax();
    }

	@Override
	public void perform(Argument[] arg0, Context arg1)throws ExtensionException, LogoException {
		NetPrologoExtension.release();	
	}
}
