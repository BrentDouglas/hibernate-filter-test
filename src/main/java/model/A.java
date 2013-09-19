package model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Brent Douglas <brent.n.douglas@gmail.com>
 */
@Entity
@Table(name = "a", schema = "public")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "prefix")
@DiscriminatorValue("a")
@NamedQueries({
        @NamedQuery(name = "A.all", query = "select a from A a"),
        @NamedQuery(name = "A.withCode",
                query = "select a from A a where lower(a.code) = trim(lower(:code)) "
                        + "or (lower(a.code) || lower(a.prefix)) = trim(lower(:code))"),
        @NamedQuery(name = "A.search",
                query = "select a from A a where (lower(a.code) || lower(a.prefix)) like '%' || lower(:value) || '%' order by a.code")
})
public class A {

    private int id;
    protected String prefix;
    protected String code;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "prefix", nullable = false, insertable = false, updatable = false)
    public String getPrefix() {
        return prefix;
    }

    public A setPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Column(name = "code", nullable = false)
    public String getCode() {
        return code;
    }

    public A setCode(final String code) {
        this.code = code;
        return this;
    }
}
