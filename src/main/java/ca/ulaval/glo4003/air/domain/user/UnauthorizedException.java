package ca.ulaval.glo4003.air.domain.user;

public class UnauthorizedException extends Exception {
    // 418 842 0539
    public UnauthorizedException(String message) {
        super(message);
    }
}
