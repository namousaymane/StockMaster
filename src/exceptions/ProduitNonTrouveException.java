package exceptions;


public class ProduitNonTrouveException extends Exception {

    public ProduitNonTrouveException(String message) {
        super(message);
    }
    public ProduitNonTrouveException(String message, Throwable cause) {
        super(message, cause);
    }
}