<template>
  <main class="admin-page">
    <aside class="admin-sidebar">
      <div class="admin-sidebar-brand">
        <span class="admin-sidebar-logo">
          <LogoIcon :size="24" />
        </span>
        <span class="admin-sidebar-title">TNC Admin</span>
      </div>
      <nav class="admin-sidebar-nav">
          <button
            type="button"
            class="admin-nav-item"
            :class="{ active: tab === 'overview' }"
            @click="tab = 'overview'"
          >
            <span class="admin-nav-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/>
                <rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/>
              </svg>
            </span>
            <span class="admin-nav-text">Tổng quan</span>
          </button>
          <button
            type="button"
            class="admin-nav-item"
            :class="{ active: tab === 'home' }"
            @click="tab = 'home'"
          >
            <span class="admin-nav-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/>
              </svg>
            </span>
            <span class="admin-nav-text">Trang chủ</span>
          </button>
          <button
            type="button"
            class="admin-nav-item"
            :class="{ active: tab === 'topics' }"
            @click="tab = 'topics'; loadTopics()"
          >
            <span class="admin-nav-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="8" y1="6" x2="21" y2="6"/><line x1="8" y1="12" x2="21" y2="12"/><line x1="8" y1="18" x2="21" y2="18"/><line x1="3" y1="6" x2="3.01" y2="6"/><line x1="3" y1="12" x2="3.01" y2="12"/><line x1="3" y1="18" x2="3.01" y2="18"/>
              </svg>
            </span>
            <span class="admin-nav-text">Chủ đề</span>
          </button>
          <button
            type="button"
            class="admin-nav-item"
            :class="{ active: tab === 'posts' }"
            @click="tab = 'posts'; loadPosts()"
          >
            <span class="admin-nav-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/>
                <line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><polyline points="10 9 9 9 8 9"/>
              </svg>
            </span>
            <span class="admin-nav-text">Bài viết</span>
          </button>
          <button
            type="button"
            class="admin-nav-item"
            :class="{ active: tab === 'comments' }"
            @click="tab = 'comments'"
          >
            <span class="admin-nav-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
              </svg>
            </span>
            <span class="admin-nav-text">Bình luận</span>
          </button>
          <button
            type="button"
            class="admin-nav-item"
            :class="{ active: tab === 'pages' }"
            @click="tab = 'pages'"
          >
            <span class="admin-nav-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/>
                <line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/>
              </svg>
            </span>
            <span class="admin-nav-text">Trang</span>
          </button>
          <button
            type="button"
            class="admin-nav-item"
            :class="{ active: tab === 'media' }"
            @click="tab = 'media'"
          >
            <span class="admin-nav-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="3" y="3" width="18" height="18" rx="2" ry="2"/>
                <circle cx="8.5" cy="8.5" r="1.5"/><polyline points="21 15 16 10 5 21"/>
              </svg>
            </span>
            <span class="admin-nav-text">Thư viện</span>
          </button>
          <button
            type="button"
            class="admin-nav-item"
            :class="{ active: tab === 'about' }"
            @click="tab = 'about'"
          >
            <span class="admin-nav-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
                <rect x="14" y="11" width="7" height="9" rx="1"/>
              </svg>
            </span>
            <span class="admin-nav-text">About Me</span>
          </button>
          <router-link to="/profile" class="admin-nav-item admin-nav-item-link">
            <span class="admin-nav-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"/>
              </svg>
            </span>
            <span class="admin-nav-text">Cài đặt</span>
          </router-link>
        </nav>
      <div class="admin-sidebar-user">
        <div class="admin-sidebar-user-avatar" :class="{ 'has-img': avatarUrl }">
          <img v-if="avatarUrl" :src="avatarUrl" alt="" />
          <span v-else>{{ userInitials }}</span>
        </div>
        <div class="admin-sidebar-user-info">
          <span class="admin-sidebar-user-name">{{ authStore.displayNameOrUsername || authStore.username }}</span>
          <span class="admin-sidebar-user-email">{{ userEmail }}</span>
        </div>
      </div>
    </aside>

    <div class="admin-main">
      <header class="admin-header" v-if="tab !== 'overview'">
        <h1 class="admin-header-title">{{ headerTitle }}</h1>
        <p class="admin-header-subtitle">{{ headerSubtitle }}</p>
      </header>

      <div class="admin-content">
        <!-- Dashboard Overview (giống mẫu Cryptix Admin) -->
        <template v-if="tab === 'home'">
        <div class="admin-home-sections">
          <p class="admin-home-sections-desc">Bật hoặc tắt các khối hiển thị trên trang chủ blog.</p>
          <div class="admin-home-section-card">
            <div class="admin-home-section-info">
              <h3 class="admin-home-section-title">Bài viết nổi bật</h3>
              <p class="admin-home-section-subtitle">Khối danh sách bài viết gần đây nhất trên trang chủ.</p>
            </div>
            <label class="admin-toggle">
              <input type="checkbox" :checked="homeSections.showFeaturedPosts" @change="homeSections.setFeaturedPosts(($event.target).checked)" />
              <span class="admin-toggle-slider"></span>
            </label>
          </div>
          <div class="admin-home-section-card">
            <div class="admin-home-section-info">
              <h3 class="admin-home-section-title">Blog này có gì?</h3>
              <p class="admin-home-section-subtitle">Khối giới thiệu 4 chủ đề (Câu chuyện cá nhân, Ghi chú kỹ thuật, Web3, Kiến thức).</p>
            </div>
            <label class="admin-toggle">
              <input type="checkbox" :checked="homeSections.showBlogIntro" @change="homeSections.setBlogIntro(($event.target).checked)" />
              <span class="admin-toggle-slider"></span>
            </label>
          </div>
          <div class="admin-home-section-card">
            <div class="admin-home-section-info">
              <h3 class="admin-home-section-title">Câu hỏi thường gặp</h3>
              <p class="admin-home-section-subtitle">Khối FAQ mở/đóng câu hỏi.</p>
            </div>
            <label class="admin-toggle">
              <input type="checkbox" :checked="homeSections.showFaq" @change="homeSections.setFaq(($event.target).checked)" />
              <span class="admin-toggle-slider"></span>
            </label>
          </div>
        </div>
      </template>

      <template v-if="tab === 'topics'">
        <div class="admin-topics-wrap">
          <p class="admin-topics-desc">Quản lý các mục hiển thị trên navbar (Bài viết, FAQ...). Tối đa 2 cấp: cấp 1 là mục gốc, cấp 2 chọn cha.</p>
          <div v-if="topicsError" class="admin-error">{{ topicsError }}</div>
          <div class="admin-toolbar">
            <button type="button" class="admin-btn admin-btn-primary" @click="openTopicForm()">Thêm chủ đề</button>
            <button type="button" class="admin-btn admin-btn-ghost" @click="loadTopics" :disabled="topicsStore.loading">{{ topicsStore.loading ? 'Đang tải...' : 'Làm mới' }}</button>
          </div>
          <div class="admin-table-wrap">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>Cấp</th>
                  <th>Nhãn</th>
                  <th>Đích (#id hoặc /path)</th>
                  <th>Thứ tự</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in topicsFlat" :key="item.id" :class="{ 'admin-topic-row-child': item.level === 2 }">
                  <td>{{ item.level }}</td>
                  <td><span :class="{ 'admin-topic-indent': item.level === 2 }">{{ item.label }}</span></td>
                  <td><code class="admin-topic-target">{{ item.target }}</code></td>
                  <td>{{ item.sortOrder }}</td>
                  <td>
                    <button type="button" class="admin-btn admin-btn-ghost admin-btn-sm" @click="editTopic(item)">Sửa</button>
                    <button type="button" class="admin-btn admin-btn-ghost admin-btn-sm admin-btn-danger" @click="confirmDeleteTopic(item)">Xóa</button>
                  </td>
                </tr>
                <tr v-if="topicsFlat.length === 0 && !topicsStore.loading">
                  <td colspan="5" class="admin-table-empty">Chưa có chủ đề. Bấm «Thêm chủ đề» để tạo.</td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- Form thêm/sửa chủ đề -->
          <div v-if="topicFormOpen" class="admin-topic-form-card">
            <h3 class="admin-topic-form-title">{{ editingTopicId ? 'Sửa chủ đề' : 'Thêm chủ đề' }}</h3>
            <div class="admin-topic-form-row">
              <label>Nhãn</label>
              <input v-model="topicForm.label" type="text" placeholder="VD: Bài viết" class="admin-input" />
            </div>
            <div class="admin-topic-form-row">
              <label>Đích đến</label>
              <input v-model="topicForm.target" type="text" placeholder="#posts hoặc /about" class="admin-input" />
            </div>
            <div class="admin-topic-form-row">
              <label>Thứ tự</label>
              <input v-model.number="topicForm.sortOrder" type="number" min="0" class="admin-input" style="max-width: 100px;" />
            </div>
            <div class="admin-topic-form-row">
              <label>Chủ đề cha (cấp 2)</label>
              <select v-model="topicForm.parentId" class="admin-input">
                <option :value="null">— Cấp 1 (không cha) —</option>
                <option v-for="r in topicsStore.tree" :key="r.id" :value="r.id">{{ r.label }}</option>
              </select>
            </div>
            <div class="admin-topic-form-actions">
              <button type="button" class="admin-btn admin-btn-primary" @click="saveTopic" :disabled="topicSaving">{{ topicSaving ? 'Đang lưu...' : 'Lưu' }}</button>
              <button type="button" class="admin-btn admin-btn-ghost" @click="closeTopicForm">Hủy</button>
            </div>
          </div>
          <!-- Modal xác nhận xóa -->
          <div v-if="topicToDelete" class="admin-modal-overlay" @click.self="topicToDelete = null">
            <div class="admin-modal">
              <p>Xóa chủ đề «{{ topicToDelete.label }}»? Các chủ đề con (nếu có) cũng sẽ bị xóa.</p>
              <div class="admin-modal-actions">
                <button type="button" class="admin-btn admin-btn-danger" @click="doDeleteTopic">Xóa</button>
                <button type="button" class="admin-btn admin-btn-ghost" @click="topicToDelete = null">Hủy</button>
              </div>
            </div>
          </div>
        </div>
      </template>

      <template v-if="tab === 'overview'">
          <div class="admin-dashboard-welcome">
            <h1 class="admin-dashboard-title">Tổng quan</h1>
            <p class="admin-dashboard-subtitle">Chào bạn! Dưới đây là tình hình hôm nay.</p>
          </div>

          <div class="admin-stats admin-stats-four">
            <div class="admin-stat-card">
              <div class="admin-stat-icon admin-stat-icon-posts">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/>
                  <line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/>
                </svg>
              </div>
              <div class="admin-stat-body">
                <span class="admin-stat-value">{{ stats.totalPosts }}</span>
                <span class="admin-stat-label">Tổng bài viết</span>
                <span class="admin-stat-growth">—</span>
              </div>
            </div>
            <div class="admin-stat-card">
              <div class="admin-stat-icon admin-stat-icon-user">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
                </svg>
              </div>
              <div class="admin-stat-body">
                <span class="admin-stat-value">{{ stats.myPosts }}</span>
                <span class="admin-stat-label">Bài viết của tôi</span>
                <span class="admin-stat-growth">—</span>
              </div>
            </div>
            <div class="admin-stat-card">
              <div class="admin-stat-icon admin-stat-icon-eye">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/>
                </svg>
              </div>
              <div class="admin-stat-body">
                <span class="admin-stat-value">—</span>
                <span class="admin-stat-label">Lượt xem</span>
                <span class="admin-stat-growth">Sắp có</span>
              </div>
            </div>
            <div class="admin-stat-card">
              <div class="admin-stat-icon admin-stat-icon-comment">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
                </svg>
              </div>
              <div class="admin-stat-body">
                <span class="admin-stat-value">—</span>
                <span class="admin-stat-label">Bình luận</span>
                <span class="admin-stat-growth">Sắp có</span>
              </div>
            </div>
          </div>
          <div class="admin-dashboard-grid">
            <div class="admin-dashboard-card admin-dashboard-chart">
              <h3 class="admin-dashboard-card-title">Hoạt động bài viết</h3>
              <p class="admin-dashboard-card-subtitle">Xu hướng và thống kê</p>
              <div class="admin-dashboard-chart-placeholder">
                <p class="admin-dashboard-chart-hint">Biểu đồ sẽ hiển thị khi có dữ liệu</p>
                <button type="button" class="admin-btn admin-btn-primary admin-btn-sm" @click="tab = 'posts'; loadPosts(); openPostForm()">Viết bài mới</button>
              </div>
            </div>
            <div class="admin-dashboard-card admin-dashboard-list">
              <h3 class="admin-dashboard-card-title">Bài viết mới nhất</h3>
              <p class="admin-dashboard-card-subtitle">Các bài đã đăng gần đây</p>
              <ul class="admin-dashboard-list-ul">
                <li v-for="post in recentPosts" :key="post.id" class="admin-dashboard-list-item">
                  <span class="admin-dashboard-list-title">{{ post.title }}</span>
                  <span class="admin-dashboard-list-meta">{{ post.authorUsername }} · {{ formatDate(post.updatedAt) }}</span>
                </li>
                <li v-if="!recentPosts.length" class="admin-dashboard-list-empty">Chưa có bài viết nào.</li>
              </ul>
              <router-link to="/" class="admin-dashboard-card-link">Xem blog →</router-link>
            </div>
          </div>
        </template>

        <!-- Comments (placeholder) - nội dung giới hạn bên trái -->
        <template v-if="tab === 'comments'">
          <div class="admin-content-left">
            <div class="admin-placeholder-block">
              <p class="admin-placeholder-icon">💬</p>
              <h2 class="admin-placeholder-title">Bình luận</h2>
              <p class="admin-placeholder-desc">Quản lý bình luận của độc giả. Tính năng đang được phát triển.</p>
            </div>
          </div>
        </template>

        <!-- Pages (placeholder) - nội dung giới hạn bên trái -->
        <template v-if="tab === 'pages'">
          <div class="admin-content-left">
            <div class="admin-placeholder-block">
              <p class="admin-placeholder-icon">📄</p>
              <h2 class="admin-placeholder-title">Trang tĩnh</h2>
              <p class="admin-placeholder-desc">Tạo trang Giới thiệu, Liên hệ, v.v. Đang được phát triển.</p>
            </div>
          </div>
        </template>

        <!-- Media (placeholder) - nội dung giới hạn bên trái -->
        <template v-if="tab === 'media'">
          <div class="admin-content-left">
            <div class="admin-placeholder-block">
              <p class="admin-placeholder-icon">🖼️</p>
              <h2 class="admin-placeholder-title">Thư viện media</h2>
              <p class="admin-placeholder-desc">Upload và quản lý ảnh, file. Đang được phát triển.</p>
            </div>
          </div>
        </template>

        <!-- About Me - rich text editor (kiểu CKEditor), hiển thị tại trang /about -->
        <template v-if="tab === 'about'">
          <div class="admin-about-editor-wrap">
            <div class="admin-about-editor-card">
              <p class="admin-about-editor-hint">Soạn nội dung giới thiệu / CV bên dưới (in đậm, in nghiêng, tiêu đề, danh sách...). Bấm <strong>Lưu</strong> để cập nhật. Nội dung hiển thị tại trang <router-link to="/about" target="_blank">/about</router-link>.</p>
              <RichTextEditor v-model="aboutStore.content" />
              <div v-if="aboutStore.saveError" class="admin-error">{{ aboutStore.saveError }}</div>
              <div class="admin-about-form-actions">
                <button type="button" class="admin-btn admin-btn-primary" @click="saveAbout" :disabled="aboutStore.loading">
                  {{ aboutStore.loading ? 'Đang lưu...' : 'Lưu About Me' }}
                </button>
                <router-link to="/about" class="admin-btn admin-btn-ghost" target="_blank">Xem trang About</router-link>
              </div>
            </div>
          </div>
        </template>

        <!-- Posts -->
        <template v-if="tab === 'posts'">
          <div class="admin-toolbar">
            <button type="button" class="admin-btn admin-btn-primary" @click="openPostForm()">
              Thêm bài viết
            </button>
            <button type="button" class="admin-btn admin-btn-ghost" @click="loadPosts" :disabled="loadingPosts">
              {{ loadingPosts ? 'Đang tải...' : 'Làm mới' }}
            </button>
          </div>
          <div v-if="postsError" class="admin-error">{{ postsError }}</div>
          <div v-else class="admin-table-wrap">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>Tiêu đề</th>
                  <th>Tác giả</th>
                  <th>Cập nhật</th>
                  <th class="admin-table-actions">Thao tác</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="loadingPosts">
                  <td colspan="4" class="admin-table-loading">Đang tải...</td>
                </tr>
                <tr v-else-if="!posts.length">
                  <td colspan="4" class="admin-table-empty">Chưa có bài viết nào.</td>
                </tr>
                <tr v-for="post in posts" :key="post.id">
                  <td class="admin-table-title">{{ post.title }}</td>
                  <td>{{ post.authorUsername || '—' }}</td>
                  <td>{{ formatDate(post.updatedAt) }}</td>
                  <td class="admin-table-actions">
                    <button type="button" class="admin-table-btn" @click="openPostForm(post)" title="Sửa">Sửa</button>
                    <button
                      type="button"
                      class="admin-table-btn admin-table-btn-danger"
                      @click="confirmDelete(post)"
                      :disabled="!isMyPost(post)"
                      :title="isMyPost(post) ? 'Xóa' : 'Chỉ tác giả mới xóa được'"
                    >
                      Xóa
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </template>
      </div>
    </div>

    <!-- Post form modal -->
    <Teleport to="body">
      <Transition name="admin-modal">
        <div v-if="showPostModal" class="admin-modal-overlay" @click.self="closePostForm">
          <div class="admin-modal">
            <div class="admin-modal-header">
              <h2 class="admin-modal-title">{{ editingPost ? 'Chỉnh sửa bài viết' : 'Thêm bài viết' }}</h2>
              <button type="button" class="admin-modal-close" aria-label="Đóng" @click="closePostForm">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
              </button>
            </div>
            <form class="admin-modal-body" @submit.prevent="savePost">
              <label class="admin-form-label">
                <span>Tiêu đề</span>
                <input v-model="postForm.title" type="text" class="admin-input" placeholder="Nhập tiêu đề" maxlength="200" required />
              </label>
              <label class="admin-form-label">
                <span>Nội dung</span>
                <textarea v-model="postForm.content" class="admin-input admin-textarea" placeholder="Nội dung bài viết..." rows="10" required></textarea>
              </label>
              <p v-if="postFormError" class="admin-error">{{ postFormError }}</p>
              <div class="admin-modal-footer">
                <button type="button" class="admin-btn admin-btn-ghost" @click="closePostForm">Hủy</button>
                <button type="submit" class="admin-btn admin-btn-primary" :disabled="savingPost">{{ savingPost ? 'Đang lưu...' : 'Lưu' }}</button>
              </div>
            </form>
          </div>
        </div>
      </Transition>
    </Teleport>

    <!-- Delete confirm -->
    <Teleport to="body">
      <Transition name="admin-modal">
        <div v-if="postToDelete" class="admin-modal-overlay" @click.self="postToDelete = null">
          <div class="admin-modal admin-modal-sm">
            <div class="admin-modal-header">
              <h2 class="admin-modal-title">Xóa bài viết?</h2>
              <button type="button" class="admin-modal-close" aria-label="Đóng" @click="postToDelete = null">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
              </button>
            </div>
            <div class="admin-modal-body">
              <p class="admin-delete-text">Bạn có chắc muốn xóa «{{ postToDelete.title }}»? Hành động này không thể hoàn tác.</p>
              <p v-if="deleteError" class="admin-error">{{ deleteError }}</p>
              <div class="admin-modal-footer">
                <button type="button" class="admin-btn admin-btn-ghost" @click="postToDelete = null">Hủy</button>
                <button type="button" class="admin-btn admin-btn-danger" @click="doDeletePost" :disabled="deletingPost">{{ deletingPost ? 'Đang xóa...' : 'Xóa' }}</button>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </Teleport>
  </main>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { useAuthStore } from '../stores/auth';
import { useAboutStore } from '../stores/about';
import { useHomeSectionsStore } from '../stores/homeSections';
import { useTopicsStore } from '../stores/topics';
import LogoIcon from '../components/LogoIcon.vue';
import RichTextEditor from '../components/RichTextEditor.vue';
import { API_BASE } from '../api/config';

const authStore = useAuthStore();
const aboutStore = useAboutStore();
const homeSections = useHomeSectionsStore();
const topicsStore = useTopicsStore();

const tab = ref('overview');

const topicsError = ref('');
const topicFormOpen = ref(false);
const editingTopicId = ref(null);
const topicForm = reactive({ label: '', target: '', sortOrder: 0, parentId: null });
const topicSaving = ref(false);
const topicToDelete = ref(null);

const topicsFlat = computed(() => topicsStore.getFlatList());

function loadTopics() {
  topicsError.value = '';
  topicsStore.fetchTopics();
}

function openTopicForm() {
  editingTopicId.value = null;
  topicForm.label = '';
  topicForm.target = '#posts';
  topicForm.sortOrder = topicsFlat.value.length;
  topicForm.parentId = null;
  topicFormOpen.value = true;
}

function editTopic(item) {
  editingTopicId.value = item.id;
  topicForm.label = item.label;
  topicForm.target = item.target || '';
  topicForm.sortOrder = item.sortOrder ?? 0;
  topicForm.parentId = item.parentId ?? null;
  topicFormOpen.value = true;
}

function closeTopicForm() {
  topicFormOpen.value = false;
  editingTopicId.value = null;
}

async function saveTopic() {
  if (!topicForm.label?.trim() || !topicForm.target?.trim()) {
    topicsError.value = 'Nhãn và đích đến không được để trống';
    return;
  }
  topicSaving.value = true;
  topicsError.value = '';
  try {
    const body = {
      label: topicForm.label.trim(),
      target: topicForm.target.trim(),
      sortOrder: topicForm.sortOrder ?? 0,
      parentId: topicForm.parentId || null
    };
    if (editingTopicId.value) {
      const res = await fetch(`${API_BASE}/api/topics/${editingTopicId.value}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json', ...getAuthHeader() },
        body: JSON.stringify(body)
      });
      if (!res.ok) {
        const err = await res.json().catch(() => ({}));
        throw new Error(err.message || res.statusText);
      }
    } else {
      const res = await fetch(`${API_BASE}/api/topics`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', ...getAuthHeader() },
        body: JSON.stringify(body)
      });
      if (!res.ok) {
        const err = await res.json().catch(() => ({}));
        throw new Error(err.message || res.statusText);
      }
    }
    await topicsStore.fetchTopics();
    closeTopicForm();
  } catch (e) {
    topicsError.value = e.message || 'Lỗi lưu chủ đề';
  } finally {
    topicSaving.value = false;
  }
}

function confirmDeleteTopic(item) {
  topicToDelete.value = item;
}

async function doDeleteTopic() {
  if (!topicToDelete.value) return;
  topicSaving.value = true;
  topicsError.value = '';
  try {
    const res = await fetch(`${API_BASE}/api/topics/${topicToDelete.value.id}`, {
      method: 'DELETE',
      headers: getAuthHeader()
    });
    if (!res.ok) throw new Error('Không xóa được');
    await topicsStore.fetchTopics();
    topicToDelete.value = null;
  } catch (e) {
    topicsError.value = e.message || 'Lỗi xóa';
  } finally {
    topicSaving.value = false;
  }
}

async function saveAbout() {
  await aboutStore.save();
}

const posts = ref([]);
const loadingPosts = ref(false);
const postsError = ref('');
const showPostModal = ref(false);
const editingPost = ref(null);
const postForm = reactive({ title: '', content: '' });
const postFormError = ref('');
const savingPost = ref(false);
const postToDelete = ref(null);
const deletingPost = ref(false);
const deleteError = ref('');

const stats = computed(() => ({
  totalPosts: posts.value.length,
  myPosts: posts.value.filter(p => p.authorUsername === authStore.username).length
}));

const avatarUrl = computed(() =>
  authStore.profile?.avatarPath ? `${API_BASE}${authStore.profile.avatarPath}` : null
);
const userInitials = computed(() => {
  const name = (authStore.displayNameOrUsername || authStore.username || '').trim();
  if (!name) return '?';
  const words = name.split(/\s+/);
  if (words.length >= 2)
    return (words[0][0] + words[words.length - 1][0]).toUpperCase();
  return name.length >= 2 ? name.slice(0, 2).toUpperCase() : name[0].toUpperCase();
});
const userEmail = computed(() =>
  authStore.profile?.email || (authStore.username ? `${authStore.username}@blog` : '')
);
const recentPosts = computed(() => posts.value.slice(0, 5));

const tabTitles = {
  overview: { title: 'Tổng quan', subtitle: 'Thống kê và hoạt động gần đây' },
  home: { title: 'Trang chủ', subtitle: 'Bật/tắt các khối trên trang chủ' },
  topics: { title: 'Chủ đề', subtitle: 'Quản lý menu chủ đề (tối đa 2 cấp)' },
  posts: { title: 'Bài viết', subtitle: 'Quản lý bài viết trên blog' },
  comments: { title: 'Bình luận', subtitle: 'Quản lý bình luận độc giả' },
  pages: { title: 'Trang', subtitle: 'Trang tĩnh (Giới thiệu, Liên hệ...)' },
  media: { title: 'Thư viện', subtitle: 'Ảnh và file đã tải lên' },
  about: { title: 'About Me', subtitle: 'Quản lý trang giới thiệu bản thân & CV' }
};
const headerTitle = computed(() => tabTitles[tab.value]?.title ?? 'Quản trị');
const headerSubtitle = computed(() => tabTitles[tab.value]?.subtitle ?? '');

function getAuthHeader() {
  return authStore.getAuthHeader();
}

function formatDate(iso) {
  if (!iso) return '—';
  const d = new Date(iso);
  return d.toLocaleDateString('vi-VN', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' });
}

function isMyPost(post) {
  return post.authorUsername === authStore.username;
}

async function loadPosts() {
  loadingPosts.value = true;
  postsError.value = '';
  try {
    const res = await fetch(`${API_BASE}/api/posts`, { headers: getAuthHeader() });
    if (!res.ok) throw new Error('Không tải được danh sách bài viết');
    posts.value = await res.json();
  } catch (e) {
    postsError.value = e.message || 'Lỗi kết nối';
  } finally {
    loadingPosts.value = false;
  }
}

function openPostForm(post = null) {
  editingPost.value = post || null;
  postForm.title = post ? post.title : '';
  postForm.content = post ? post.content : '';
  postFormError.value = '';
  showPostModal.value = true;
}

function closePostForm() {
  showPostModal.value = false;
  editingPost.value = null;
  postForm.title = '';
  postForm.content = '';
  postFormError.value = '';
}

async function savePost() {
  postFormError.value = '';
  savingPost.value = true;
  try {
    const url = editingPost.value
      ? `${API_BASE}/api/posts/${editingPost.value.id}`
      : `${API_BASE}/api/posts`;
    const method = editingPost.value ? 'PUT' : 'POST';
    const res = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json', ...getAuthHeader() },
      body: JSON.stringify({ title: postForm.title, content: postForm.content })
    });
    const data = await res.json().catch(() => ({}));
    if (!res.ok) {
      postFormError.value = data.message || 'Lưu thất bại';
      return;
    }
    closePostForm();
    loadPosts();
  } catch {
    postFormError.value = 'Lỗi kết nối';
  } finally {
    savingPost.value = false;
  }
}

function confirmDelete(post) {
  if (!isMyPost(post)) return;
  deleteError.value = '';
  postToDelete.value = post;
}

async function doDeletePost() {
  if (!postToDelete.value) return;
  deletingPost.value = true;
  deleteError.value = '';
  try {
    const res = await fetch(`${API_BASE}/api/posts/${postToDelete.value.id}`, {
      method: 'DELETE',
      headers: getAuthHeader()
    });
    if (!res.ok) throw new Error('Xóa thất bại');
    postToDelete.value = null;
    loadPosts();
  } catch (e) {
    deleteError.value = e.message || 'Lỗi kết nối';
  } finally {
    deletingPost.value = false;
  }
}

onMounted(() => {
  authStore.fetchProfile();
  loadPosts();
});
</script>

<style scoped>
.admin-page {
  min-height: 100vh;
  padding-top: 4rem;
  background-color: #0a0b14;
}
/* Sidebar cố định bên trái, bề rộng 240px — menu nằm gọn trong cột trái, không đổ từ header, không bằng màn hình */
.admin-sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 240px;
  max-width: 240px;
  height: 100vh;
  background-color: #14151f;
  border-right: 1px solid rgba(255, 255, 255, 0.06);
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  overflow-x: hidden;
  z-index: 30;
}
.admin-sidebar-brand {
  padding: 1.25rem 1rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
  flex-shrink: 0;
}
.admin-sidebar-logo { display: flex; align-items: center; justify-content: center; color: #10b981; flex-shrink: 0; }
.admin-sidebar-title { font-size: 1.125rem; font-weight: 700; color: #fff; }
/* Reset global nav (main.css) để menu chỉ nằm trong sidebar, không full-width */
.admin-sidebar-nav {
  position: static;
  left: auto;
  right: auto;
  width: 100%;
  max-width: 240px;
  flex: 1;
  min-height: 0;
  display: flex;
  flex-direction: column;
  gap: 0;
  padding: 0.5rem 0;
  overflow-y: auto;
  overflow-x: hidden;
}
.admin-nav-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  width: 100%;
  min-height: 48px;
  padding: 0.75rem 1rem;
  font-size: 0.9375rem;
  color: #9ca3af;
  background: none;
  border: none;
  cursor: pointer;
  text-align: left;
  transition: color 0.2s, background 0.2s;
  border-left: 3px solid transparent;
  flex-shrink: 0;
  text-decoration: none;
  box-sizing: border-box;
}
.admin-nav-item:hover { color: #fff; background: rgba(255, 255, 255, 0.04); }
.admin-nav-item.active {
  color: #10b981;
  background: rgba(16, 185, 129, 0.15);
  border-left-color: #10b981;
  border-left-width: 4px;
}
.admin-nav-icon { display: flex; align-items: center; justify-content: center; flex-shrink: 0; }
.admin-nav-text { flex: 1; min-width: 0; }
.admin-nav-item-link { text-decoration: none; }
.admin-sidebar-user {
  padding: 1rem;
  border-top: 1px solid rgba(255, 255, 255, 0.06);
  display: flex;
  align-items: center;
  gap: 0.75rem;
  flex-shrink: 0;
  background: rgba(0, 0, 0, 0.2);
}
.admin-sidebar-user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: #fff;
  font-size: 0.875rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  overflow: hidden;
}
.admin-sidebar-user-avatar.has-img { background: #14151f; }
.admin-sidebar-user-avatar img { width: 100%; height: 100%; object-fit: cover; }
.admin-sidebar-user-info { min-width: 0; display: flex; flex-direction: column; gap: 0.15rem; }
.admin-sidebar-user-name { font-size: 0.9375rem; font-weight: 600; color: #fff; }
.admin-sidebar-user-email { font-size: 0.75rem; color: #6b7280; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }

/* Nội dung Bình luận, Trang, Thư viện chỉ nằm bên trái, không tràn full màn hình */
.admin-content-left {
  max-width: 420px;
  width: 100%;
}
.admin-placeholder-block {
  padding: 2rem 1.5rem;
  text-align: center;
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 0.75rem;
}
.admin-placeholder-icon { font-size: 2.5rem; margin: 0 0 0.75rem; }
.admin-placeholder-title { font-size: 1.125rem; color: #fff; margin: 0 0 0.5rem; }
.admin-placeholder-desc { font-size: 0.875rem; color: #9ca3af; margin: 0; }

.admin-home-sections { max-width: 640px; }
.admin-home-sections-desc {
  font-size: 0.9375rem;
  color: #9ca3af;
  margin: 0 0 1.5rem;
  line-height: 1.5;
}
.admin-home-section-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  padding: 1.25rem 1.5rem;
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 0.75rem;
  margin-bottom: 0.75rem;
}
.admin-home-section-info { flex: 1; min-width: 0; }
.admin-home-section-title { font-size: 1rem; font-weight: 600; color: #fff; margin: 0 0 0.25rem; }
.admin-home-section-subtitle { font-size: 0.8125rem; color: #9ca3af; margin: 0; }
.admin-toggle {
  position: relative;
  display: inline-block;
  width: 48px;
  height: 26px;
  flex-shrink: 0;
}
.admin-toggle input {
  opacity: 0;
  width: 0;
  height: 0;
}
.admin-toggle-slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.12);
  border-radius: 9999px;
  transition: background 0.2s;
}
.admin-toggle-slider::before {
  content: '';
  position: absolute;
  height: 20px;
  width: 20px;
  left: 3px;
  bottom: 3px;
  background: #fff;
  border-radius: 50%;
  transition: transform 0.2s;
}
.admin-toggle input:checked + .admin-toggle-slider {
  background: #10b981;
}
.admin-toggle input:checked + .admin-toggle-slider::before {
  transform: translateX(22px);
}

.admin-topics-wrap { max-width: 900px; }
.admin-topics-desc {
  font-size: 0.9375rem;
  color: #9ca3af;
  margin: 0 0 1rem;
  line-height: 1.5;
}
.admin-topic-indent { padding-left: 1rem; }
.admin-topic-target { font-size: 0.8125rem; background: rgba(0,0,0,0.3); padding: 0.15em 0.4em; border-radius: 4px; }
.admin-topic-row-child { background: rgba(255,255,255,0.02); }
.admin-table-empty { text-align: center; color: #9ca3af; padding: 1.5rem !important; }
.admin-topic-form-card {
  margin-top: 1.5rem;
  padding: 1.5rem;
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 0.75rem;
}
.admin-topic-form-title { font-size: 1.125rem; color: #fff; margin: 0 0 1rem; }
.admin-topic-form-row { margin-bottom: 1rem; }
.admin-topic-form-row label { display: block; font-size: 0.875rem; color: #9ca3af; margin-bottom: 0.35rem; }
.admin-topic-form-actions { display: flex; gap: 0.75rem; margin-top: 1.25rem; }
.admin-modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 200;
}
.admin-modal {
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 0.75rem;
  padding: 1.5rem;
  max-width: 400px;
  width: 90%;
}
.admin-modal p { margin: 0 0 1rem; color: #d1d5db; font-size: 0.9375rem; }
.admin-modal-actions { display: flex; gap: 0.75rem; }
.admin-btn-sm { padding: 0.35rem 0.65rem; font-size: 0.8125rem; }
.admin-btn-danger { background: #dc2626; color: #fff; }
.admin-btn-danger:hover { background: #b91c1c; }

.admin-about-editor-wrap { max-width: 100%; width: 100%; }
.admin-about-editor-card {
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 0.75rem;
  padding: 1.5rem 1.75rem;
}
.admin-about-editor-hint {
  font-size: 0.875rem;
  color: #9ca3af;
  margin: 0 0 1.25rem;
  line-height: 1.5;
}
.admin-about-editor-hint a { color: #10b981; text-decoration: none; }
.admin-about-editor-hint a:hover { text-decoration: underline; }
.admin-about-form-actions { display: flex; flex-wrap: wrap; gap: 0.75rem; margin-top: 1rem; }

.admin-main {
  margin-left: 240px;
  min-width: 0;
  min-height: calc(100vh - 4rem);
  padding: 1.5rem 2rem;
}
.admin-header { margin-bottom: 1.5rem; }
.admin-header-title { font-size: 1.5rem; font-weight: 700; color: #fff; margin: 0 0 0.25rem; }
.admin-header-subtitle { font-size: 0.875rem; color: #9ca3af; margin: 0; }

.admin-stats {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 1rem;
  margin-bottom: 1.5rem;
}
.admin-stat-card {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1.25rem;
  background-color: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 0.75rem;
  transition: border-color 0.2s, box-shadow 0.2s;
}
.admin-stat-card:hover { border-color: rgba(16, 185, 129, 0.3); box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2); }
.admin-stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 0.75rem;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}
.admin-stat-icon-posts { background: rgba(16, 185, 129, 0.15); color: #10b981; }
.admin-stat-icon-user { background: rgba(16, 185, 129, 0.15); color: #10b981; }
.admin-stat-icon-eye { background: rgba(59, 130, 246, 0.15); color: #3b82f6; }
.admin-stat-icon-comment { background: rgba(168, 85, 247, 0.15); color: #a855f7; }
.admin-stat-body { display: flex; flex-direction: column; gap: 0.15rem; }
.admin-stat-value { font-size: 1.5rem; font-weight: 700; color: #fff; }
.admin-stat-label { font-size: 0.8125rem; color: #9ca3af; }
.admin-stat-growth { font-size: 0.75rem; color: #10b981; }
.admin-stats-four { grid-template-columns: repeat(4, 1fr); }
@media (max-width: 900px) {
  .admin-stats-four { grid-template-columns: repeat(2, 1fr); }
}
@media (max-width: 480px) {
  .admin-stats-four { grid-template-columns: 1fr; }
}

.admin-dashboard-welcome { margin-bottom: 1.5rem; }
.admin-dashboard-title { font-size: 1.5rem; font-weight: 700; color: #fff; margin: 0 0 0.25rem; }
.admin-dashboard-subtitle { font-size: 0.9375rem; color: #9ca3af; margin: 0; }

.admin-dashboard-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
  margin-top: 1.5rem;
}
@media (max-width: 900px) {
  .admin-dashboard-grid { grid-template-columns: 1fr; }
}
.admin-dashboard-card {
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 0.75rem;
  padding: 1.25rem;
}
.admin-dashboard-card-title { font-size: 1rem; font-weight: 600; color: #fff; margin: 0 0 0.25rem; }
.admin-dashboard-card-subtitle { font-size: 0.8125rem; color: #9ca3af; margin: 0 0 1rem; }
.admin-dashboard-chart-placeholder {
  min-height: 200px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  background: rgba(0, 0, 0, 0.2);
  border-radius: 0.5rem;
  padding: 1.5rem;
}
.admin-dashboard-chart-hint { font-size: 0.875rem; color: #6b7280; margin: 0; }
.admin-dashboard-list-ul { list-style: none; margin: 0; padding: 0; }
.admin-dashboard-list-item {
  padding: 0.75rem 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
}
.admin-dashboard-list-item:last-of-type { border-bottom: none; }
.admin-dashboard-list-title { font-size: 0.9375rem; font-weight: 500; color: #fff; }
.admin-dashboard-list-meta { font-size: 0.75rem; color: #6b7280; }
.admin-dashboard-list-empty { padding: 1rem 0; color: #6b7280; font-size: 0.875rem; }
.admin-dashboard-card-link {
  display: inline-block;
  margin-top: 0.75rem;
  font-size: 0.875rem;
  color: #10b981;
  text-decoration: none;
  font-weight: 500;
}
.admin-dashboard-card-link:hover { text-decoration: underline; }
.admin-btn-sm { padding: 0.4rem 0.75rem; font-size: 0.875rem; }

.admin-overview-actions { display: flex; gap: 0.75rem; flex-wrap: wrap; }
.admin-toolbar { display: flex; gap: 0.75rem; margin-bottom: 1rem; flex-wrap: wrap; }
.admin-btn {
  padding: 0.5rem 1rem;
  font-size: 0.9375rem;
  font-weight: 600;
  border-radius: 0.5rem;
  cursor: pointer;
  border: none;
  text-decoration: none;
  transition: background 0.2s, color 0.2s;
}
.admin-btn-primary { background: linear-gradient(135deg, #10b981 0%, #059669 100%); color: #fff; }
.admin-btn-primary:hover:not(:disabled) { background: #059669; }
.admin-btn-ghost { background: rgba(255, 255, 255, 0.06); color: #d1d5db; }
.admin-btn-ghost:hover { background: rgba(255, 255, 255, 0.1); color: #fff; }
.admin-btn-danger { background: rgba(239, 68, 68, 0.2); color: #fca5a5; }
.admin-btn-danger:hover:not(:disabled) { background: rgba(239, 68, 68, 0.35); }
.admin-btn:disabled { opacity: 0.6; cursor: not-allowed; }

.admin-error { color: #fca5a5; font-size: 0.875rem; margin: 0.5rem 0 0; }
.admin-table-wrap {
  background-color: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 0.75rem;
  overflow: hidden;
}
.admin-table { width: 100%; border-collapse: collapse; }
.admin-table th,
.admin-table td { padding: 0.75rem 1rem; text-align: left; border-bottom: 1px solid rgba(255, 255, 255, 0.06); }
.admin-table th { font-size: 0.75rem; font-weight: 600; color: #9ca3af; text-transform: uppercase; letter-spacing: 0.05em; }
.admin-table td { font-size: 0.9375rem; color: #d1d5db; }
.admin-table tbody tr:last-child td { border-bottom: none; }
.admin-table tbody tr:hover td { background: rgba(255, 255, 255, 0.02); }
.admin-table-title { font-weight: 500; color: #fff; max-width: 280px; }
.admin-table-actions { width: 140px; }
.admin-table-loading,
.admin-table-empty { color: #9ca3af; text-align: center; padding: 2rem !important; }
.admin-table-btn {
  padding: 0.25rem 0.5rem;
  font-size: 0.8125rem;
  color: #10b981;
  background: none;
  border: none;
  cursor: pointer;
  margin-right: 0.5rem;
  border-radius: 0.25rem;
  transition: background 0.2s, color 0.2s;
}
.admin-table-btn:hover { background: rgba(16, 185, 129, 0.15); }
.admin-table-btn-danger { color: #fca5a5; }
.admin-table-btn-danger:hover:not(:disabled) { background: rgba(239, 68, 68, 0.15); }
.admin-table-btn:disabled { opacity: 0.5; cursor: default; }

/* Modal */
.admin-modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.75);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 1rem;
}
.admin-modal {
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 1rem;
  max-width: 520px;
  width: 100%;
  max-height: 90vh;
  overflow: auto;
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.5);
}
.admin-modal-sm { max-width: 400px; }
.admin-modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1.25rem 1.5rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
}
.admin-modal-title { font-size: 1.25rem; font-weight: 600; color: #fff; margin: 0; }
.admin-modal-close {
  width: 2.25rem;
  height: 2.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  background: rgba(255, 255, 255, 0.06);
  border: none;
  border-radius: 0.5rem;
  color: #9ca3af;
  cursor: pointer;
  transition: color 0.2s, background 0.2s;
}
.admin-modal-close:hover { color: #fff; background: rgba(255, 255, 255, 0.1); }
.admin-modal-body { padding: 1.25rem 1.5rem; }
.admin-form-label {
  display: flex;
  flex-direction: column;
  gap: 0.35rem;
  margin-bottom: 1rem;
}
.admin-form-label > span { font-size: 0.875rem; color: #9ca3af; }
.admin-input {
  width: 100%;
  padding: 0.6rem 0.75rem;
  font-size: 0.9375rem;
  color: #fff;
  background: #0a0b14;
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 0.5rem;
  outline: none;
}
.admin-input:focus { border-color: #10b981; }
.admin-textarea { resize: vertical; min-height: 120px; }
.admin-modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid rgba(255, 255, 255, 0.06);
}
.admin-delete-text { color: #d1d5db; font-size: 0.9375rem; margin: 0 0 1rem; }

.admin-modal-enter-active,
.admin-modal-leave-active { transition: opacity 0.2s; }
.admin-modal-enter-from,
.admin-modal-leave-to { opacity: 0; }
.admin-modal-enter-active .admin-modal,
.admin-modal-leave-active .admin-modal { transition: transform 0.2s; }
.admin-modal-enter-from .admin-modal,
.admin-modal-leave-to .admin-modal { transform: scale(0.96); }

@media (max-width: 768px) {
  .admin-sidebar { width: 64px; max-width: 64px; }
  .admin-main { margin-left: 64px; }
  .admin-sidebar-title,
  .admin-nav-text,
  .admin-sidebar-user-info { display: none !important; }
  .admin-sidebar-brand { justify-content: center; padding: 0.75rem 0.5rem; }
  .admin-sidebar-logo { margin: 0 auto; }
  .admin-nav-item { justify-content: center; padding: 0.6rem; }
  .admin-sidebar-user { justify-content: center; padding: 0.5rem; }
}
</style>
