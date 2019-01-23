package ca.ulaval.glo4002.trading.infrastructure.repositories;

import javax.persistence.EntityManager;

public class EntityManagerProvider {
    private static ThreadLocal<EntityManager> localEntityManager = new ThreadLocal<>();

    public static EntityManager getEntityManager() {
        return localEntityManager.get();
    }

    public static void setEntityManager(EntityManager _entityManager) {
        localEntityManager.set(_entityManager);
    }

    public static void clearEntityManager() {
        localEntityManager.set(null);
    }
}
