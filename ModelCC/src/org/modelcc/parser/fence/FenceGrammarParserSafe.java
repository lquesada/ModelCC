/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.modelcc.lexer.lamb.LexicalGraph;
import org.modelcc.lexer.lamb.Token;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.Grammar;
import org.modelcc.language.syntax.Rule;

/**
 * Fence Grammar Parser
 * @author elezeta
 * @serial
 */
public final class FenceGrammarParserSafe implements Serializable {

    /**
     * The already generated handles.
     */
    Map<ParsedSymbol,Set<WaitingHandle>> doneHandles;
    
    /**
     * The waiting handle pool.
     */
    Stack<WaitingHandle> waitingHandlePool;
    
    /**
     * The cores.
     */
    Map<ParsedSymbol,Map<Object,Set<Handle>>> cores;
    
    /**
     * The grammar.
     */
    Grammar g;
    
    /**
     * The preceding map.
     */
    Map<ParsedSymbol,Set<ParsedSymbol>> preceding;
    
    /**
     * The following map.
     */
    Map<ParsedSymbol,Set<ParsedSymbol>> following;
    
    /**
     * The symbol set.
     */
    Set<ParsedSymbol> symbolSet;
    
    /**
     * The start symbols.
     */
    Set<ParsedSymbol> starts; 

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Adds a preceding/following relationship between parsed symbols t1 and t2.
     * @param t1 the preceding parsed symbol.
     * @param t2 the following parsed symbol.
     */
    public void addPreceding(ParsedSymbol t1,ParsedSymbol t2) {
        addPFElement(preceding,t2,t1);
        addPFElement(following,t1,t2);
    }

    /**
     * Removes a preceding/following relationship between parsed symbols t1 and t2.
     * @param t1 the preceding parsed symbol.
     * @param t2 the following parsed symbol.
     */
    public void removePreceding(ParsedSymbol t1,ParsedSymbol t2) {
        removePFElement(preceding,t2,t1);
        removePFElement(following,t1,t2);
    }

    /**
     * Adds a parsed symbol to the set of another key parsed symbol.
     * @param t1 the key parsed symbol.
     * @param t2 the parsed symbol to be added.
     */
    private void addPFElement(Map<ParsedSymbol,Set<ParsedSymbol>> target,ParsedSymbol t1,ParsedSymbol t2) {
        Set<ParsedSymbol> set = target.get(t1);
        if (set == null) {
            set = new HashSet<ParsedSymbol>();
            target.put(t1,set);
        }
        set.add(t2);
    }

    /**
     * Removes a parsed symbol from the set of another key parsed symbol.
     * @param t1 the key parsed symbol.
     * @param t2 the parsed symbol to be removed.
     */
    private void removePFElement(Map<ParsedSymbol,Set<ParsedSymbol>> target,ParsedSymbol t1,ParsedSymbol t2) {
        Set<ParsedSymbol> set = target.get(t1);
        if (set == null) {
            return;
        }
        set.remove(t2);
        if (set.isEmpty())
            target.remove(t1);
    }

    /**
     * Performs the parse of a lexical graph
     * @param g the grammar
     * @param lg the lexical graph
     * @return a parsed graph
     */
    public ParsedGraph parse(Grammar g,LexicalGraph lg) {
        ////out("PARSE");

        //OPTIMIZED Map<ParsedSymbol,Set<Object>> singleTypeHistories = new HashMap<ParsedSymbol,Set<Object>>();
        preceding = new HashMap<ParsedSymbol,Set<ParsedSymbol>>(256);
        following = new HashMap<ParsedSymbol,Set<ParsedSymbol>>(256);
        symbolSet = new HashSet<ParsedSymbol>(256);
        starts = new HashSet<ParsedSymbol>(256);

        // -------------
        // Conversion to Syntactic Graph.
        // -------------

        {

            // INITIALIZATION
            // --------------

            // Auxiliar variables
            Iterator<Token> ite;
            Iterator<Token> ite2;
            ParsedSymbol s;
            Token t;
            Iterator<ParsedSymbol> itep;

            // Token -> ParsedSymbol correspondence map.
            Map<Token,ParsedSymbol> ttos = new HashMap<Token,ParsedSymbol>();


            // PROCEDURE
            // --------------

            // Generates correspondence map.
            for (ite = lg.getTokens().iterator();ite.hasNext();) {
                t = ite.next();
                s = new ParsedSymbol(t.getType(),t.getStartIndex(),t.getEndIndex(),t.getString());
                s.setUserData(t.getUserData());
                System.out.println("------------------------------------------- TOKEN is " +t.getType()+"("+t.getString()+") at "+t.getStartIndex()+"-"+t.getEndIndex());
                ttos.put(t,s);
                symbolSet.add(s);
            }

            // Completes preceding/following sets.
            for (ite = lg.getTokens().iterator();ite.hasNext();) {
                t = ite.next();
                s = ttos.get(t);
                Set<Token> prec = lg.getPreceding().get(t);
                if (prec != null)
                    for (ite2 = prec.iterator();ite2.hasNext();)
                        addPreceding(ttos.get(ite2.next()),s);
            }

            for (itep = symbolSet.iterator();itep.hasNext();) {
                s = itep.next();
                if (s.getType().equals(g.getStartType()))
                    if (preceding.get(s) == null && following.get(s) == null)
                        starts.add(s);
            }
        }

        // -------------
        // Parsing.
        // -------------

        {

            // INITIALIZATION
            // --------------

            // Auxiliar variables
            Iterator<Rule> iter;
            Rule r;
            Iterator<ParsedSymbol> ites;
            ParsedSymbol s;
            WaitingHandle wh;
            Object nextType;
            int skip;

            // Grammar.
            this.g = g;
            
            // Cores.
            cores = new HashMap<ParsedSymbol,Map<Object,Set<Handle>>>(256);
            for (ites = symbolSet.iterator();ites.hasNext();) {
                cores.put(ites.next(),new HashMap<Object,Set<Handle>>());
            }

            // Waiting handle pool
            waitingHandlePool = new Stack<WaitingHandle>();

            // Done handles
            doneHandles = new HashMap<ParsedSymbol,Set<WaitingHandle>>(256);

            // PROCEDURE
            // --------------

            // Adds a handle with each rule to each core.
            for (ites = symbolSet.iterator();ites.hasNext();) {
                s = ites.next();
                Set<Rule> startRules = g.getStartRules().get(s.getType());
                if (startRules != null) {
                    for (iter = startRules.iterator();iter.hasNext();) {
                        r = iter.next();
                        //out("Adding init "+r.getLeft().getType()+" has "+s.getType());
                        addHandle(s,r,0,s);
                    }
                }
            }

            // If start symbol is in emptyrules, add it.
            if (g.getEmptyRules().contains(g.getStartType()) && lg.getStart().isEmpty()) {
                s = new ParsedSymbol(g.getStartType(),-1,-1);
                symbolSet.add(s);
                starts.add(s);
            }


            // Processes waiting handle pool.
            while (!waitingHandlePool.isEmpty()) {
                wh = waitingHandlePool.pop();
                //out("");
                //out("");
                //out("START PROCESSING "+wh);
                
                if (wh.getMatched()==wh.getRule().getRight().size()-1) {
                    generateSymbol(g.getStartType(),wh,lg.getInputStart(),lg.getInputEnd());
                }
                else {
                    Set<ParsedSymbol> foll = following.get(wh.getNext());
                    if (foll != null) {
                        for (ites = foll.iterator();ites.hasNext();) {
                            s = ites.next();
                            //out("step");
                            addHandle(s,wh.getRule(),wh.getMatched()+1,wh.getFirst());
                        }
                    }
                    else {
                        skip = 0;
                        do {
                            skip++;
                            nextType = wh.getRule().getRight().get(wh.getMatched()+skip).getType();
                            //System.out.println("Looking for nextType: "+nextType);
                            //System.out.println("Is it in empty rules? "+g.getEmptyRules().contains(nextType));
                            if (g.getEmptyRules().contains(nextType) && wh.getMatched()+skip+1==wh.getRule().getRight().size()) {
                                generateSymbol(g.getStartType(),wh,lg.getInputStart(),lg.getInputEnd());
                            }
                        } while (g.getEmptyRules().contains(nextType) && wh.getMatched()+skip+1<wh.getRule().getRight().size());
                    }
                }
                //out("END PROCESSING "+wh);
            }
            //System.out.println("GENERATED UP TO: "+doneHandles.size());
        }
        
        return new ParsedGraph(symbolSet,starts,preceding,following,g);
    }

    /**
     * Generates a handle and the corresponding waiting handles.
     * @param emptyRules the set of empty rules.
     * @param doneHandles the already generated waiting handles
     * @param waitingHandlePool the pool of waiting handles
     * @param cores the set of cores
     * @param s the next symbol to consider
     * @param r the rule
     * @param matched the next element to match in the rule
     * @param first the first symbol of this match
     */
    private void addHandle(ParsedSymbol s,Rule r,int matched,ParsedSymbol first) {
        Object nextType = r.getRight().get(matched).getType();
        Map<Object,Set<Handle>> thisCore = cores.get(s);
        Handle h;
        int skip = -1;
        do {
            skip++;
            nextType = r.getRight().get(matched+skip).getType();
            //System.out.println("Looking for nextType: "+nextType);
            //System.out.println("Is it in empty rules? "+g.getEmptyRules().contains(nextType));
            Set<Handle> tc = thisCore.get(nextType);
            h = new Handle(r,matched+skip,first.getStartIndex(),first);
            if (tc == null || !tc.contains(h)) {
                Set<Object> fs = g.getFirstStar().get(nextType);
                if (fs != null) {
                    if (fs.contains(s.getType())) {
                        if (tc == null) {
                            tc = new HashSet<Handle>();
                            thisCore.put(nextType,tc);
                        }
                        if (matched+skip != 0)
                            tc.add(h);
                    }
                }
                
                if (nextType.equals(s.getType())) {
                	//System.out.println("Matched!");
                    WaitingHandle w = new WaitingHandle(r,matched+skip,first.getStartIndex(),first,s);
                    Set<WaitingHandle> dh = doneHandles.get(s);
                    if (dh == null) {
                        dh = new HashSet<WaitingHandle>();
                        doneHandles.put(s, dh);
                    }
                    if (!dh.contains(w)) {
                        dh.add(w);
                        waitingHandlePool.add(w);
                    }
                }
            }
        } while (g.getEmptyRules().contains(r.getRight().get(matched+skip).getType()) && matched+skip+1<r.getRight().size());
    }

    /**
     * Generates a symbol
     * @param emptyRules the set of empty rules.
     * @param doneHandles the already generated waiting handles
     * @param waitingHandlePool the pool of waiting handles
     * @param cores the set of cores
     * @param preceding the map of preceding symbols
     * @param following the map of following symbols
     * @param startType the start type of the grammar
     * @param symbolSet the set of symbols generated
     * @param starts the set of start symbols
     * @param wh the waiting handle
     * @param inputstart the input start index
     * @param inputend the input end index
     */
    private void generateSymbol(Object startType,WaitingHandle wh,int inputstart,int inputend) {
        Iterator<Handle> iteh;
        Iterator<ParsedSymbol> ites;
        Iterator<Set<Handle>> itec;
        Handle h;
        Set<ParsedSymbol> aux = new HashSet<ParsedSymbol>();
        ParsedSymbol s;
        Set<Object> singleTypeHistory = new HashSet<Object>();
        Iterator<RuleElement> itee;
        int count = 0;
        for (itee = wh.getRule().getRight().iterator();itee.hasNext();) {
        	RuleElement e = itee.next();
            if (!g.getEmptyRules().contains(e.getType())) {
                count++;
            }
        }
        if (count==1) {
            if (wh.getNext().getSingleTypeHistory() != null)
                singleTypeHistory.addAll(wh.getNext().getSingleTypeHistory());
            if (singleTypeHistory.contains(wh.getRule().getLeft().getType()))
                return; // Avoid cyclic rules!
            singleTypeHistory.add(wh.getRule().getLeft().getType());
        }


        
        s = new ParsedSymbol(wh.getRule().getLeft().getType(),wh.getFirst().getStartIndex(),wh.getNext().getEndIndex(),singleTypeHistory);
        //System.out.println("------------------------------------------- GP GENERATED " +wh.getRule().getLeft().getType()+" in "+wh.getFirst().getStartIndex()+"-"+wh.getNext().getEndIndex());

        if (!symbolSet.contains(s)) {
            symbolSet.add(s);
            cores.put(s,new HashMap<Object,Set<Handle>>());

            Set<ParsedSymbol> prec = preceding.get(wh.getFirst());
            if (prec != null)
                for (ites = prec.iterator();ites.hasNext();)
                    aux.add(ites.next());
            for (ites = aux.iterator();ites.hasNext();)
                addPreceding(ites.next(),s);
            aux.clear();

            Set<ParsedSymbol> foll = following.get(wh.getNext());
            if (foll != null)
                for (ites = foll.iterator();ites.hasNext();)
                    aux.add(ites.next());
            for (ites = aux.iterator();ites.hasNext();)
                addPreceding(s,ites.next());
            aux.clear();

            for (itec = cores.get(wh.getFirst()).values().iterator();itec.hasNext();)
                for (iteh = itec.next().iterator();iteh.hasNext();) {
                    h = iteh.next();
                    //Checking
                    if (h.getMatched()==0)
                        addHandle(s,h.getRule(),h.getMatched(),s);
                    else
                        addHandle(s,h.getRule(),h.getMatched(),h.getFirst());
                }

            if (g.getStartRules().get(s.getType()) != null) {
                for (Iterator<Rule> iter = g.getStartRules().get(s.getType()).iterator();iter.hasNext();) {
                    Rule r = iter.next();
                    //out("Rule "+r);
                    addHandle(s,r,0,s);
                }
            }
            
            if (s.getType().equals(startType)) {
                if (s.getStartIndex()==inputstart && s.getEndIndex()==inputend) {
                    starts.add(s);
                }
            }
        }
    }

}
