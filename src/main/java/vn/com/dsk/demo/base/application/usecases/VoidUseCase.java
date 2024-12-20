package vn.com.dsk.demo.base.application.usecases;

@FunctionalInterface
public interface VoidUseCase<PARAMS> {
    void execute(PARAMS params);
}