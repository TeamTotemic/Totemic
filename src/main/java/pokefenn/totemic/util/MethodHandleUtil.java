package pokefenn.totemic.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

import cpw.mods.modlauncher.api.INameMappingService.Domain;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

/**
 * Some utility methods for getting MethodHandles for private or obfuscated methods
 */
public final class MethodHandleUtil {
    private static final Lookup lookup = MethodHandles.lookup();

    public static MethodHandle findMethod(Class<?> clazz, String srgName, MethodType type) {
        try {
            String name = ObfuscationReflectionHelper.remapName(Domain.METHOD, srgName);
            return getPrivateLookup(clazz).findVirtual(clazz, name, type);
        }
        catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static MethodHandle findStaticMethod(Class<?> clazz, String srgName, MethodType type) {
        try {
            String name = ObfuscationReflectionHelper.remapName(Domain.METHOD, srgName);
            return getPrivateLookup(clazz).findStatic(clazz, name, type);
        }
        catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static MethodHandle findConstructor(Class<?> clazz, MethodType type) {
        try {
            return getPrivateLookup(clazz).findConstructor(clazz, type);
        }
        catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static VarHandle findField(Class<?> clazz, String srgName, Class<?> type) {
        try {
            String name = ObfuscationReflectionHelper.remapName(Domain.FIELD, srgName);
            return getPrivateLookup(clazz).findVarHandle(clazz, name, type);
        }
        catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static VarHandle findStaticField(Class<?> clazz, String srgName, Class<?> type) {
        try {
            String name = ObfuscationReflectionHelper.remapName(Domain.FIELD, srgName);
            return getPrivateLookup(clazz).findStaticVarHandle(clazz, name, type);
        }
        catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static Lookup getPrivateLookup(Class<?> clazz) {
        try {
            return MethodHandles.privateLookupIn(clazz, lookup);
        }
        catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
