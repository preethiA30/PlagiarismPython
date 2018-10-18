package ast;

import python3Resources.Python3Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *  Class representing an AST for a single .py code file
 */
public class AST {
    private RuleContext ctx;
    private boolean ignoringWrappers = true;
    private StringBuilder astRepresenation = new StringBuilder();
    /**
     * A node which will be the parent of AST , initialized as Dummy
     */
    private Node nodeAST = new Node("dummy");
    /** Constructor for the AST
     *
     * @param ctx : A parse tree consisting of AST rules.
     */
    public AST(RuleContext ctx){
        this.ctx = ctx;
    }
    /** function to pint the AST.
     *
     */
    public void printAst(){
        explore(ctx);
    }
    /**
     * Function
     * @return
     */
    public Node getAstNode() {
        convertToNode(ctx, nodeAST);
        return nodeAST.getChildren().get(0);
    }
    /**
     * Traverse the parsed tree and create a new tree of Node type with label and List of Children
     * @param ctx ctx A parse tree consisting of AST rules.
     * @param parentNode the Node which will become the parent of currently iterated Node
     */
    private void convertToNode(RuleContext ctx,  Node parentNode) {
        boolean toBeIgnored = ignoringWrappers
                && ctx.getChildCount() == 1
                && ctx.getChild(0) instanceof ParserRuleContext;
        Node n = null;
        if (!toBeIgnored) {
            n  = new Node();
            String ruleName = Python3Parser.ruleNames[ctx.getRuleIndex()];
            n.setName(ruleName);
            parentNode.addChild(n);
        }
        for (int i=0;i<ctx.getChildCount();i++) {
            ParseTree element = ctx.getChild ( i );
            if (element instanceof RuleContext) {
                convertToNode ( (RuleContext) element, n == null? parentNode:n);
            }
        }
    }
    /** Explores the parse tree.
     *
     * @param ctx A parse tree consisting of AST rules.
     */
    private void explore(RuleContext ctx) {
        if (ctx.getChildCount() != 1) {
            String ruleName = Python3Parser.ruleNames[ctx.getRuleIndex()];
            astRepresenation.append(ruleName);
            astRepresenation.append(": ");
            astRepresenation.append(ctx.getText());
            astRepresenation.append("\n");
        }
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            if (element instanceof RuleContext) {
                explore((RuleContext) element);
            }
        }
    }


    /**
     *
     * @return the String form of the AST.
     */
    public StringBuilder convertToString(){
        explore(ctx);
        return astRepresenation;
    }
}