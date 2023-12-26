package primitives;

import main.NetPrologoExtension;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.Reporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Opens a new Prolog query.
public class RunQueryJPL implements Reporter {

    final static String regex = "consult\\(\\'(.+)\\'\\)";

    public Syntax getSyntax() {
        return SyntaxJ.reporterSyntax(new int[] {Syntax.StringType()}, Syntax.BooleanType());
    }
  
	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException{
        String call;
        String currentDirectory = ".";
        try {
            currentDirectory = arg1.attachCurrentDirectory(currentDirectory);
        } catch (java.net.MalformedURLException ex) {
            throw new ExtensionException(ex);
        }
        try {
            call = arg0[0].getString();
        } catch( LogoException e ){
        	throw new ExtensionException(e.getMessage());
        }
        
        if (call.startsWith("consult(")) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(call);

            if (matcher.find()) {
                String path = matcher.group(1);
                if (!path.startsWith("/")) {
                    path = currentDirectory + "/" + path;
                    call = "consult('" + path + "')";
                }
            }
        }

    	return NetPrologoExtension.runQueryJPL(call); 
	}
}
