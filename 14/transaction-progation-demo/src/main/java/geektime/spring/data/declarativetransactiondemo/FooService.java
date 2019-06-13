package geektime.spring.data.declarativetransactiondemo;

public interface FooService {
    void insertThenRollback() throws RollbackException;
    void invokeInsertThenRollback();
}
