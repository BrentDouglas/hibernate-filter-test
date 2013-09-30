import junit.framework.AssertionFailedError;
import model.fail.D;
import model.fail.E;
import model.fail.F;
import model.pass.A;
import model.pass.B;
import model.pass.C;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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

    private static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void before() {
        entityManagerFactory = Persistence.createEntityManagerFactory("TestPU");
    }

    @Test
    public void passingTest() throws Exception {
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
            session.enableFilter("c_filter")
                    .setParameter("c", c1id);

            final List<A> as = entityManager.createNamedQuery("A.search", A.class)
                    .setParameter("value", "code")
                    .getResultList();

            for (final A a : as) {
                if (a.getId() == b2id) {
                    Assert.fail();
                }
            }
            Assert.assertEquals(2, as.size());

            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Test(expected = AssertionError.class)
    public void failingTest() throws Exception {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.setFlushMode(FlushModeType.COMMIT);

        int f1id;
        int e2id;

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            final F f1 = new F();
            final F f2 = new F();

            entityManager.persist(f1);
            entityManager.persist(f2);

            f1id = f1.getId();

            final D d = new D().setCode("code");
            final D e1 = new E().setF(f1)
                    .setCode("code");
            final D e2 = new E().setF(f2)
                    .setCode("code");

            entityManager.persist(d);
            entityManager.persist(e1);
            entityManager.persist(e2);

            e2id = e2.getId();

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
            session.enableFilter("f_filter")
                    .setParameter("f", f1id);

            final List<D> ds = entityManager.createNamedQuery("D.search", D.class)
                    .setParameter("value", "code")
                    .getResultList();

            for (final D d : ds) {
                if (d.getId() == e2id) {
                    Assert.fail();
                }
            }

            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @AfterClass
    public static void after() throws Exception {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.setFlushMode(FlushModeType.COMMIT);

        final EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createNativeQuery("delete from public.a").executeUpdate();
            entityManager.createNativeQuery("delete from public.d").executeUpdate();

            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
