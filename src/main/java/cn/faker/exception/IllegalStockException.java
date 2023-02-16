package cn.faker.exception;

public class IllegalStockException extends RuntimeException {
    public IllegalStockException() {
        super("库存不足");
    }
}
