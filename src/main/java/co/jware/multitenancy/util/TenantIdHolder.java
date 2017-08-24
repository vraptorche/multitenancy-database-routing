package co.jware.multitenancy.util;

public class TenantIdHolder {
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static String get() {
        return holder.get();
    }

    public static void set(String tenantId) {
        if (null == tenantId) {
            throw new NullPointerException();
        }
        holder.set(tenantId);
    }

    public static void clear() {
        holder.remove();
    }
}
