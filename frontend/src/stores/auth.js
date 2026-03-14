import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

const AUTH_KEY = 'auth';
const API_BASE = 'http://localhost:8080';

export const useAuthStore = defineStore('auth', () => {
  const token = ref(null);
  const username = ref(null);
  const roles = ref([]);
  const isAdmin = ref(false);
  const profile = ref(null);

  const isLoggedIn = computed(() => !!token.value);
  const avatarPath = computed(() => profile.value?.avatarPath || null);
  /** Display name shown in navbar etc.: displayName if set, else username */
  const displayNameOrUsername = computed(() => {
    const name = profile.value?.displayName?.trim();
    return name || username.value || '';
  });
  const roleLabel = computed(() => {
    if (isAdmin.value) return 'Quản trị viên';
    return 'Người dùng';
  });

  function initFromStorage() {
    try {
      const raw = localStorage.getItem(AUTH_KEY);
      if (!raw) return;
      const data = JSON.parse(raw);
      token.value = data.accessToken || null;
      username.value = data.username || null;
      roles.value = Array.isArray(data.roles) ? data.roles : [];
      isAdmin.value = !!data.isAdmin;
    } catch {
      token.value = null;
      username.value = null;
      roles.value = [];
      isAdmin.value = false;
    }
  }

  function setAuth(data) {
    token.value = data.accessToken || null;
    username.value = data.username || null;
    roles.value = Array.isArray(data.roles) ? data.roles : [];
    isAdmin.value = !!data.isAdmin;
    localStorage.setItem(AUTH_KEY, JSON.stringify(data));
  }

  function logout() {
    token.value = null;
    username.value = null;
    roles.value = [];
    isAdmin.value = false;
    profile.value = null;
    localStorage.removeItem(AUTH_KEY);
  }

  function getAuthHeader() {
    return token.value ? { Authorization: `Bearer ${token.value}` } : {};
  }

  async function fetchProfile() {
    if (!token.value) return;
    try {
      const res = await fetch(`${API_BASE}/api/users/me`, { headers: getAuthHeader() });
      if (res.ok) profile.value = await res.json();
    } catch {
      profile.value = null;
    }
  }

  function setProfile(p) {
    profile.value = p;
  }

  initFromStorage();

  return {
    token,
    username,
    roles,
    isAdmin,
    profile,
    isLoggedIn,
    avatarPath,
    displayNameOrUsername,
    roleLabel,
    initFromStorage,
    setAuth,
    logout,
    getAuthHeader,
    fetchProfile,
    setProfile
  };
});
