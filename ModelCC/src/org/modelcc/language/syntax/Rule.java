/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.modelcc.parser.fence.Symbol;

/**
 * Production Rule.
 * @author elezeta
 * @serial
 */
public final class Rule implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * User data.
     */
    private Object userData;

    /**
     * Left hand side element.
     */
    private RuleElement left;

    /**
     * Right hand side elements.
     */
    private List<RuleElement> right;

    /**
     * Builder.
     */
    private SymbolBuilder builder;

    /**
     * A Posteriori builder.
     */
    private PostSymbolBuilder postBuilder;

    /**
     * Default Builder.
     */
    private static SymbolBuilder defaultBuilder = new SymbolBuilder() {

        @Override
        public boolean build(Symbol t,Object data) {
            return true;
        }

    };

    /**
     * Default Builder.
     */
    private static PostSymbolBuilder defaultPostBuilder = new PostSymbolBuilder() {

        @Override
        public boolean build(Symbol t,Object data,Map<Symbol,Set<Symbol>> usedIn) {
            return true;
        }

    };

    /**
     * Relevant rule.
     */
    private int relevant;

    /**
     * Constructor.
     * @param left the left hand side element.
     * @param right the right hand side elements.
     * @param userData the user data.
     * @param builder the symbol builder.
     * @param postBuilder the symbol a posteriori builder.
     * @param relevant the relevant symbol.
     */
    public Rule(RuleElement left,List<RuleElement> right,Object userData,SymbolBuilder builder,PostSymbolBuilder postBuilder,int relevant) {
        this(left,right,userData,builder);
        this.relevant = relevant;
        this.postBuilder = postBuilder;
        for (int i = 0;i < right.size();i++)
            if (!RuleElementPosition.class.equals(right.get(i).getClass())) {
                System.out.println("ERROR     "+this);
                System.exit(0);
            }
    }

    /**
     * Constructor.
     * @param left the left hand side element.
     * @param right the right hand side elements.
     * @param userData the user data.
     * @param builder the symbol builder.
     * @param relevant the relevant symbol.
     */
    public Rule(RuleElement left,List<RuleElement> right,Object userData,SymbolBuilder builder,int relevant) {
        this(left,right,userData,builder);
        this.relevant = relevant;
    }

    /**
     * Constructor.
     * @param left the left hand side element.
     * @param right the right hand side elements.
     * @param userData the user data.
     * @param builder the symbol builder.
     * @param postBuilder the symbol a posteriori builder. 
     */
    public Rule(RuleElement left,List<RuleElement> right,Object userData,SymbolBuilder builder,PostSymbolBuilder postBuilder) {
        this(left,right,userData,builder);
        this.postBuilder = postBuilder;
        if (this.postBuilder == null)
            this.postBuilder = defaultPostBuilder;
    }
    
    /**
     * Constructor.
     * @param left the left hand side element.
     * @param right the right hand side elements.
     * @param userData the user data.
     * @param builder the symbol builder.
     */
    public Rule(RuleElement left,List<RuleElement> right,Object userData,SymbolBuilder builder) {
        this.left = left;
        this.right = right;
        this.userData = userData;
        this.builder = builder;
        if (this.right == null)
            this.right = new ArrayList<RuleElement>();
        if (this.builder == null)
            this.builder = defaultBuilder;
        if (this.postBuilder == null)
            this.postBuilder = defaultPostBuilder;
        relevant = -1;
    }

    /**
     * Constructor.
     * @param left the left hand side element.
     * @param right the right hand side elements.
     */
    public Rule(RuleElement left,List<RuleElement> right) {
        this(left,right,null,defaultBuilder);
    }

    /**
     * Constructor.
     */
    public Rule() {
        this(null,null);
    }

    /**
     * @return the left hand side element.
     */
    public RuleElement getLeft() {
        return left;
    }

    /**
     * @param left the left hand side element to set.
     */
    public void setLeft(RuleElement left) {
        this.left = left;
    }

    /**
     * @return the right hand side elements.
     */
    public List<RuleElement> getRight() {
        return right;
    }

    /**
     * @param right the right hand side elements to set.
     */
    public void setRight(List<RuleElement> right) {
        this.right = right;
        if (this.right == null)
            this.right = new ArrayList<RuleElement>();
    }

    /**
     * @return the builder
     */
    public SymbolBuilder getBuilder() {
        return builder;
    }

    /**
     * @param builder the builder to set
     */
    public void setBuilder(SymbolBuilder builder) {
        this.builder = builder;
        if (this.builder == null)
            this.builder = defaultBuilder;
    }

    /**
     * @return the userData
     */
    public Object getUserData() {
        return userData;
    }

    /**
     * @param userData the userData to set
     */
    public void setUserData(Object userData) {
        this.userData = userData;
    }

    /**
     * @return the relevant
     */
    public int getRelevant() {
        return relevant;
    }

    /**
     * @param relevant the relevant to set
     */
    public void setRelevant(int relevant) {
        this.relevant = relevant;
    }
    
    @Override
    public String toString() {
        String r = left.getType()+" ::= ";
        for (int i = 0;i < right.size();i++) {
            String s = "";
            if (right.get(i).getClass().equals(RuleElementPosition.class))
                s = "+";
            r += s+right.get(i).getType()+" ";
        }
        return r+" builder is "+builder;
    }

    /**
     * @return the postBuilder
     */
    public PostSymbolBuilder getPostBuilder() {
        return postBuilder;
    }
}
