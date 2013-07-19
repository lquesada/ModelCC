/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.ElementMember;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.metamodel.MultipleElementMember;
import org.modelcc.tools.FieldSearcher;

/**
 * Key Wrapper
 * @author elezeta
 * @serial
 */
public class KeyWrapper {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Wrapped object.
     */
    private Object o;
    
    /**
     * Track used for basic elements.
     */
    private Object track;
    
    /**
     * The model.
     */
    private Model m;
    
    /**
     * Hash code
     */
    private int hash;
    
    /**
     * The map.
     */
    private Map<Object,ObjectWrapper> mymap;

    /**
     * Gets a KeyWrapper
     * @param o the object to wrap
     * @param m the model
     * @param map the object wrappers map.
     * @return the keywrapper.
     */
    static public KeyWrapper getKeyWrapper(Object o,Model m,Map<Object,ObjectWrapper> map) {
        int hash = 0;
        ComplexModelElement me = (ComplexModelElement) m.getClassToElement().get(o.getClass());
        Map<Object,ObjectWrapper> myadded = new HashMap<Object,ObjectWrapper>();
        try {
            for (int i = 0;i < me.getIds().size();i++) {
                ElementMember em = me.getIds().get(i);
                Class c = em.getElementClass();
                Field fld = FieldSearcher.searchField(me.getElementClass(),em.getField());
                fld.setAccessible(true);
                Object val = fld.get(o);
                if (val == null) {
                    hash *= 53;
                }
                else {
                    if (MultipleElementMember.class.isAssignableFrom(em.getClass())) {
                        MultipleElementMember mem = (MultipleElementMember) em;
                        switch (mem.getCollection()) {
                            case LANGARRAY:
                                {
                                    Object[] list = (Object[])val;
                                    for (int j = 0;j < list.length;j++) {
                                        ObjectWrapper ow = ObjectWrapper.getObjectWrapper(list[j],m,map,myadded);
                                        map.put(list[j],ow);
                                        hash += ow.hashCode();
                                        hash *= 53;
                                    }
                                }
                                break;
                            case LIST:
                                {
                                    List list = (List)val;
                                    for (int j = 0;j < list.size();j++) {
                                        ObjectWrapper ow = ObjectWrapper.getObjectWrapper(list.get(j),m,map,myadded);
                                        map.put(list.get(j),ow);
                                        hash += ow.hashCode();
                                        hash *= 53;
                                    }
                                }
                                break;
                            case SET:
                                {
                                    Set<ObjectWrapper> listo = new HashSet<ObjectWrapper>();
                                    Set<Object> list = (Set<Object>)val;
                                    for (Iterator ite = list.iterator();ite.hasNext();) {
                                        Object on = ite.next();
                                        ObjectWrapper ow = ObjectWrapper.getObjectWrapper(on,m,map,myadded);
                                        map.put(on,ow);
                                        listo.add(ow);
                                    }
                                    for (Iterator ite = listo.iterator();ite.hasNext();) {
                                        hash += ite.next().hashCode();
                                    }
                                    hash *= 53;
                                }
                                break;
                        }
                    }
                    else {
                        ObjectWrapper ow = ObjectWrapper.getObjectWrapper(val,m,map,myadded);
                        map.put(val,ow);
                        hash += ow.hashCode();
                        hash *= 53;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        KeyWrapper th = new KeyWrapper(o,m,hash,myadded);
        return th;
        
    }
    
    /**
     * Constructor
     * @param o the object
     * @param m the model.
     * @param hash the hash code.
     * @param track track used for basic elements. 
     */
    public KeyWrapper(Object o,Model m,int hash,Object track) {
        this.o = o;
        this.m = m;
        this.track = track;
        this.hash = hash;
        this.mymap = new HashMap<Object,ObjectWrapper>();
    }

    /**
     * Constructor
     * @param o the object
     * @param m the model.
     * @param hash the hash code.
     * @param mymap my mappings. 
     */
    public KeyWrapper(Object o,Model m,int hash,Map<Object,ObjectWrapper> mymap) {
        this.o = o;
        this.m = m;
        this.hash = hash;
        this.mymap = mymap;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KeyWrapper other = (KeyWrapper) obj;
        if (hashCode()!=other.hashCode()) {
            return false;
        }
        
        ModelElement me = m.getClassToElement().get(o.getClass());
        if (ComplexModelElement.class.isAssignableFrom(me.getClass())) {
            ComplexModelElement cme = (ComplexModelElement) m.getClassToElement().get(o.getClass());
            try {
                for (int i = 0;i < cme.getIds().size();i++) {
                    ElementMember em = cme.getIds().get(i);
                    Class c = em.getElementClass();
                    Field fld = FieldSearcher.searchField(cme.getElementClass(),em.getField());
                    fld.setAccessible(true);
                    Object val = fld.get(o);
                    Object val2 = fld.get(other.o);
                    Map<Object,ObjectWrapper> mymap2 = other.getMymap();
                    if (val == null && val2 == null) {
                    }
                    else if (val != null && val2 == null) {
                        return false;
                    }
                    else if (val == null && val2 != null) {
                        return false;
                    }
                    else {
                        if (MultipleElementMember.class.isAssignableFrom(em.getClass())) {
                            MultipleElementMember mem = (MultipleElementMember) em;
                            switch (mem.getCollection()) {
                                case LANGARRAY:
                                    {
                                        Object[] list = (Object[])val;
                                        Object[] list2 = (Object[])val2;
                                        if (list.length!=list2.length)
                                            return false;
                                        for (int j = 0;j < list.length;j++) {
                                            if (!ObjectWrapper.getObjectWrapper(list[j], m,mymap).equals(ObjectWrapper.getObjectWrapper(list2[j], m,mymap2)))
                                                return false;
                                        }
                                    }
                                    break;
                                case LIST:
                                    {
                                        List list = (List)val;
                                        List list2 = (List)val2;
                                        if (list.size()!=list2.size())
                                            return false;
                                        for (int j = 0;j < list.size();j++) {
                                            if (!ObjectWrapper.getObjectWrapper(list.get(j), m,mymap).equals(ObjectWrapper.getObjectWrapper(list2.get(j), m,mymap2)))
                                                return false;
                                        }
                                    }
                                    break;
                                case SET:
                                    {
                                        Set<Object> list = (Set<Object>)val;
                                        Set<Object> list2 = (Set<Object>)val2;
                                        Set<ObjectWrapper> listo = new HashSet<ObjectWrapper>();
                                        Set<ObjectWrapper> listo2 = new HashSet<ObjectWrapper>();
                                        for (Iterator ite = list.iterator();ite.hasNext();) {
                                            listo.add(ObjectWrapper.getObjectWrapper(ite.next(), m,mymap));
                                        }
                                        for (Iterator ite = list2.iterator();ite.hasNext();) {
                                            listo2.add(ObjectWrapper.getObjectWrapper(ite.next(), m,mymap2));
                                        }
                                        if (listo.size()!=listo2.size())
                                            return false;
                                        for (Iterator ite = listo.iterator();ite.hasNext();) {
                                            if (!listo2.contains(ite.next()))
                                                return false;
                                        }
                                    }
                                    break;
                            }
                        }
                        else {                        
                            if (!ObjectWrapper.getObjectWrapper(val,m,mymap).equals(ObjectWrapper.getObjectWrapper(val2, m,mymap2)))
                                return false;
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(ObjectWrapper.class.getName()).log(Level.SEVERE, null, ex);
            }      
        }
        else {
            if (!track.equals(other.track))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    /**
     * @return the mymap
     */
    public Map<Object,ObjectWrapper> getMymap() {
        return mymap;
    }
    
    
}
