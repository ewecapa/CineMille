package lascaux.cinemille.model.dto;

public enum HttpStatusCodeReference {
    BAD_REQUEST(400, "https://www.rfc-editor.org/rfc/rfc9110.html#name-400-bad-request"),
    FORBIDDEN(403, "https://www.rfc-editor.org/rfc/rfc9110.html#name-403-forbidden"),
    NOT_FOUND(404, "https://www.rfc-editor.org/rfc/rfc9110.html#name-404-not-found"),
    CONFLICT(409, "https://www.rfc-editor.org/rfc/rfc9110.html#name-409-conflict"),
    INTERNAL_SERVER_ERROR(500, "https://www.rfc-editor.org/rfc/rfc9110.html#name-500-internal-server-error");

    private final int statusCode;
    private final String reference;

    HttpStatusCodeReference(int statusCode, String reference) {
        this.statusCode = statusCode;
        this.reference = reference;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReference() {
        return reference;
    }

    public static String getValue(int statusCode) {
        for (HttpStatusCodeReference code : values()) {
            if (code.getStatusCode() == statusCode) {
                return code.getReference();
            }
        }
        // Return an empty string if the status code is not present in the enum
        return "";
    }
}
