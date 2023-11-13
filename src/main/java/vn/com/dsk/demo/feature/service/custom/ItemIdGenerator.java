package vn.com.dsk.demo.feature.service.custom;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import vn.com.dsk.demo.feature.repository.BookDetailRepository;


public class ItemIdGenerator implements IdentifierGenerator {

    private BookDetailRepository bookDetailRepository;
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = "BR";


        return null;
    }
}
