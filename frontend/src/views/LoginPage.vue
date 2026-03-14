<template>
  <main class="login-page">
    <section class="login-hero">
      <div class="login-hero-bg" aria-hidden="true"></div>
      <div class="login-content">
        <div class="login-card animate-in">
          <div class="login-card-glow"></div>
          <div class="login-header">
            <div class="login-icon">
              <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="7" r="4" />
                <path d="M5 21v-2a5 5 0 0 1 5-5h4a5 5 0 0 1 5 5v2" />
              </svg>
            </div>
            <h1 class="login-title">Đăng nhập</h1>
            <p class="login-subtitle">Đăng nhập để quản lý blog và viết bài</p>
          </div>

          <form class="login-form" @submit.prevent="handleLogin">
            <label class="login-label">
              <span class="login-label-text">Tên đăng nhập</span>
              <input
                v-model="username"
                type="text"
                class="login-input"
                placeholder="Nhập username"
                autocomplete="username"
                required
                :disabled="isFormLocked"
              />
            </label>

            <label class="login-label">
              <span class="login-label-text">Mật khẩu</span>
              <div class="login-input-wrap">
                <input
                  v-model="password"
                  :type="showPassword ? 'text' : 'password'"
                  class="login-input"
                  placeholder="Nhập mật khẩu"
                  autocomplete="current-password"
                  required
                  :disabled="isFormLocked"
                />
                <button
                  type="button"
                  class="login-password-toggle"
                  :aria-label="showPassword ? 'Ẩn mật khẩu' : 'Hiện mật khẩu'"
                  @click="showPassword = !showPassword"
                >
                  <svg v-if="!showPassword" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                    <circle cx="12" cy="12" r="3"></circle>
                  </svg>
                  <svg v-else width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path>
                    <line x1="1" y1="1" x2="23" y2="23"></line>
                  </svg>
                </button>
              </div>
            </label>

            <button type="submit" class="login-submit" :disabled="loading || isFormLocked">
              <span v-if="!loading">Đăng nhập</span>
              <span v-else class="login-spinner"></span>
            </button>

            <p v-if="error" class="login-error" :class="{ 'login-error-lockout': isFormLocked }">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="12" y1="8" x2="12" y2="12"></line>
                <line x1="12" y1="16" x2="12.01" y2="16"></line>
              </svg>
              {{ error }}
            </p>
          </form>

          <button type="button" class="login-back" @click="$router.push({ name: 'home' })">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="19" y1="12" x2="5" y2="12"></line>
              <polyline points="12 19 5 12 12 5"></polyline>
            </svg>
            Quay lại trang chủ
          </button>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useAuthStore } from '../stores/auth';

const LOCK_KEY = 'loginFormLock';
const MAX_FAILED = 3;
const LOCK_MINUTES = 5;

function getLockState() {
  try {
    const raw = localStorage.getItem(LOCK_KEY);
    return raw ? JSON.parse(raw) : { failedCount: 0, lockedUntil: null };
  } catch {
    return { failedCount: 0, lockedUntil: null };
  }
}

function setLockState(state) {
  localStorage.setItem(LOCK_KEY, JSON.stringify(state));
}

const authStore = useAuthStore();
const username = ref('');
const password = ref('');
const showPassword = ref(false);
const loading = ref(false);
const error = ref('');
const lockState = ref(getLockState());

const isFormLocked = computed(() => {
  const s = lockState.value;
  return s.lockedUntil != null && Date.now() < s.lockedUntil;
});

const lockMessage = computed(() => {
  const s = lockState.value;
  if (!s.lockedUntil || Date.now() >= s.lockedUntil) return '';
  const remainingMin = Math.ceil((s.lockedUntil - Date.now()) / 60000);
  return `Bạn đã đăng nhập sai ${MAX_FAILED} lần. Chức năng đăng nhập tạm khóa ${LOCK_MINUTES} phút để tránh spam. Thử lại sau ${remainingMin} phút.`;
});

onMounted(() => {
  let s = getLockState();
  if (s.lockedUntil != null && Date.now() >= s.lockedUntil) {
    s = { failedCount: 0, lockedUntil: null };
    setLockState(s);
  }
  lockState.value = s;
  if (isFormLocked.value) {
    error.value = lockMessage.value || `Chức năng đăng nhập tạm khóa ${LOCK_MINUTES} phút.`;
  }
});

function updateLockMessage() {
  if (isFormLocked.value) {
    const s = lockState.value;
    const remainingMin = Math.ceil((s.lockedUntil - Date.now()) / 60000);
    error.value = `Bạn đã đăng nhập sai ${MAX_FAILED} lần. Chức năng đăng nhập tạm khóa ${LOCK_MINUTES} phút. Thử lại sau ${remainingMin} phút.`;
  }
}

async function handleLogin() {
  let s = getLockState();
  if (s.lockedUntil != null && Date.now() >= s.lockedUntil) {
    s = { failedCount: 0, lockedUntil: null };
    setLockState(s);
    lockState.value = s;
  }
  if (isFormLocked.value) {
    updateLockMessage();
    return;
  }

  error.value = '';
  loading.value = true;

  try {
    const res = await fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: username.value,
        password: password.value
      })
    });

    const data = await res.json().catch(() => ({}));

    if (!res.ok) {
      const state = getLockState();
      const newFailed = (state.failedCount || 0) + 1;
      if (newFailed >= MAX_FAILED) {
        const lockedUntil = Date.now() + LOCK_MINUTES * 60 * 1000;
        setLockState({ failedCount: 0, lockedUntil });
        lockState.value = { failedCount: 0, lockedUntil };
        error.value = `Bạn đã đăng nhập sai ${MAX_FAILED} lần. Chức năng đăng nhập tạm khóa ${LOCK_MINUTES} phút. Thử lại sau ${LOCK_MINUTES} phút.`;
      } else {
        setLockState({ failedCount: newFailed, lockedUntil: null });
        lockState.value = { failedCount: newFailed, lockedUntil: null };
        error.value = (data.message || 'Sai tên đăng nhập hoặc mật khẩu.') + ` Bạn còn ${MAX_FAILED - newFailed} lần thử.`;
      }
      return;
    }

    setLockState({ failedCount: 0, lockedUntil: null });
    lockState.value = { failedCount: 0, lockedUntil: null };
    authStore.setAuth(data);
    window.location.href = '/';
  } catch (e) {
    error.value = 'Đăng nhập thất bại. Vui lòng kiểm tra lại.';
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  padding-top: 5rem;
}

.login-hero {
  position: relative;
  padding: 3rem 1.5rem 5rem;
  min-height: calc(100vh - 8rem);
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-hero-bg {
  position: absolute;
  inset: 0;
  background:
    radial-gradient(ellipse 80% 50% at 50% -20%, rgba(16, 185, 129, 0.15), transparent),
    radial-gradient(ellipse 60% 40% at 80% 50%, rgba(16, 185, 129, 0.08), transparent);
  pointer-events: none;
}

.login-content {
  position: relative;
  width: 100%;
  max-width: 400px;
}

.login-card {
  position: relative;
  padding: 2rem;
  background: rgba(20, 21, 31, 0.9);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 1rem;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.4);
  overflow: hidden;
  animation: loginFadeIn 0.6s ease-out;
}

.login-card-glow {
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle at 30% 30%, rgba(16, 185, 129, 0.06) 0%, transparent 50%);
  pointer-events: none;
}

@keyframes loginFadeIn {
  from {
    opacity: 0;
    transform: translateY(16px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.login-header {
  text-align: center;
  margin-bottom: 1.75rem;
}

.login-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 56px;
  height: 56px;
  border-radius: 1rem;
  background: linear-gradient(135deg, rgba(16, 185, 129, 0.2) 0%, rgba(16, 185, 129, 0.05) 100%);
  color: #10b981;
  margin-bottom: 1rem;
}

.login-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #fff;
  margin: 0 0 0.25rem;
}

.login-subtitle {
  font-size: 0.9375rem;
  color: #9ca3af;
  margin: 0;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.login-label {
  display: block;
}

.login-label-text {
  display: block;
  font-size: 0.875rem;
  font-weight: 500;
  color: #d1d5db;
  margin-bottom: 0.5rem;
}

.login-input-wrap {
  position: relative;
}

.login-input-wrap .login-input {
  padding-right: 2.75rem;
}

.login-input {
  width: 100%;
  padding: 0.75rem 1rem;
  font-size: 1rem;
  color: #fff;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 0.5rem;
  transition: border-color 0.2s, background 0.2s;
  box-sizing: border-box;
}

.login-input::placeholder {
  color: #6b7280;
}

.login-input:hover {
  background: rgba(255, 255, 255, 0.06);
  border-color: rgba(255, 255, 255, 0.15);
}

.login-input:focus {
  outline: none;
  border-color: #10b981;
  background: rgba(16, 185, 129, 0.05);
}

.login-input:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.login-password-toggle {
  position: absolute;
  right: 0.5rem;
  top: 50%;
  transform: translateY(-50%);
  padding: 0.35rem;
  color: #9ca3af;
  background: none;
  border: none;
  cursor: pointer;
  border-radius: 0.375rem;
  transition: color 0.2s;
}

.login-password-toggle:hover {
  color: #10b981;
}

.login-submit {
  width: 100%;
  padding: 0.875rem 1.5rem;
  font-size: 1rem;
  font-weight: 600;
  color: #fff;
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.login-submit:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
}

.login-submit:active:not(:disabled) {
  transform: translateY(0);
}

.login-submit:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.login-spinner {
  display: inline-block;
  width: 1.25rem;
  height: 1.25rem;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: #fff;
  border-radius: 50%;
  animation: loginSpin 0.8s linear infinite;
}

@keyframes loginSpin {
  to { transform: rotate(360deg); }
}

.login-error {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.875rem 1rem;
  font-size: 0.875rem;
  color: #fca5a5;
  background: rgba(239, 68, 68, 0.1);
  border: 1px solid rgba(239, 68, 68, 0.2);
  border-radius: 0.75rem;
  margin: 0;
}

.login-error svg {
  flex-shrink: 0;
}

.login-error-lockout {
  background: rgba(239, 68, 68, 0.18);
  border-color: rgba(239, 68, 68, 0.4);
  color: #fecaca;
}

.login-back {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  width: 100%;
  margin-top: 1.25rem;
  padding: 0.65rem;
  font-size: 0.9375rem;
  color: #9ca3af;
  background: none;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 0.5rem;
  cursor: pointer;
  transition: color 0.2s, border-color 0.2s;
}

.login-back:hover {
  color: #10b981;
  border-color: rgba(16, 185, 129, 0.3);
}

@media (max-width: 480px) {
  .login-card {
    padding: 1.5rem;
  }
  .login-title {
    font-size: 1.25rem;
  }
}
</style>
