package model.pass;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Brent Douglas <brent.n.douglas@gmail.com>
 */
@Entity
@DiscriminatorValue("b")
public class B extends A {

    private C c;

    @ManyToOne
    @JoinColumn(name = "c", nullable = false)
    public C getC() {
        return c;
    }

    public B setC(final C c) {
        this.c = c;
        return this;
    }
}
