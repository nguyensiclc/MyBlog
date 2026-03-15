import { defineStore } from 'pinia';
import { ref } from 'vue';
import { useAuthStore } from './auth';
import { API_BASE } from '../api/config';

const STORAGE_KEY = 'about_me';

function loadFromStorage() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return { content: '' };
    const data = JSON.parse(raw);
    return { content: data.content ?? '' };
  } catch {
    return { content: '' };
  }
}

function saveToStorage(data) {
  try {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
  } catch (_) {}
}

export const useAboutStore = defineStore('about', () => {
  const content = ref('');
  const loading = ref(false);
  const saveError = ref(null);

  async function init() {
    loading.value = true;
    saveError.value = null;
    try {
      const res = await fetch(`${API_BASE}/api/about`);
      if (res.ok) {
        const data = await res.json();
        content.value = data.content ?? '';
      } else {
        const fallback = loadFromStorage();
        content.value = fallback.content ?? '';
      }
    } catch (_) {
      const fallback = loadFromStorage();
      content.value = fallback.content ?? '';
    }
    loading.value = false;
  }

  async function save() {
    saveError.value = null;
    const authStore = useAuthStore();
    loading.value = true;
    try {
      const res = await fetch(`${API_BASE}/api/about`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          ...authStore.getAuthHeader()
        },
        body: JSON.stringify({ content: content.value })
      });
      if (res.ok) {
        const data = await res.json();
        content.value = data.content ?? content.value;
        saveToStorage({ content: content.value });
        loading.value = false;
        return true;
      }
      saveError.value = res.statusText || 'Lưu thất bại';
    } catch (e) {
      saveError.value = e?.message || 'Lỗi kết nối';
    }
    loading.value = false;
    return false;
  }

  init();

  return {
    content,
    loading,
    saveError,
    init,
    save
  };
});
