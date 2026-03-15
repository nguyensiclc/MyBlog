import { defineStore } from 'pinia';
import { ref } from 'vue';
import { API_BASE } from '../api/config';

export const useTopicsStore = defineStore('topics', () => {
  const tree = ref([]);
  const loading = ref(false);
  const error = ref(null);

  async function fetchTopics() {
    loading.value = true;
    error.value = null;
    try {
      const res = await fetch(`${API_BASE}/api/topics`);
      if (!res.ok) throw new Error('Không tải được chủ đề');
      tree.value = await res.json();
    } catch (e) {
      error.value = e.message || 'Lỗi kết nối';
      tree.value = [];
    } finally {
      loading.value = false;
    }
  }

  /** Flatten tree to list (roots first, then children) for admin list. */
  function getFlatList() {
    const out = [];
    for (const r of tree.value) {
      out.push({ ...r, level: 1 });
      for (const c of r.children || []) {
        out.push({ ...c, level: 2 });
      }
    }
    return out;
  }

  return {
    tree,
    loading,
    error,
    fetchTopics,
    getFlatList
  };
});
