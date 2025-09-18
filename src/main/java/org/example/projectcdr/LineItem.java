package org.example.projectcdr;

public class LineItem {
    public final long seq;
    public final String text;
    public final String producer;
    public final boolean poison;

    public LineItem(long seq, String text, String producer, boolean poison) {
        this.seq = seq;
        this.text = text;
        this.producer = producer;
        this.poison = poison;
    }

    public static LineItem poison() {
        return new LineItem(-1, "", "POISON", true);
    }
}
