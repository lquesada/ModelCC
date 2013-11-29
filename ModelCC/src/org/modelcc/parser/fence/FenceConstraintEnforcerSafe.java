/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.modelcc.language.syntax.AssociativityConstraint;
import org.modelcc.language.syntax.Constraints;
import org.modelcc.language.syntax.Rule;
import org.modelcc.language.syntax.RuleElement;

/**
 * Fence Constraint Enforcer
 * @author elezeta
 * @serial
 */
public class FenceConstraintEnforcerSafe implements Serializable {

    private Constraints constraints;

    private Map<Object, Object> superClasses;

    private Map<Object, Set<Object>> indirectSuperclasses;

    private Map<Object, AssociativityConstraint> indirectAssociativities;

    private Set<Object> isIndirectAssociative;

    private Set<Object> hasAnyAssociativity;

    private Set<Rule> hasAnyAssociativityRule;

    private Map<Object,Set<Object>> subclasses;

    private Map<Object,Set<Object>> indirectSubclasses;

    private Set<Object> classes;

    private Counter id;

    private Set<Symbol> usedSymbols;

    private Map<Symbol,Set<Symbol>> usedIn;

    private Set<Symbol> start;

    private ParsedGraph pg;

    private Set<Symbol> symbols;

    private Map<ParsedSymbol,Set<Symbol>> mapped;

    private Set<Object> associateds;

    private Map<ParsedSymbol,Set<ExpandTuple>> mappedtuples;
    
    protected Object data;

    private Map<Object,Map<String,Object>> objectMetadata;
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Builds a symbol, filling its data, and validates it.
     * @param emptyRules 
     * @param s symbol to be built.
     * @return true if the symbol is valid, false if not
     */
    protected boolean build(Rule r,Symbol s) {
        return r.getBuilder().build(s,data);
    }

    /**
     * Builds a token symbol, filling its data, and validates it.
     * @param s symbol to be built.
     * @return true if the symbol is valid, false if not
     */
    private boolean build(Symbol s) {
        return pg.getGrammar().getTsb().build(s,data);
    }
    
    /**
     * Builds a symbol a posteriori, filling its data, and validates it.
     * @param s symbol to be built.
     * @return true if the symbol is valid, false if not
     */
    private boolean postBuild(Rule r,Symbol s) {
        return r.getPostBuilder().build(s,data,usedIn);
    }

    /**
     * Perform syntactical analysis on a lexical graph.
     * @param constraints the constraints.
     * @param pg the input parsed graph.
     * @param objectMetadata the object metadata warehouse
     * @return a syntactic analysis graph.
     */
    public SyntaxGraph enforce(Constraints constraints, ParsedGraph pg) {
    	return enforce(constraints,pg,null);
    }
    
    /**
     * Perform syntactical analysis on a lexical graph.
     * @param constraints the constraints.
     * @param pg the input parsed graph.
     * @return a syntactic analysis graph.
     */
    public SyntaxGraph enforce(Constraints constraints, ParsedGraph pg,Map<Object,Map<String,Object>> objectMetadata) {
        
    	this.objectMetadata = objectMetadata;
    	
        this.pg = pg;
        
        this.constraints = constraints;
        
        usedSymbols = new HashSet<Symbol>(256);
        
        usedIn = new HashMap<Symbol,Set<Symbol>>();

        start = new HashSet<Symbol>();
        
        data = pg.getGrammar().getDataFactory().generate();


        // -------------
        // Expansion and enforcement.
        // -------------

        {

            // INITIALIZATION
            // --------------

            // Auxiliar variables
            Iterator<ParsedSymbol> ite;
            Iterator<Symbol> ites;
            Symbol s;
            ParsedSymbol ps;

            // Set of symbols.
            symbols = new HashSet<Symbol>(256);

            // Potential start symbols.
            Set<Symbol> potentialstart = new HashSet<Symbol>();

            // Map of parsed symbols to symbols.
            mapped = new HashMap<ParsedSymbol,Set<Symbol>>(64);

            // Map of expand tuples.
            mappedtuples = new HashMap<ParsedSymbol,Set<ExpandTuple>>(64);
                    
            // Parsed Symbol history.
            HashSet<ParsedSymbol> history = new HashSet<ParsedSymbol>();

            // Associate symbols.
            associateds = new HashSet<Object>();

            // id.
            id = new Counter();

            // Super classes.
            superClasses = new HashMap<Object,Object>(64);
            
            // Indirect super classes.
            indirectSuperclasses  = new HashMap<Object,Set<Object>>(64);
            
            // Indirect associativities.
            indirectAssociativities = new HashMap<Object,AssociativityConstraint>(64);
            
            // HasAnyAssociativity.
            isIndirectAssociative = new HashSet<Object>(64);
            
            // HasAnyAssociativity.
            hasAnyAssociativity = new HashSet<Object>(64);

            // HasAnyAssociativityRule.
            hasAnyAssociativityRule = new HashSet<Rule>(64);
                 
            // classes.
            classes = new HashSet<Object>(64);

            // indirect subclasses.
            indirectSubclasses = new HashMap<Object,Set<Object>>(64);

            // subclasses.
            subclasses = new HashMap<Object,Set<Object>>(64);
               
            initializeConstraints();            
            
            // PROCEDURE
            // --------------


            // Recursively expand potential starting symbols, using memoization.
            for (ite = pg.getStart().iterator();ite.hasNext();) {
                ps = ite.next();
                potentialstart.addAll(expand(history,ps));
            }


            // Calculate start and used symbols.
            for (ites = potentialstart.iterator();ites.hasNext();) {
                s = ites.next();
                if (pg.getPreceding().get(s.getParsedSymbol()) == null && pg.getFollowing().get(s.getParsedSymbol()) == null) {
                    start.add(s);
                    recAdd(s);
                }
            }

        }

        Set<Symbol> erase = new HashSet<Symbol>();
        for (Iterator<Symbol> ite = usedSymbols.iterator();ite.hasNext();) {
            Symbol s = ite.next();
            if (!erase.contains(s)) {
                if (s.getRule() != null) {
                    if (!postBuild(s.getRule(),s)) {
                        removeDependent(s,erase);
                    }
                }
            }
        }
        
        for (Iterator<Symbol> ite = erase.iterator();ite.hasNext();) {
            Symbol s = ite.next();
            usedSymbols.remove(s);
            start.remove(s);
        }
        
        return new SyntaxGraph(usedSymbols,start);
    }


    private void initializeConstraints() {
        
        // Super classes.
        
        classes.addAll(constraints.getAssociativities().keySet());
        
        for (Iterator<Rule> iter = pg.getGrammar().getRules().iterator();iter.hasNext();) {
            Rule r = iter.next();
            if (r.getRelevant() != -1) {
                Object c = r.getRight().get(r.getRelevant()).getType();
                Object n = r.getLeft().getType();
                superClasses.put(c,n);
                Set<Object> na = subclasses.get(n);
                if (na == null) {
                    na = new HashSet<Object>();
                    subclasses.put(n,na);
                }
                na.add(c);
                classes.add(n);
                classes.add(c);
            }
        }
        
        // Indirect super classes.

        Object c;
        for (Iterator<Object> itec = classes.iterator();itec.hasNext();) {
            c = itec.next();
            Object sup = superClasses.get(c);
            if (sup != null) {
                Set<Object> indClasses = new HashSet<Object>();
                Stack<Object> stClasses = new Stack<Object>();
                Set<Object> done = new HashSet<Object>();
                done.add(c);
                done.add(sup);
                stClasses.push(sup);
                while (!stClasses.isEmpty()) {
                    Object n = stClasses.pop();
                    Object nsup = superClasses.get(n);
                    if (nsup != null && !done.contains(nsup)) {
                        stClasses.push(nsup);
                        done.add(nsup);
                    }
                    indClasses.add(n);
                    Set<Object> na = indirectSubclasses.get(n);
                    if (na == null) {
                        na = new HashSet<Object>();
                        indirectSubclasses.put(n,na);
                    }
                    na.add(c);
                }
                indClasses.add(c);
                indirectSuperclasses.put(c,indClasses);
            }
        }
        
        // indirectAssociativities
        indirectAssociativities.putAll(constraints.getAssociativities());
        for (Iterator<Object> ite = classes.iterator();ite.hasNext();) {
            Object cl = ite.next();
            Object cls = superClasses.get(cl);
            if (cls == null) {
                recAssoc(cl);
            }
        }
        
        // hasAnyAssociativity
        
        for (Iterator<Rule> iter = pg.getGrammar().getRules().iterator();iter.hasNext();) {
            Rule r = iter.next();
            if (r.getRelevant() == -1) {
                int assocs = 0;
                for (int i = 0;i < r.getRight().size();i++) {
                    if (isIndirectAssociative.contains(r.getRight().get(i).getType()))
                        assocs++;
                }            
                if (assocs>=1) {
                    hasAnyAssociativity.add(r.getLeft().getType());
                    hasAnyAssociativityRule.add(r);
                }
            }
        }
    }
    
     private void recAssoc(Object pe) {
        Set<Object> pes = subclasses.get(pe);
        if (pes == null) {
            if (indirectAssociativities.get(pe)!=null) {
                if (indirectAssociativities.get(pe)!=AssociativityConstraint.UNDEFINED) {
                    isIndirectAssociative.add(pe);
                    indirectAssociativities.put(pe, indirectAssociativities.get(pe));
                }
            }
        }
        else {
            indirectAssociativities.remove(pe);
            boolean hasOne = false;
            AssociativityConstraint ac = null;
            AssociativityConstraint acn;
            for (Iterator<Object> ite = pes.iterator();ite.hasNext();) {
                Object pesub = ite.next();
                recAssoc(pesub);
                acn = indirectAssociativities.get(pesub);
                if (acn != null) {
                    if (ac == null) {
                        hasOne = true;
                        ac = acn;
                    }
                    else if (acn!=ac) {
                        hasOne = false;
                    }
                }
            }
            if (ac != null) {
                isIndirectAssociative.add(pe);
                if (hasOne) {
                    indirectAssociativities.put(pe, ac);
                    if (ac == AssociativityConstraint.UNDEFINED)
                        isIndirectAssociative.remove(pe);
                }
            }
        }
    }
   
    /**
     * Recursively adds the symbols used by a symbol
     * @param usedSymbols the used symbols list to be generated.
     * @param s the symbol
     */
    private void recAdd(Symbol s) {
        usedSymbols.add(s);
        Iterator<Symbol> ite;
        Symbol x;
        for (ite = s.getContents().iterator();ite.hasNext();) {
            x = ite.next();
            recAdd(x);
        }
    }

    /**
     * Expands a parsed symbol into several symbols.
     * @param history the history of single symbol expansions to avoid cycles.
     * @param pg the parsed graph
     * @param constraints the constraints
     * @param symbols the set of symbols
     * @param mapped the mappings between parsed symbols and symbols
     * @param ps the parsed symbol to be expanded
     * @return a set of symbols expanded from the parsed symbol.
     */
    private Set<Symbol> expand(Set<ParsedSymbol> history,ParsedSymbol ps) {
        Set<Symbol> ma = mapped.get(ps);
        // Memoization wrapper
        if (ma != null)
            return ma;

        // If the symbol to be expanded is a token.
        if (ps.isToken()) {
            Symbol s;
            // Generate the token.
            HashSet<Symbol> hs = new HashSet<Symbol>();
            s = new Symbol(id.val,ps);

            //System.out.println("1------ to generate "+ps.getType()+" string "+ps.getString()+" "+ps.getStartIndex()+"-"+ps.getEndIndex());
            mapped.put(ps,hs);
            Set<Symbol> ss = fixEmpties(s);
            if (ss.isEmpty())
            	ss.add(s);
            for (Iterator<Symbol> ite = ss.iterator();ite.hasNext();) {
            	Symbol s1 = ite.next();
                s1.setUserData(ps.getUserData());
	            if (build(s1)) {
	                id.val++;
	                symbols.add(s1);
	                hs.add(s1);
	                storeMetadata(s1);
	            }
            }
            return hs;
        }
        
        // If the symbol is an empty start symbol.
        else if (ps.getStartIndex()==ps.getEndIndex() && ps.getStartIndex()==-1) {

            // Generate the token.
            HashSet<Symbol> hs = new HashSet<Symbol>();
            Symbol s;
            s = new Symbol(id.val,ps);



            //System.out.println("2------ to generate "+ps.getType()+" string "+ps.getString()+" "+ps.getStartIndex()+"-"+ps.getEndIndex());

            
            mapped.put(ps,hs);
            Set<Symbol> ss = fixEmpties(s);
            if (ss.isEmpty())
            	ss.add(s);
            for (Iterator<Symbol> ite = ss.iterator();ite.hasNext();) {
            	Symbol s1 = ite.next();
                s1.setUserData(ps.getUserData());
            	if (build(pg.getGrammar().getEmptyRuleRules().get(s.getType()),s)) {
            		id.val++;
                	symbols.add(s1);
	                hs.add(s1);
	                storeMetadata(s1);
                }
            }
            return hs;
        }
        else {
        
            if (history.contains(ps))
                return new HashSet<Symbol>();
            history.add(ps);

            Set<ExpandTuple> tuples = searchAllTuples(ps);
            
            //System.out.println("3------ to generate "+ps.getType()+" string "+ps.getString()+" "+ps.getStartIndex()+"-"+ps.getEndIndex());

            Set<Symbol> hs = new HashSet<Symbol>();
            for (Iterator<ExpandTuple> ite = tuples.iterator();ite.hasNext();) {
                ExpandTuple et = ite.next();
                expandSymbol(history,hs,ps,et,0,new ArrayList<Symbol>(),new ArrayList<RuleElement>());
            }


            Set<Symbol> hsf = new HashSet<Symbol>();

            while (!hs.isEmpty()) {
                Set<Rule> precf = new HashSet<Rule>();

                Set<Rule> precall = new HashSet<Rule>();

                for (Iterator<Symbol> ites = hsf.iterator();ites.hasNext();) {
                    Symbol hs2 = ites.next();
                    Set<Rule> aux1 = constraints.getSelectionPrecedences().get(hs2.getRule());
                    if (aux1 != null) {
                        precf.addAll(aux1);
                        precall.addAll(aux1);
                    }
                    Set<Rule> aux2 = constraints.getSelectionPrecedences().get(hs2.getRelevantRule());
                    if (aux2 != null) {
                        precf.addAll(aux2);
                        precall.addAll(aux2);
                    }
                }

                for (Iterator<Symbol> ites = hs.iterator();ites.hasNext();) {
                    Symbol hs2 = ites.next();
                    Set<Rule> aux1 = constraints.getSelectionPrecedences().get(hs2.getRule());
                    if (aux1 != null) {
                        precall.addAll(aux1);
                    }
                    Set<Rule> aux2 = constraints.getSelectionPrecedences().get(hs2.getRelevantRule());
                    if (aux2 != null) {
                        precall.addAll(aux2);
                    }
                }

                for (Iterator<Symbol> ites = hs.iterator();ites.hasNext();) {
                    Symbol hs2 = ites.next();
                    if (precf.contains(hs2.getRule()) || precf.contains(hs2.getRelevantRule()))
                        ites.remove();
                    else if (!precall.contains(hs2.getRule()) && !precall.contains(hs2.getRelevantRule())) {
                        ites.remove();
                        hsf.add(hs2);
                    }
                }

            }


            mapped.put(ps,hsf);        
            return hsf;
        
        }

    }


    private Set<Symbol> fixEmpties(Symbol s) {
    	if (!s.getContents().isEmpty()) {
        	Set<Symbol> ret = new HashSet<Symbol>();
    		ret.add(s);
    		return ret;
    	}
    	if (pg.getGrammar().getEmptyRules().containsKey(s.getType())) {
    		Set<Object> rules = pg.getGrammar().getEmptyRules().get(s.getType());
    		if (rules == null) {
            	Set<Symbol> ret = new HashSet<Symbol>();
            	ret.add(s);
        		return ret;
        	}
    		else {
            	Set<Symbol> ret = new HashSet<Symbol>();
            	ret.add(s);
            	/*
    			for (Iterator<Object> iter = rules.iterator();iter.hasNext();) {
    				Object type = iter.next();
    				//System.out.println("FOUND "+type+"  "+rules);
    				Symbol sn = new Symbol(id.val,new ParsedSymbol(type,-1,-1,""));
    				//System.out.println("RECURSIVE IN");
    	            Set<Symbol> ss = fixEmpties(sn);
    	            if (ss.size()==0)
    	            	ret.add(s);
    	            for (Iterator<Symbol> ite = ss.iterator();ite.hasNext();) {
    	            	Symbol s1 = ite.next();
    	            	//System.out.println("TIENE CONTENIDOS "+s1.getContents().size());
    	                //s1.setUserData(s.getParsedSymbol().getUserData());
    		            if (build(pg.getGrammar().getEmptyRuleRules().get(s1.getType()),s1)) {
    		                id.val++;
    		                symbols.add(s1);
    		                storeMetadata(s1);

    		                List<RuleElement> elements1 = new ArrayList<RuleElement>();
    		                elements1.add(new RuleElement(type)); //Position?
    		                List<Symbol> contents1 = new ArrayList<Symbol>();
    		                contents1.add(s1);
    		                Symbol snew = new Symbol(id.val,s.getParsedSymbol(),pg.getGrammar().getEmptyRuleRules().get(type),pg.getGrammar().getEmptyRuleRules().get(type),elements1,contents1);
    		                snew.setUserData(s.getParsedSymbol().getUserData());
//        		            if (build(pg.getGrammar().getEmptyRuleRules().get(s.getType()),snew)) {
//        		            	id.val++;
//        		            	symbols.add(snew);
//        		            	storeMetadata(snew);
    		            	System.out.println("ACABO DE DEVOLVER"+snew+"    "+snew.getType()+"   con "+elements1);
    		            	ret.add(snew);
//        		            }
    		            }
	    	            id.val++;
    	            }
    			}
    			*/
    			return ret;
    		}
    	}
    	else {
        	Set<Symbol> ret = new HashSet<Symbol>();
    		ret.add(s);
    		return ret;
    	}
	}

	private Set<ExpandTuple> searchAllTuples(ParsedSymbol ps) {
        Set<ExpandTuple> ma = mappedtuples.get(ps);
        if (ma != null)
            return ma;
        Set<ExpandTuple> ets = new HashSet<ExpandTuple>();
        ParsedSymbol ps2;
        Rule r;
        Set<Rule> rules = pg.getElementRules().get(ps.getType());
        if (rules != null) {
            Set<ParsedSymbol> pss = pg.getStartPositions().get(ps.getStartIndex());
            for (Iterator<Rule> iter = rules.iterator();iter.hasNext();) {
                r = iter.next();
                ExpandTuple et = new ExpandTuple(r,new ArrayList<ParsedSymbol>());
                for (Iterator<ParsedSymbol> itep = pss.iterator();itep.hasNext();) {
                    ps2 = itep.next();
                    // If that symbol is not the same symbol as this one and ends before this one
                    if ((!ps2.equals(ps)) && (ps2.getEndIndex()<=ps.getEndIndex())) {
                        if (r.getRight().size()!=1 || ps2.getEndIndex()==ps.getEndIndex())
                        // And is the next symbol of the rule and does not consist of only this one.
                        if (isNext(ps2.getType(),r,0)) {
                            searchTuples(ps,ets,r,0,ps2,et);
                           // Produce all the tuples (rule,listofnonexpandedsymbols) that represent valid reductions.

                        }
                    }
                }
            }
        }

        if (hasAnyAssociativity.contains(ps.getType())) {
            // Sacar de ets todas las tuplas que usan una regla con elemento asociativo.
            Set<ExpandTuple> etsas = new HashSet<ExpandTuple>();
            rules = new HashSet<Rule>();
            for (Iterator<ExpandTuple> ite = ets.iterator();ite.hasNext();) {
                ExpandTuple et = ite.next();
                if (hasAnyAssociativityRule.contains(et.getRule())) {
                    ite.remove();
                    etsas.add(et);
                    rules.add(et.getRule());
                }
            }
          // Si todas están formadas usando la misma regla:
            if (rules.size()==1) {
                Rule rx = rules.iterator().next();
                // Buscar el número de elemento que es asociativo.
                int elm = -1;
                for (int i = 0;i < rx.getRight().size();i++) {
                    if (isIndirectAssociative.contains(rx.getRight().get(i).getType()))
                        elm = i;
                }
                if (elm != -1) {
                    int min = -1;
                    int max = -1;
                    AssociativityConstraint ac = null;
                    boolean ok = true;
                    for (Iterator<ExpandTuple> ite = etsas.iterator();ite.hasNext();) {
                        ExpandTuple et = ite.next();
                        ParsedSymbol asoc = et.getSymbols().get(elm);
                        AssociativityConstraint acn = calculateRelevantAssoc(asoc);
                        if (ac != null && acn != ac)
                            ok = false;
                        ac = acn;
                        if (asoc.getStartIndex()<min || min==-1)
                            min = asoc.getStartIndex();
                        if (asoc.getEndIndex()>max)
                            max = asoc.getEndIndex();
                    }
                    
                    if (ac != null && ok && etsas.size()>1) {
                        // Localizar el elemento asociativo en todas, + localizar máximo (=end maximo) y mínimo (=start mínimo).
                        // Si la asociatividad del elemento asociativo es IGUAL en todas:

                        // Si NON_ASSOCIATIVE y hay más de una, borrar todas.
                        if (ac == AssociativityConstraint.NON_ASSOCIATIVE) {
                            etsas.clear();
                        }

                        // Si LEFT_TO_RIGHT: Eliminar todas cuyo elemento con asociatividad.end != end maximo
                        
                        if (ac == AssociativityConstraint.LEFT_TO_RIGHT) {
                            for (Iterator<ExpandTuple> ite = etsas.iterator();ite.hasNext();) {
                                ExpandTuple et = ite.next();
                                if (et.getSymbols().get(elm).getEndIndex()!=max) {
                                    ite.remove();
                                }
                            }
                        }

                        // Si RIGHT_TO_LEFT: Eliminar todas cuyo elemento con asociatividad.start != start minimo
                        if (ac == AssociativityConstraint.RIGHT_TO_LEFT) {
                            for (Iterator<ExpandTuple> ite = etsas.iterator();ite.hasNext();) {
                                ExpandTuple et = ite.next();
                                if (et.getSymbols().get(elm).getStartIndex()!=min) {
                                    ite.remove();
                                }
                            }
                        }

                    }
                }
                
            }
            ets.addAll(etsas);
            // Si LEFT_TO_RIGHT o RIGHT_TO_LEFT:
            
            // En cualquier caso: devolver a ets.
        }

        mappedtuples.put(ps, ets);
        return ets;
    }
     
    private void searchTuples(ParsedSymbol ps,Set<ExpandTuple> tuples, Rule r, int i, ParsedSymbol ps2,ExpandTuple act) {
        // Si el siguiente de la regla está en g.getEmptyRules(), hacer searchTuples con i+1
        if (i >= r.getRight().size()) {
            int lastIndex = -1;
            for (int j = act.getSymbols().size()-1;lastIndex == -1 && j >= 0;j--) {
                ParsedSymbol par = act.getSymbols().get(j);
                if (par != null)
                    lastIndex = par.getEndIndex();
            }
            if (lastIndex == ps.getEndIndex()) {
                tuples.add(act);
            }
            return;
        }
        else {
        	if (pg.getGrammar().getEmptyRules().containsKey(r.getRight().get(i).getType())) {
        		Set<Object> rules = pg.getGrammar().getEmptyRules().get(r.getRight().get(i).getType());
            	if (rules==null) {
	                List<ParsedSymbol> nl = new ArrayList<ParsedSymbol>();
	                nl.addAll(act.getSymbols());
	                nl.add(null);
	                ExpandTuple n = new ExpandTuple(r, nl);
	                searchTuples(ps,tuples,r,i+1,ps2,n);
            	}
            	else {
	                for (Iterator<Object> ite = rules.iterator();ite.hasNext();) {
	                	Object id = ite.next();
		                List<ParsedSymbol> nl = new ArrayList<ParsedSymbol>();
		                ParsedSymbol nps = new ParsedSymbol(id,-1,-1,"");
		                nl.addAll(act.getSymbols());
		                nl.add(nps);
		                ExpandTuple n = new ExpandTuple(r, nl);
		                searchTuples(ps,tuples,r,i+1,ps2,n);
	                }
            	}
            }
            if (ps2 != null) {
                if (r.getRight().get(i).getType().equals(ps2.getType())) {
                    List<ParsedSymbol> nl = new ArrayList<ParsedSymbol>();
                    nl.addAll(act.getSymbols());
                    nl.add(ps2);
                    ExpandTuple n = new ExpandTuple(r, nl);
                    Set<ParsedSymbol> pss = pg.getFollowing().get(ps2);
                    if (pss != null) {
                        for (Iterator<ParsedSymbol> ite = pss.iterator();ite.hasNext();) {
                            ParsedSymbol psn = ite.next();
                            if ((!psn.equals(ps)) && (psn.getEndIndex()<=ps.getEndIndex())) {
                                searchTuples(ps,tuples,r,i+1,psn,n);
                            }
                        }
                    }
                    searchTuples(ps,tuples,r,i+1,null,n);
                }
            }
        }
    }

    private void expandSymbol(Set<ParsedSymbol> history,Set<Symbol> ret, ParsedSymbol ps,ExpandTuple et, int i,List<Symbol> content,List<RuleElement> elements) {
        Rule r = et.getRule();
        if (i >= r.getRight().size()) {
            if (content.get(content.size()-1).getEndIndex()<=ps.getEndIndex()) {
                Rule relevant;
                Symbol s;
                if (r.getRight().size()==1)
                    relevant = content.get(0).getRelevantRule();
                else if (r.getRelevant() != -1)
                    relevant = content.get(r.getRelevant()).getRelevantRule();
                else
                    relevant = r;

                s = new Symbol(id.val,ps,r,relevant,elements,content);

                Set<Symbol> ss = fixEmpties(s);
                for (Iterator<Symbol> ite = ss.iterator();ite.hasNext();) {
                	Symbol s1 = ite.next();
	                id.val++;
	                
	                s1.setUserData(ps.getUserData());
	
	                boolean inhibited,recLeft,recRight;
	
	                //Checks if associate.
	                boolean associate = false;
	                for (i = 0;i < s1.getContents().size();i++) {
	                    if (constraints.getAssociativities().get(s1.getContents().get(i).getType()) != null) {
	                        associate = true;
	                        if (i > 0) {
	                            if (s1.getRule() != null && s1.getContents().get(i-1).getRelevantRule() != null) {
	                                if (s1.getRule().getLeft().equals(s1.getContents().get(i-1).getRelevantRule().getLeft()) || s1.getRule().equals(s1.getContents().get(i-1).getRelevantRule())) {
	                                   associate = true;
	                                }
	                            }
	                        }
	                        if (i < s1.getContents().size()-1) {
	                            if (s1.getRule() != null && s1.getContents().get(i+1).getRelevantRule() != null) {
	                                if (s1.getRule().getLeft().equals(s1.getContents().get(i+1).getRelevantRule().getLeft()) || s1.getRule().equals(s1.getContents().get(i+1).getRelevantRule())) {
	                                    associate = true;
	                                }
	                            }
	                        }
	                    }
	                }
	                if (associate) {
	                    associateds.add(s1);
	                }
	               
	                inhibited = false;
	
	                boolean aux;
	                for (i = 0;i < s1.getContents().size();i++) {
	
	                    
	                    if (constraints.getAssociativities().get(s1.getContents().get(i).getType()) != null) {
	                        recLeft = false;
	                        recRight = false;
	                        if (i > 0) {
	                            if (s1.getRule().equals(s1.getContents().get(i-1).getRelevantRule()))
	                                recLeft = true;
	                            if (associateds.contains(s1.getContents().get(i-1))) {
	                                aux = recLeft;
	                                recLeft = true;
	                                Set<Rule> compc = constraints.getCompositionPrecedences().get(s1.getContents().get(i-1).getRelevantRule());
	                                if (compc != null) {
	                                    if (compc.contains(r)) {
	                                        recLeft = aux;
	                                    }
	                                }
	                            }
	                        }
	                        if (i < s1.getContents().size()-1) {
	                            if (s1.getRule().equals(s1.getContents().get(i+1).getRelevantRule()))
	                                recRight = true;
	                            if (associateds.contains(s1.getContents().get(i+1))) {
	                                aux = recRight;
	                                recRight = true;
	                                Set<Rule> compc = constraints.getCompositionPrecedences().get(s1.getContents().get(i+1).getRelevantRule());
	                                if (compc != null) {
	                                    if (compc.contains(r)) {
	                                        recRight = aux;
	                                    }
	                                }
	                            }
	                        }
	
	                        switch (constraints.getAssociativities().get(s1.getContents().get(i).getType())) {
	                            case LEFT_TO_RIGHT:
	                                if (recRight) {
	                                    inhibited = true;
	                                }
	                                break;
	                            case RIGHT_TO_LEFT:
	                                if (recLeft) {
	                                    inhibited = true;
	                                }
	                                break;
	                            case NON_ASSOCIATIVE:
	                                if (recRight || recLeft) {
	                                    inhibited = true;
	                                }
	                                break;
	                            default:
	                                break;
	                            }
	                    }
	                }
	
	
	                if (!inhibited) {
	                    Set<Rule> compc = constraints.getCompositionPrecedences().get(r);
	                    if (compc != null) {
	                        for (int j = 0;j < s1.getContents().size();j++) {
	                            if (s1.getContents().get(j).getRelevantRule() != null) {
	                                if (compc.contains(s1.getContents().get(j).getRelevantRule())) {
	                                    inhibited = true;
	                                }
	                            }
	                        }
	                    }
	                }
	
	                 if (!inhibited) {
	                	 
	                    if (build(s1.getRule(),s1)) {
	                        if (r.getRight().size() == 1)
	                            if (associateds.contains(content.get(0)))
	                                associateds.add(s1);
	                        if (r.getRelevant() != -1)
	                            if (associateds.contains(content.get(r.getRelevant())))
	                                associateds.add(s1);
	                        symbols.add(s1);
	                        ret.add(s1);
	                        for (int j = 0;j < s1.getContents().size();j++) {
	                            addUses(s1,s1.getContents().get(j));
	                        }
	                        storeMetadata(s1);
	                    }
	                }
                }
            }
        }
        else {
            if (et.getSymbols().get(i) == null) {
                expandSymbol(history,ret,ps,et,i+1,content,elements);
            }
            else {
                List<Symbol> cts;
                List<RuleElement> eles;
                eles = new ArrayList<RuleElement>();
                eles.addAll(elements);
                eles.add(r.getRight().get(i));
                for (Iterator<Symbol> ite = expand(history,et.getSymbols().get(i)).iterator();ite.hasNext();) {
                    Symbol s = ite.next();
                    cts = new ArrayList<Symbol>();
                    cts.addAll(content);
                    cts.add(s);
                    expandSymbol(history,ret, ps, et, i+1, cts, eles);
                }

            }
        }
    }
    

	/**
     * Checks if a symbol is the next matchable from a rule
     * @param type the type of the symbol
     * @param rul the rule
     * @param grammar the grammar
     * @param ini the next position of the rule
     * @return
     */
    private boolean isNext(Object type, Rule r,int ini) {
        int i = ini;
        while (i < r.getRight().size()) {
            if (type.equals(r.getRight().get(i).getType()))
                return true;
            else if(pg.getGrammar().getEmptyRules().containsKey(r.getRight().get(i).getType())) {
                i++;
            }
            else
                return false;
        }
        return false;
    }

    private AssociativityConstraint calculateRelevantAssoc(ParsedSymbol ps) {
        AssociativityConstraint ac = indirectAssociativities.get(ps.getType());
        if (ac != null) {
            return ac;
        }
        else if (isIndirectAssociative.contains(ps.getType())) {   
            Set<ExpandTuple> tup = searchAllTuples(ps);
            if (tup.size()==1) {
                ExpandTuple et = tup.iterator().next();
                if (et.getRule().getRelevant() != -1) {
                    return calculateRelevantAssoc(et.getSymbols().get(et.getRule().getRelevant()));
                }
            }
        }
        return null;
    }

    private void addUses(Symbol s, Symbol get) {
        Set<Symbol> useds = usedIn.get(get);
        if (useds == null) {
            useds = new HashSet<Symbol>();
            usedIn.put(get,useds);
        }
        useds.add(s);
    }

    private void removeDependent(Symbol s,Set<Symbol> erase) {
        erase.add(s);
        if (usedIn.get(s) != null) {
            for (Iterator<Symbol> ite = usedIn.get(s).iterator();ite.hasNext();) {
                Symbol s2 = ite.next();
                removeDependent(s2,erase);
            }
        }
    }

    private void storeMetadata(Symbol symbol) {
    	if (objectMetadata == null) {
    		return;
    	}
        Map<String,Object> symbolMap = objectMetadata.get(symbol.getUserData());
        if (symbolMap != null)
                return;
        symbolMap = new HashMap<String,Object>();
        objectMetadata.put(symbol.getUserData(),symbolMap);
        fillMetadata(symbol,symbolMap);
        for (int i = 0;i < symbol.getContents().size();i++)
                storeMetadata(symbol.getContents().get(i));
        }

    /**
     * Fills symbol metadata.
     * @param symbol symbol to analyze.
     * @param symbolMap symbol map in which to store metadata.
     */
    private void fillMetadata(Symbol symbol, Map<String, Object> symbolMap) {
        symbolMap.put("startIndex",symbol.getStartIndex());
        symbolMap.put("endIndex",symbol.getEndIndex());
    }
}
