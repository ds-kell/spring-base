package vn.com.dsk.demo.base.shared.constants;

public class SecurityConstants {

    public static final String JWT_SECRET = "NGUYENTUANTRUYEN132001MYYOUTHROMANTICCOMEDYISWRONGASIEXPECTEDBTT19112001";

    public static final long ACCESS_TOKEN_EXPIRATION = 86400000;

    public static final long REFRESH_TOKEN_EXPIRATION = 30L * 86400000;

    public static final long RESET_TOKEN_EXPIRATION = 1000 * 60 * 5;

    private SecurityConstants(){}
}