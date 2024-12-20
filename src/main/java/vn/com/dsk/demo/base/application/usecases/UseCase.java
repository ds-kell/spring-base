package vn.com.dsk.demo.base.application.usecases;

@FunctionalInterface
public interface UseCase<RESULT, PARAMS> {
    RESULT execute(PARAMS params);
}
