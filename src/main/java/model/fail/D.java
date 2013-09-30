package model.fail;

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
@Table(name = "d", schema = "public")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "prefix")
@DiscriminatorValue("d")
@NamedQueries({
        @NamedQuery(name = "D.all", query = "select d from D d"),
        @NamedQuery(name = "D.withCode",
                query = "select d from D d where lower(d.code) = trim(lower(:code)) "
                        + "or (lower(d.code) || lower(d.prefix)) = trim(lower(:code))"),
        @NamedQuery(name = "D.search",
                query = "select d from D d where (lower(d.code) || lower(d.prefix)) like '%' || lower(:value) || '%' order by d.code")
})
public class D {

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

    public D setPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Column(name = "code", nullable = false)
    public String getCode() {
        return code;
    }

    public D setCode(final String code) {
        this.code = code;
        return this;
    }
}
