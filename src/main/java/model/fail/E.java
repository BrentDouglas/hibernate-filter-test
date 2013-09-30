package model.fail;

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
@DiscriminatorValue("e")
@FilterDef(name = "f_filter", parameters = @ParamDef(name = "f", type = "integer"))
@Filters({@Filter(name = "f_filter", condition = " (f is null or f = :f ) ")})
public class E extends D {

    private F f;

    @ManyToOne
    @JoinColumn(name = "f", nullable = false)
    public F getF() {
        return f;
    }

    public E setF(final F f) {
        this.f = f;
        return this;
    }
}
