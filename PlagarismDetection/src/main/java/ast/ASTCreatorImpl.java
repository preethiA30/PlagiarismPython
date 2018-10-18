package ast;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import python3Resources.Python3BaseListener;
import python3Resources.Python3Lexer;
import python3Resources.Python3Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 *  Creates an AST for a single source file.
 */
public class ASTCreatorImpl implements  ASTCreator{
    /**
     *
     * @param file : A .py file
     * @param encoding : The character encoding
     * @return A String form of the input code file.
     * @throws IOException
     */
    private static String readFile(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, encoding);
    }
    /**
     *
     * @param file: A .py file.
     * @return A parse tree.
     * @throws IOException
     */
    public Python3Parser.File_inputContext parse(File file) throws IOException {
        String code = readFile(file, Charset.forName("UTF-8"));
        Python3Lexer lexer = new Python3Lexer(new ANTLRInputStream(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer); // Get a list of matched tokens
        Python3Parser parser = new Python3Parser(tokens); // Pass tokens to parser
        return parser.file_input(); // Specify entry point
    }

    /**
     * Parse the python file to create a map with node type and line pair.
     * @param file file to parsed
     * @return A map parsed from file
     * @throws IOException
     */
    public List parseWithLine(File file) throws IOException {
        String code = readFile(file, Charset.forName("UTF-8"));
        Python3Lexer lexer = new Python3Lexer(new ANTLRInputStream(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer); // Get a list of matched tokens
        Python3Parser parser = new Python3Parser(tokens); // Pass tokens to parser
        Python3BaseListener listener = new Python3BaseListener();
        Python3Parser.File_inputContext rulecontext = parser.file_input();
        ParseTreeWalker.DEFAULT.walk(listener, rulecontext);

        return listener.getTreeList();
    }
}
