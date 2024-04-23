package com.chuanchuan.seata.storage.dao;

import com.chuanchuan.seata.storage.vo.StorageModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public class StorageDAOImpl implements StorageDAO {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Session getH5Session() {
        Session s = null;
        try {
            s = entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
        } catch (Exception err) {
            s = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        }
        return s;
    }

    @Override
    public String saveStorage(StorageModel sm) {
        this.getH5Session().save(sm);
        return "DAO save Storage is ok.";
    }

}
