package ca.ulaval.glo4002.trading.interfaces.rest.filters;

import ca.ulaval.glo4002.trading.infrastructure.repositories.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.trading.infrastructure.repositories.EntityManagerProvider;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EntityManagerContextFilter implements Filter {
    private EntityManagerFactory entityManagerFactory;

    public void init(FilterConfig _filterConfig) {
        entityManagerFactory = EntityManagerFactoryProvider.getFactory();
    }

    public void doFilter(
            ServletRequest _request,
            ServletResponse _response,
            FilterChain _chain) throws IOException, ServletException {
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            EntityManagerProvider.setEntityManager(entityManager);
            _chain.doFilter(_request, _response);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            EntityManagerProvider.clearEntityManager();
        }
    }

    public void destroy() {
        entityManagerFactory.close();
    }
}
