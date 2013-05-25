/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.recognizer.regexp;

import java.io.Serializable;

/**
 * Shifting char sequence that shifts by using references and indexes, thus
 * avoiding copying and it's subsequent overload time.
 * @author elezeta
 * @serial
 */
public class ShiftingCharSequence implements CharSequence,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Original char sequence.
     */
    private CharSequence data;

    /**
     * Start index. Determines the position of data which corresponds to the
     *   zero position of this shifting char sequence.
     */
    private int start;

    /**
     * End index. Determines the position of data which corresponds to the
     *   last position of this shifting char sequence.
     */
    private int end;

    /**
     * Returns the length of the shifting char sequence.
     * @return The length of the shifting char sequence.
     */
    public int length() {
        return getEnd()-getStart();
    }

    /**
     * Returns the character in the index position of the shifting char sequence.
     * @param index Position of the character to return.
     * @return The character in the index position of the shifting char sequence.
     */
    public char charAt(int index) {
        return getData().charAt(getStart()+index);
    }

    /**
     * Subsequences the shifting char sequence by reference.
     * @param start New start index.
     * @param end New end index.
     * @return A new shifting char sequence object which is a subsequence of this one.
     */
    public ShiftingCharSequence subSequence(int start, int end) {
        ShiftingCharSequence n = new ShiftingCharSequence();
        n.setData(getData());
        n.setStart(this.getStart() + start);
        n.setEnd(n.getStart() + (end - start));
        return n;
    }

    /**
     * Virtually removes shiftlength characters from the beginning of this
     * shifting char sequence.
     * @param shiftlength the number of characters to remove from the beggining.
     */
    public void shift(int shiftlength) {
        setStart(getStart() + shiftlength);
    }

    /**
     * Char sequence constructor.
     * @param cs the char sequence to build this shifting char sequence from.
     */
    public ShiftingCharSequence(CharSequence cs) {
        data = cs;
        start = 0;
        end = cs.length();
    }

    /**
     * Char sequence constructor.
     * @param cs the char sequence to build this shifting char sequence from.
     * @param start the start position.
     * @param end the end position.
     */
    public ShiftingCharSequence(CharSequence cs,int start,int end) {
        data = cs;
        this.start = start;
        this.end = end;
        if (this.end>cs.length())
            this.end = cs.length();
    }

    /**
     * Default constructor.
     */
    public ShiftingCharSequence() {
    }

    /**
     * Converts to String.
     * @return the string with the characters of this shifting char sequence.
     */
    @Override
    public String toString() {
        return (String) getData().subSequence(getStart(), getEnd());
    }

    /**
     * @return the data.
     */
    public CharSequence getData() {
        return data;
    }

    /**
     * @param data the data to set.
     */
    public void setData(CharSequence data) {
        this.data = data;
        this.start = 0;
        this.end = data.length();
    }

    /**
     * @return the start index.
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start index to set.
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the end index.
     */
    public int getEnd() {
        return end;
    }

    /**
     * @param end the end index to set.
     */
    public void setEnd(int end) {
        this.end = end;
    }

}
