package ast;
import python3Resources.Python3Parser;
import java.io.IOException;
import java.io.File;
/**
 *  Interface for creating the AST.
 */
public interface ASTCreator {
    /**
     *
     * @param file: A .py file.
     * @return A parse tree.
     */
    Python3Parser.File_inputContext parse(File file) throws IOException;
}