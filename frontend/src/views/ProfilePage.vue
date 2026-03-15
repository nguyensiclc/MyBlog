<template>
  <main class="profile-page">
    <section class="profile-hero">
      <div class="profile-hero-bg" aria-hidden="true"></div>
      <div class="profile-content">
        <div class="profile-card animate-in">
          <div class="profile-card-glow"></div>
          <div class="profile-card-header">
            <div class="profile-card-header-text">
              <h1 class="profile-title">Hồ sơ & Cài đặt</h1>
              <p class="profile-subtitle">Thay đổi thông tin và cài đặt website theo ý bạn</p>
            </div>
            <button
              type="button"
              class="profile-card-close"
              aria-label="Đóng"
              @click="$router.push({ name: 'home' })"
            >
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="18" y1="6" x2="6" y2="18"></line>
                <line x1="6" y1="6" x2="18" y2="18"></line>
              </svg>
            </button>
          </div>

          <div v-if="loadError" class="profile-error">
            {{ loadError }}
          </div>

          <template v-else-if="profile">
            <form class="profile-form" @submit.prevent="handleSave">
              <div class="profile-form-grid">
              <div class="profile-section profile-section-avatar">
                <h2 class="profile-section-title">Ảnh đại diện</h2>
                <div
                  class="profile-avatar-fb"
                  :class="{ 'is-uploading': uploadingAvatar }"
                  @click="!uploadingAvatar && (avatarInputRef?.click())"
                >
                  <input
                    ref="avatarInputRef"
                    type="file"
                    accept="image/jpeg,image/png,image/gif,image/webp"
                    class="profile-avatar-input"
                    @change="handleAvatarSelect"
                  />
                  <div class="profile-avatar-fb-photo">
                    <img v-if="avatarDisplayUrl" :src="avatarDisplayUrl" alt="" />
                    <span v-else class="profile-avatar-initials">{{ profileInitials }}</span>
                  </div>
                  <div class="profile-avatar-fb-overlay" v-if="!uploadingAvatar">
                    <span class="profile-avatar-fb-icon">
                      <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"></path>
                        <circle cx="12" cy="13" r="4"></circle>
                      </svg>
                    </span>
                    <span class="profile-avatar-fb-text">Thay ảnh đại diện</span>
                  </div>
                  <div class="profile-avatar-fb-loading" v-if="uploadingAvatar">
                    <span class="profile-avatar-fb-spinner"></span>
                    <span>Đang tải...</span>
                  </div>
                </div>

                <AvatarCropModal
                  :show="showCropModal"
                  :image-url="cropImageUrl"
                  :image-file="cropImageFile"
                  :uploading="uploadingAvatar"
                  @cancel="closeCropModal"
                  @confirm="uploadCroppedAvatar"
                />
                <p class="profile-hint profile-avatar-hint">Nhấn vào ảnh để chọn file (JPEG, PNG, GIF, WebP)</p>
                <p v-if="avatarError" class="profile-error">{{ avatarError }}</p>
              </div>

              <div class="profile-section">
                <h2 class="profile-section-title">Thông tin cá nhân</h2>
                <p class="profile-hint">Username: {{ profile.username }} · Vai trò: {{ profile.admin ? 'Quản trị viên' : 'Người dùng' }}</p>
                <label class="profile-label">
                  <span class="profile-label-text">
                    <span class="profile-label-icon" aria-hidden="true"><svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg></span>Tên hiển thị</span>
                  <input
                    v-model="form.displayName"
                    type="text"
                    class="profile-input"
                    placeholder="Tùy chọn"
                    maxlength="100"
                  />
                </label>
                <label class="profile-label">
                  <span class="profile-label-text">
                    <span class="profile-label-icon" aria-hidden="true"><svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg></span>Họ và tên</span>
                  <input
                    v-model="form.fullName"
                    type="text"
                    class="profile-input"
                    placeholder="Nguyễn Văn A"
                    maxlength="150"
                  />
                </label>
                <label class="profile-label">
                  <span class="profile-label-text">
                    <span class="profile-label-icon" aria-hidden="true"><svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/><polyline points="22,6 12,13 2,6"/></svg></span>Email</span>
                  <input
                    v-model="form.email"
                    type="email"
                    class="profile-input"
                    placeholder="email@example.com"
                    maxlength="150"
                  />
                </label>
                <label class="profile-label">
                  <span class="profile-label-text">
                    <span class="profile-label-icon" aria-hidden="true"><svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/></svg></span>Số điện thoại</span>
                  <input
                    v-model="form.phone"
                    type="text"
                    class="profile-input"
                    placeholder="0912345678"
                    maxlength="20"
                  />
                </label>
              </div>

              <div class="profile-section">
                <h2 class="profile-section-title">Đổi mật khẩu</h2>
                <label class="profile-label">
                  <span class="profile-label-text">
                    <span class="profile-label-icon" aria-hidden="true"><svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg></span>Mật khẩu hiện tại</span>
                  <div class="profile-input-wrap">
                    <input
                      v-model="form.currentPassword"
                      :type="showCurrentPassword ? 'text' : 'password'"
                      class="profile-input"
                      placeholder="Để trống nếu không đổi"
                    />
                    <button type="button" class="profile-password-toggle" @click="showCurrentPassword = !showCurrentPassword" aria-label="Toggle">
                      <svg v-if="!showCurrentPassword" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>
                      <svg v-else width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path><line x1="1" y1="1" x2="23" y2="23"></line></svg>
                    </button>
                  </div>
                </label>
                <label class="profile-label">
                  <span class="profile-label-text">
                    <span class="profile-label-icon" aria-hidden="true"><svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 2l-2 2m-7.61 7.61a5.5 5.5 0 1 1-7.778 7.778 5.5 5.5 0 0 1 7.777-7.777zm0 0L15.5 7.5m0 0l3 3L22 7l-3-3m-3.5 3.5L19 4"/></svg></span>Mật khẩu mới</span>
                  <div class="profile-input-wrap">
                    <input
                      v-model="form.newPassword"
                      :type="showNewPassword ? 'text' : 'password'"
                      class="profile-input"
                      placeholder="Tối thiểu 6 ký tự"
                    />
                    <button type="button" class="profile-password-toggle" @click="showNewPassword = !showNewPassword" aria-label="Toggle">
                      <svg v-if="!showNewPassword" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>
                      <svg v-else width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path><line x1="1" y1="1" x2="23" y2="23"></line></svg>
                    </button>
                  </div>
                </label>
              </div>

              <div class="profile-section">
                <h2 class="profile-section-title">Cài đặt website</h2>
                <label class="profile-label">
                  <span class="profile-label-text">
                    <span class="profile-label-icon" aria-hidden="true"><svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="5"/><line x1="12" y1="1" x2="12" y2="3"/><line x1="12" y1="21" x2="12" y2="23"/><line x1="4.22" y1="4.22" x2="5.64" y2="5.64"/><line x1="18.36" y1="18.36" x2="19.78" y2="19.78"/><line x1="1" y1="12" x2="3" y2="12"/><line x1="21" y1="12" x2="23" y2="12"/><line x1="4.22" y1="19.78" x2="5.64" y2="18.36"/><line x1="18.36" y1="5.64" x2="19.78" y2="4.22"/></svg></span>Giao diện (theme)</span>
                  <select v-model="form.settings.theme" class="profile-input profile-select">
                    <option value="dark">Tối (dark)</option>
                    <option value="light">Sáng (light)</option>
                    <option value="system">Theo hệ thống</option>
                  </select>
                </label>
              </div>
              </div>

              <p v-if="saveError" class="profile-error">{{ saveError }}</p>
              <p v-if="saveSuccess" class="profile-success">{{ saveSuccess }}</p>

              <div class="profile-actions">
                <button type="submit" class="profile-submit" :disabled="saving">
                  {{ saving ? 'Đang lưu...' : 'Lưu thay đổi' }}
                </button>
                <button type="button" class="profile-back" @click="$router.push({ name: 'home' })">
                  ← Quay lại
                </button>
              </div>
            </form>
          </template>

          <div v-else class="profile-loading">Đang tải...</div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { useAuthStore } from '../stores/auth';
import AvatarCropModal from '../components/AvatarCropModal.vue';
import { API_BASE } from '../api/config';

const authStore = useAuthStore();
const profile = ref(null);
const loadError = ref('');
const saveError = ref('');
const saveSuccess = ref('');
const saving = ref(false);
const showCurrentPassword = ref(false);
const showNewPassword = ref(false);

const avatarInputRef = ref(null);
const uploadingAvatar = ref(false);
const avatarError = ref('');
const showCropModal = ref(false);
const cropImageUrl = ref('');
const cropImageFile = ref(null);

const form = reactive({
  displayName: '',
  fullName: '',
  email: '',
  phone: '',
  currentPassword: '',
  newPassword: '',
  settings: { theme: 'dark' }
});

const avatarPreviewUrl = computed(() => {
  if (!profile.value?.avatarPath) return null;
  return `${API_BASE}${profile.value.avatarPath}`;
});
const avatarDisplayUrl = computed(() => avatarPreviewUrl.value);
const profileInitials = computed(() => {
  const name = profile.value?.username || '';
  if (name.length >= 2) return name.slice(0, 2).toUpperCase();
  return name ? name[0].toUpperCase() : '?';
});

async function loadProfile() {
  if (!authStore.token) {
    loadError.value = 'Bạn cần đăng nhập.';
    return;
  }
  try {
    const res = await fetch(`${API_BASE}/api/users/me`, {
      headers: authStore.getAuthHeader()
    });
    if (!res.ok) {
      loadError.value = 'Không tải được hồ sơ.';
      return;
    }
    const data = await res.json();
    profile.value = data;
    form.displayName = data.displayName || '';
    form.fullName = data.fullName || '';
    form.email = data.email || '';
    form.phone = data.phone || '';
    if (data.settings && typeof data.settings === 'object') {
      form.settings.theme = data.settings.theme || 'dark';
    }
  } catch {
    loadError.value = 'Lỗi kết nối.';
  }
}

async function handleSave() {
  saveError.value = '';
  saveSuccess.value = '';
  if (form.newPassword && form.newPassword.length < 6) {
    saveError.value = 'Mật khẩu mới tối thiểu 6 ký tự.';
    return;
  }
  if (form.newPassword && !form.currentPassword) {
    saveError.value = 'Nhập mật khẩu hiện tại để đổi mật khẩu.';
    return;
  }
  saving.value = true;
  try {
    const body = {
      displayName: form.displayName || null,
      fullName: form.fullName || null,
      email: form.email || null,
      phone: form.phone || null,
      settings: form.settings
    };
    if (form.newPassword) {
      body.currentPassword = form.currentPassword;
      body.newPassword = form.newPassword;
    }
    const res = await fetch(`${API_BASE}/api/users/me`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        ...authStore.getAuthHeader()
      },
      body: JSON.stringify(body)
    });
    const data = await res.json().catch(() => ({}));
    if (!res.ok) {
      saveError.value = data.message || 'Không lưu được.';
      return;
    }
    profile.value = data;
    authStore.setProfile(data);
    saveSuccess.value = 'Đã lưu.';
    form.currentPassword = '';
    form.newPassword = '';
  } catch {
    saveError.value = 'Lỗi kết nối.';
  } finally {
    saving.value = false;
  }
}

function handleAvatarSelect(e) {
  const file = e.target?.files?.[0];
  e.target.value = '';
  avatarError.value = '';
  if (cropImageUrl.value) URL.revokeObjectURL(cropImageUrl.value);
  cropImageUrl.value = '';
  cropImageFile.value = null;
  if (!file) return;
  if (!file.type.startsWith('image/')) {
    avatarError.value = 'Chỉ chấp nhận file ảnh (JPEG, PNG, GIF, WebP).';
    return;
  }
  cropImageUrl.value = URL.createObjectURL(file);
  cropImageFile.value = file;
  showCropModal.value = true;
}

function closeCropModal() {
  showCropModal.value = false;
  if (cropImageUrl.value) {
    URL.revokeObjectURL(cropImageUrl.value);
    cropImageUrl.value = '';
  }
  cropImageFile.value = null;
  avatarError.value = '';
}

async function uploadCroppedAvatar(blob) {
  if (!blob) return;
  avatarError.value = '';
  uploadingAvatar.value = true;
  try {
    const formData = new FormData();
    formData.append('file', blob, 'avatar.jpg');
    const res = await fetch(`${API_BASE}/api/users/me/avatar`, {
      method: 'POST',
      headers: authStore.getAuthHeader(),
      body: formData
    });
    const data = await res.json().catch(() => ({}));
    if (!res.ok) {
      avatarError.value = data.message || 'Tải ảnh lên thất bại.';
      return;
    }
    profile.value = data;
    authStore.setProfile(data);
    closeCropModal();
  } catch {
    avatarError.value = 'Lỗi kết nối.';
  } finally {
    uploadingAvatar.value = false;
  }
}

onMounted(() => {
  loadProfile();
});
</script>

<style scoped>
.profile-page { min-height: 100vh; padding-top: 4rem; overflow-x: hidden; }
.profile-hero {
  position: relative;
  padding: 1rem 1rem 1.5rem;
  min-height: calc(100vh - 4rem);
  display: flex;
  align-items: center;
  justify-content: center;
}
.profile-hero-bg {
  position: absolute;
  inset: 0;
  background: radial-gradient(ellipse 80% 50% at 50% -20%, rgba(16, 185, 129, 0.12), transparent);
  pointer-events: none;
}
.profile-content { position: relative; max-width: 54rem; margin: 0 auto; width: 100%; }
.profile-card {
  position: relative;
  background-color: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 1.25rem;
  padding: 1.25rem 1.5rem;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  max-height: calc(100vh - 5rem);
  display: flex;
  flex-direction: column;
}
.profile-card-glow {
  position: absolute;
  top: -1px; left: 50%;
  transform: translateX(-50%);
  width: 60%;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(16, 185, 129, 0.5), transparent);
  border-radius: 1px;
  opacity: 0.8;
}
.profile-card-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 0.75rem;
  margin-bottom: 0.25rem;
}
.profile-card-header-text { flex: 1; min-width: 0; }
.profile-card-close {
  flex-shrink: 0;
  width: 2.25rem;
  height: 2.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 0.5rem;
  color: #9ca3af;
  cursor: pointer;
  transition: color 0.2s, background 0.2s;
}
.profile-card-close:hover {
  color: #fff;
  background: rgba(255, 255, 255, 0.1);
}
.animate-in { animation: profileFadeIn 0.5s ease-out; }
@keyframes profileFadeIn {
  from { opacity: 0; transform: translateY(16px); }
  to { opacity: 1; transform: translateY(0); }
}
.profile-title { font-size: 1.5rem; font-weight: 700; margin: 0 0 0.15rem; color: #fff; }
.profile-subtitle { font-size: 0.8125rem; color: #9ca3af; margin: 0; }
.profile-form {
  flex: 1;
  min-height: 0;
  display: flex;
  flex-direction: column;
}
.profile-form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 0.75rem 1.5rem;
  flex: 1;
  min-height: 0;
  margin-bottom: 0.5rem;
}
@media (max-width: 768px) {
  .profile-form-grid { grid-template-columns: 1fr; }
}
.profile-section { margin-bottom: 0.75rem; }
.profile-section-avatar { grid-column: 1 / -1; margin-bottom: 0.5rem; }
.profile-section-title { font-size: 0.9375rem; font-weight: 600; color: #d1d5db; margin: 0 0 0.5rem; }
.profile-label { display: flex; flex-direction: column; gap: 0.25rem; margin-bottom: 0.5rem; }
.profile-label-text {
  display: inline-flex;
  align-items: center;
  gap: 0.4rem;
  font-size: 0.8125rem;
  color: #9ca3af;
  transition: color 0.2s;
}
.profile-label:focus-within .profile-label-text,
.profile-label:hover .profile-label-text { color: #d1d5db; }
.profile-label-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: #6b7280;
  transition: transform 0.25s ease, color 0.2s;
}
.profile-label-icon svg { display: block; }
.profile-label:hover .profile-label-icon,
.profile-label:focus-within .profile-label-icon {
  color: #10b981;
  transform: scale(1.15);
}
.profile-input {
  width: 100%;
  padding: 0.5rem 0.75rem;
  font-size: 0.9375rem;
  color: #fff;
  background-color: #0a0b14;
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 0.5rem;
  outline: none;
}
.profile-input-wrap { position: relative; }
.profile-input-wrap .profile-input { padding-right: 2.5rem; }
.profile-password-toggle {
  position: absolute;
  top: 50%; right: 0.5rem;
  transform: translateY(-50%);
  width: 2rem; height: 2rem;
  display: flex; align-items: center; justify-content: center;
  padding: 0; background: none; border: none;
  color: #9ca3af; cursor: pointer;
  border-radius: 0.5rem;
}
.profile-password-toggle:hover { color: #10b981; }
.profile-select { cursor: pointer; }
.profile-hint { font-size: 0.75rem; color: #6b7280; margin: 0 0 0.35rem; }
.profile-section-avatar { text-align: center; }
.profile-avatar-hint { margin-top: 0.25rem; }

/* Facebook-style avatar upload */
.profile-avatar-fb {
  position: relative;
  width: 88px;
  height: 88px;
  margin: 0 auto;
  border-radius: 50%;
  cursor: pointer;
  overflow: hidden;
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  box-shadow: 0 0 0 3px rgba(255,255,255,0.08), 0 4px 20px rgba(0,0,0,0.35);
  transition: box-shadow 0.2s;
}
.profile-avatar-fb:hover { box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.4), 0 4px 24px rgba(0,0,0,0.4); }
.profile-avatar-input { position: absolute; width: 0; height: 0; opacity: 0; pointer-events: none; }

.profile-avatar-fb-photo {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}
.profile-avatar-fb-photo img { width: 100%; height: 100%; object-fit: cover; }
.profile-avatar-initials { color: #fff; font-size: 1.75rem; font-weight: 700; }

.profile-avatar-fb-overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0.35rem;
  opacity: 0;
  transition: opacity 0.2s;
}
.profile-avatar-fb:hover .profile-avatar-fb-overlay { opacity: 1; }
.profile-avatar-fb-icon { color: #fff; line-height: 0; }
.profile-avatar-fb-text { font-size: 0.75rem; color: #fff; font-weight: 500; }

.profile-avatar-fb-loading {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  font-size: 0.8125rem;
  color: #fff;
}
.profile-avatar-fb-spinner {
  width: 28px;
  height: 28px;
  border: 3px solid rgba(255,255,255,0.3);
  border-top-color: #fff;
  border-radius: 50%;
  animation: profileAvatarSpin 0.8s linear infinite;
}
@keyframes profileAvatarSpin { to { transform: rotate(360deg); } }

.profile-error { color: #fca5a5; font-size: 0.8125rem; margin: 0.25rem 0 0; }
.profile-success { color: #6ee7b7; font-size: 0.8125rem; margin: 0 0 0.5rem; }
.profile-actions { display: flex; flex-wrap: wrap; gap: 0.5rem; margin-top: 0.5rem; flex-shrink: 0; }
.profile-submit {
  padding: 0.5rem 1.25rem;
  font-size: 0.9375rem;
  font-weight: 600;
  color: #fff;
  background-color: #10b981;
  border: none;
  border-radius: 9999px;
  cursor: pointer;
}
.profile-submit:hover:not(:disabled) { background-color: #059669; }
.profile-submit:disabled { opacity: 0.7; cursor: not-allowed; }
.profile-back {
  padding: 0.5rem 0.75rem;
  font-size: 0.875rem;
  color: #9ca3af;
  background: none;
  border: none;
  cursor: pointer;
}
.profile-back:hover { color: #10b981; }
.profile-loading { color: #9ca3af; }
</style>
