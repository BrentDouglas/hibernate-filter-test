import model.A;
import model.B;
import model.C;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import java.util.List;

/**
 * @author Brent Douglas <brent.n.douglas@gmail.com>
 */
public class FilterTest {

    @Test
    public void test() throws Exception {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestPU");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.setFlushMode(FlushModeType.COMMIT);

        int c1id;
        int b2id;

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            final C c1 = new C();
            final C c2 = new C();

            entityManager.persist(c1);
            entityManager.persist(c2);

            c1id = c1.getId();

            final A a = new A().setCode("code");
            final A b1 = new B().setC(c1)
                    .setCode("code");
            final A b2 = new B().setC(c2)
                    .setCode("code");

            entityManager.persist(a);
            entityManager.persist(b1);
            entityManager.persist(b2);

            b2id = b2.getId();

            entityManager.flush();
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        }

        transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            final Session session = (Session) entityManager.getDelegate();
            session.enableFilter("filter")
                    .setParameter("c", c1id);

            final List<A> as = entityManager.createNamedQuery("A.search", A.class)
                    .setParameter("value", "code")
                    .getResultList();

            for (final A a : as) {
                if (a.getId() == b2id) {
                    Assert.fail();
                }
            }

            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
