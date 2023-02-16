package cn.faker.exception;

public class IllegalBalanceException extends RuntimeException {
    public IllegalBalanceException(){
        super("余额不足");
    }
}
