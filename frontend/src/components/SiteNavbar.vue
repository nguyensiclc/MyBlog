<template>
  <nav>
    <div class="nav-container">
      <button class="logo" @click="goHome">
        <div class="logo-icon">
          <LogoIcon :size="20" />
        </div>
        <span class="logo-text">TNC</span>
      </button>
      <div class="nav-links" ref="navLinksRef">
        <template v-for="item in topicsStore.tree" :key="item.id">
          <template v-if="item.children && item.children.length">
            <div class="nav-dropdown-wrap">
              <button type="button" class="nav-link" :class="{ open: openDropdownId === item.id }" @click="toggleDropdown(item.id)">
                {{ item.label }}
                <svg class="nav-chevron" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="6 9 12 15 18 9"/></svg>
              </button>
              <div v-if="openDropdownId === item.id" class="nav-dropdown">
                <button
                  v-for="c in item.children"
                  :key="c.id"
                  type="button"
                  class="nav-link nav-dropdown-item"
                  @click="navigateTo(c.target); openDropdownId = null"
                >
                  {{ c.label }}
                </button>
              </div>
            </div>
          </template>
          <template v-else>
            <button v-if="isHashTarget(item.target)" type="button" class="nav-link" @click="scrollToTarget(item.target)">{{ item.label }}</button>
            <router-link v-else :to="item.target" class="nav-link nav-link-route">{{ item.label }}</router-link>
          </template>
        </template>
      </div>
      <div class="nav-actions">
        <template v-if="authStore.isLoggedIn">
          <div class="nav-user-wrap" ref="userWrapRef">
            <button type="button" class="nav-user-btn" @click="toggleUserMenu" :aria-expanded="userMenuOpen" aria-haspopup="true">
              <span class="nav-user-avatar" :class="{ 'nav-user-avatar-img': avatarUrl }" :title="authStore.displayNameOrUsername">
                <img v-if="avatarUrl" :src="avatarUrl" alt="" />
                <template v-else>{{ userInitials }}</template>
              </span>
              <span class="nav-user-name">{{ authStore.displayNameOrUsername }}</span>
              <span class="nav-user-role-badge">{{ authStore.roleLabel }}</span>
              <svg class="nav-user-chevron" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <polyline points="6 9 12 15 18 9"></polyline>
              </svg>
            </button>
            <div v-if="userMenuOpen" class="nav-user-dropdown" role="menu">
              <button type="button" class="nav-user-item" role="menuitem" @click="goProfile">
                Hồ sơ & Cài đặt
              </button>
              <button v-if="authStore.isAdmin" type="button" class="nav-user-item" role="menuitem" @click="goAdmin">
                Trang quản trị
              </button>
              <button type="button" class="nav-user-item nav-user-item-danger" role="menuitem" @click="handleLogout">
                Đăng xuất
              </button>
            </div>
          </div>
        </template>
        <template v-else>
          <button class="nav-link" @click="goLogin">Đăng nhập</button>
        </template>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { useTopicsStore } from '../stores/topics';
import LogoIcon from './LogoIcon.vue';

const router = useRouter();
const authStore = useAuthStore();
const topicsStore = useTopicsStore();
const userMenuOpen = ref(false);
const userWrapRef = ref(null);
const openDropdownId = ref(null);
const navLinksRef = ref(null);

const API_BASE = 'http://localhost:8080';
const avatarUrl = computed(() =>
  authStore.avatarPath ? `${API_BASE}${authStore.avatarPath}` : null
);
const userInitials = computed(() => {
  const name = (authStore.displayNameOrUsername || authStore.username || '').trim();
  if (!name) return '?';
  const words = name.split(/\s+/);
  if (words.length >= 2)
    return (words[0][0] + words[words.length - 1][0]).toUpperCase();
  return name.length >= 2 ? name.slice(0, 2).toUpperCase() : name[0].toUpperCase();
});

function scrollToId(id) {
  const el = document.getElementById(id);
  if (el) el.scrollIntoView({ behavior: 'smooth' });
}

function isHashTarget(target) {
  return typeof target === 'string' && target.startsWith('#');
}

function scrollToTarget(target) {
  const id = target.replace(/^#/, '');
  scrollToId(id);
}

function navigateTo(target) {
  if (isHashTarget(target)) {
    scrollToTarget(target);
  } else {
    router.push(target);
  }
}

function toggleDropdown(id) {
  openDropdownId.value = openDropdownId.value === id ? null : id;
}

function goHome() {
  if (router.currentRoute.value.name === 'home') {
    scrollToId('top');
  } else {
    router.push({ name: 'home' });
  }
}

function goLogin() {
  router.push({ name: 'login' });
}

function goProfile() {
  userMenuOpen.value = false;
  router.push({ name: 'profile' });
}

function goAdmin() {
  userMenuOpen.value = false;
  router.push({ path: '/admin' });
}

function toggleUserMenu() {
  userMenuOpen.value = !userMenuOpen.value;
}

function handleLogout() {
  userMenuOpen.value = false;
  authStore.logout();
  router.push({ name: 'home' });
}

function onDocumentClick(e) {
  if (userWrapRef.value && !userWrapRef.value.contains(e.target)) {
    userMenuOpen.value = false;
  }
  if (navLinksRef.value && !navLinksRef.value.contains(e.target)) {
    openDropdownId.value = null;
  }
}

onMounted(() => {
  document.addEventListener('click', onDocumentClick);
  if (authStore.isLoggedIn) authStore.fetchProfile();
  topicsStore.fetchTopics();
});
onBeforeUnmount(() => {
  document.removeEventListener('click', onDocumentClick);
});
</script>

<style scoped>
.nav-user-wrap {
  position: relative;
}
.nav-user-btn {
  display: flex;
  align-items: center;
  gap: 0.35rem;
  padding: 0.5rem 0.75rem;
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 0.5rem;
  color: #d1d5db;
  font-size: 0.9375rem;
  cursor: pointer;
  transition: background 0.2s, border-color 0.2s;
}
.nav-user-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: rgba(16, 185, 129, 0.3);
  color: #fff;
}
.nav-user-btn:hover .nav-user-avatar {
  box-shadow: 0 0 0 2px rgba(16, 185, 129, 0.5), 0 0 16px rgba(16, 185, 129, 0.25);
  transform: scale(1.05);
}
.nav-user-avatar {
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: #fff;
  font-size: 0.75rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  letter-spacing: -0.02em;
  box-shadow: 0 0 0 2px rgba(255, 255, 255, 0.12), 0 2px 8px rgba(0, 0, 0, 0.3);
  transition: transform 0.2s, box-shadow 0.2s;
  overflow: hidden;
}
.nav-user-avatar.nav-user-avatar-img {
  background: #14151f;
  padding: 0;
}
.nav-user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.nav-user-name {
  max-width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.nav-user-role-badge {
  font-size: 0.6875rem;
  font-weight: 600;
  color: #10b981;
  background: rgba(16, 185, 129, 0.15);
  padding: 0.2rem 0.5rem;
  border-radius: 9999px;
  flex-shrink: 0;
  border: 1px solid rgba(16, 185, 129, 0.3);
}
.nav-user-chevron {
  flex-shrink: 0;
  opacity: 0.8;
  transition: transform 0.2s;
}
.nav-user-btn[aria-expanded="true"] .nav-user-chevron {
  transform: rotate(180deg);
}
.nav-user-dropdown {
  position: absolute;
  top: calc(100% + 0.25rem);
  right: 0;
  min-width: 180px;
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 0.75rem;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.4);
  padding: 0.25rem;
  z-index: 100;
}
.nav-user-item {
  display: block;
  width: 100%;
  padding: 0.6rem 0.75rem;
  text-align: left;
  font-size: 0.9375rem;
  color: #d1d5db;
  background: none;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
}
.nav-user-item:hover {
  background: rgba(255, 255, 255, 0.06);
  color: #10b981;
}
.nav-user-item-danger:hover {
  color: #fca5a5;
}

.nav-dropdown-wrap {
  position: relative;
}
.nav-dropdown-wrap .nav-link {
  display: inline-flex;
  align-items: center;
  gap: 0.25rem;
}
.nav-chevron {
  flex-shrink: 0;
  opacity: 0.8;
  transition: transform 0.2s;
}
.nav-dropdown-wrap .nav-link.open .nav-chevron {
  transform: rotate(180deg);
}
.nav-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  margin-top: 0.25rem;
  min-width: 160px;
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 0.5rem;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.3);
  padding: 0.25rem;
  z-index: 50;
}
.nav-dropdown-item {
  display: block;
  width: 100%;
  text-align: left;
  padding: 0.5rem 0.75rem;
  border-radius: 0.375rem;
}
.nav-dropdown-item:hover {
  background: rgba(255, 255, 255, 0.06);
}
</style>
