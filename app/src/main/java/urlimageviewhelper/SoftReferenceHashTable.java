package urlimageviewhelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

public class SoftReferenceHashTable<K,V> {
    @NotNull
    Hashtable<K, SoftReference<V>> mTable = new Hashtable<K, SoftReference<V>>();
    
    @Nullable
    public V put(K key, V value) {
        SoftReference<V> old = mTable.put(key, new SoftReference<V>(value));
        if (old == null)
            return null;
        return old.get();
    }
    
    @Nullable
    public V get(K key) {
        SoftReference<V> val = mTable.get(key);
        if (val == null)
            return null;
        V ret = val.get();
        if (ret == null)
            mTable.remove(key);
        return ret;
    }
    
    @Nullable
    public V remove(K k) {
        SoftReference<V> v = mTable.remove(k);
        if (v == null)
            return null;
        return v.get();
    }
}
