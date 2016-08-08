package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderedHashMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 4066678094885745030L;

	private List<K> keyOrder;

	public OrderedHashMap() {
		super();
		this.keyOrder = new ArrayList<K>();
	}

	public V get(int index) {
		return get(keyOrder.get(index));
	}

	@Override
	public V put(K key, V value) {
		keyOrder.add(key);
		return super.put(key, value);
	}

	@Override
	public V remove(Object key) {
		keyOrder.remove(key);
		return super.remove(key);
	}

	@Override
	public boolean remove(Object key, Object value) {
		if (super.remove(key, value)) {
			keyOrder.remove(key);
		}
		return false;
	}

}
