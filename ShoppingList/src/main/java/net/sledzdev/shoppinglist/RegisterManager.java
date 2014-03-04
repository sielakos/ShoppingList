package net.sledzdev.shoppinglist;

/**
 * Created by mariusz on 24.02.14.
 */
public interface RegisterManager {
    void register(String name, Object handler);
    void unregisterAll();
}
