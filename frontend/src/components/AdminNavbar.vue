<template>
  <nav class="admin-topbar">
    <div class="admin-topbar-inner">
      <div class="admin-topbar-search">
        <svg class="admin-topbar-search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
        </svg>
        <input
          type="search"
          class="admin-topbar-search-input"
          placeholder="Tìm kiếm..."
          aria-label="Tìm kiếm"
        />
      </div>
      <div class="admin-topbar-actions">
        <router-link to="/" class="admin-topbar-icon-btn" title="Về trang chủ">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/>
          </svg>
        </router-link>
        <button type="button" class="admin-topbar-icon-btn" title="Thông báo" aria-label="Thông báo">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/>
          </svg>
        </button>
        <button type="button" class="admin-topbar-icon-btn" title="Làm mới" aria-label="Làm mới" @click="$emit('refresh')">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="23 4 23 10 17 10"/><path d="M20.49 15a9 9 0 1 1-2.12-9.36L23 10"/>
          </svg>
        </button>
        <router-link to="/profile" class="admin-topbar-icon-btn" title="Trợ giúp / Cài đặt" aria-label="Cài đặt">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"/>
          </svg>
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script setup>
defineEmits(['refresh']);
</script>

<style scoped>
.admin-topbar {
  position: fixed;
  top: 0;
  left: 240px;
  right: 0;
  height: 4rem;
  z-index: 40;
  background: rgba(20, 21, 31, 0.95);
  backdrop-filter: blur(12px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
}
@media (max-width: 768px) {
  .admin-topbar { left: 64px; }
}
.admin-topbar-inner {
  max-width: 100%;
  height: 100%;
  margin: 0 auto;
  padding: 0 1.5rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}
.admin-topbar-search {
  flex: 1;
  max-width: 320px;
  position: relative;
  display: flex;
  align-items: center;
}
.admin-topbar-search-icon {
  position: absolute;
  left: 0.75rem;
  color: #6b7280;
  pointer-events: none;
}
.admin-topbar-search-input {
  width: 100%;
  height: 2.25rem;
  padding: 0 0.75rem 0 2.25rem;
  font-size: 0.875rem;
  color: #fff;
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 0.5rem;
  outline: none;
  transition: border-color 0.2s;
}
.admin-topbar-search-input::placeholder { color: #6b7280; }
.admin-topbar-search-input:focus { border-color: #10b981; }
.admin-topbar-actions {
  display: flex;
  align-items: center;
  gap: 0.25rem;
}
.admin-topbar-icon-btn {
  width: 2.25rem;
  height: 2.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  color: #9ca3af;
  background: transparent;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  text-decoration: none;
  transition: color 0.2s, background 0.2s;
}
.admin-topbar-icon-btn:hover { color: #10b981; background: rgba(16, 185, 129, 0.1); }
</style>
