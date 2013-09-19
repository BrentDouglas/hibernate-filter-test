package model;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Brent Douglas <brent.n.douglas@gmail.com>
 */
@Entity
@DiscriminatorValue("b")
@FilterDef(name = "filter", parameters = @ParamDef(name = "c", type = "integer"))
@Filters({@Filter(name = "filter", condition = " (c is null or c = :c ) ")})
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
