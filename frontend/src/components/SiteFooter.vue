<template>
  <footer>
    <div class="container">
      <div class="footer-quote-row">
        <div class="footer-quote-block">
          <div class="footer-brand">
            <div class="logo-icon">
              <LogoIcon :size="20" />
            </div>
            <span class="logo-text">TNC</span>
          </div>
          <p class="footer-description">
            {{ dailyQuote.content || 'Góc nhỏ chia sẻ ý tưởng, code và cuộc sống làm dev.' }}
            <span v-if="dailyQuote.author" class="footer-quote-author">— {{ dailyQuote.author }}</span>
          </p>
        </div>
        <TodayWidget class="footer-today-widget" />
      </div>

      <div class="footer-grid" v-if="showFooterNav">
          <div>
            <h3 class="footer-heading">Điều hướng</h3>
            <ul class="footer-links">
              <li><button class="footer-link" @click="scrollToId('top')">Trang chủ</button></li>
              <li><button class="footer-link" @click="scrollToId('posts')">Bài viết</button></li>
              <li><router-link to="/about" class="footer-link">About</router-link></li>
            </ul>
          </div>
          <div>
            <h3 class="footer-heading">Kết nối</h3>
            <ul class="footer-links">
              <li><a href="mailto:you@example.com" class="footer-link">Email</a></li>
              <li><a href="https://github.com" target="_blank" rel="noreferrer" class="footer-link">GitHub</a></li>
              <li><a href="https://twitter.com" target="_blank" rel="noreferrer" class="footer-link">Twitter</a></li>
            </ul>
          </div>
          <div>
            <h3 class="footer-heading">Khác</h3>
            <ul class="footer-links">
              <li><span class="footer-link">© 2026 TNC</span></li>
            </ul>
          </div>
      </div>

      <section class="footer-tech" aria-labelledby="footer-tech-heading">
        <h2 id="footer-tech-heading" class="footer-tech-heading">Built with</h2>
        <p class="footer-tech-desc">Vue · Vite · Spring Boot · PostgreSQL · AI-assisted</p>
        <div class="footer-tech-grid">
          <div class="footer-tech-card" style="--tech-accent: #42b883;">
            <div class="footer-tech-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M12 2L3 7v10l9 5 9-5V7l-9-5z"/><path d="M12 22V12M3 7l9 5 9-5M12 12l9-5M12 12L3 7"/></svg>
            </div>
            <span class="footer-tech-name">Vue 3</span>
            <span class="footer-tech-tag">Reactive UI</span>
          </div>
          <div class="footer-tech-card" style="--tech-accent: #646cff;">
            <div class="footer-tech-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M12 2L2 7l10 5 10-5-10-5z"/><path d="M2 17l10 5 10-5"/><path d="M2 12l10 5 10-5"/></svg>
            </div>
            <span class="footer-tech-name">Vite</span>
            <span class="footer-tech-tag">Build & dev server</span>
          </div>
          <div class="footer-tech-card" style="--tech-accent: #ffd859;">
            <div class="footer-tech-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M4 4h16a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2z"/><path d="M8 10h8M8 14h5"/></svg>
            </div>
            <span class="footer-tech-name">Pinia</span>
            <span class="footer-tech-tag">State management</span>
          </div>
          <div class="footer-tech-card" style="--tech-accent: #4fc08d;">
            <div class="footer-tech-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M5 12h14M12 5l7 7-7 7"/></svg>
            </div>
            <span class="footer-tech-name">Vue Router</span>
            <span class="footer-tech-tag">SPA routing</span>
          </div>
          <div class="footer-tech-card" style="--tech-accent: #6db33f;">
            <div class="footer-tech-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2z"/><path d="M12 6v6l4 2"/></svg>
            </div>
            <span class="footer-tech-name">Spring Boot</span>
            <span class="footer-tech-tag">Backend API</span>
          </div>
          <div class="footer-tech-card" style="--tech-accent: #336791;">
            <div class="footer-tech-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M4 4h16v16H4z"/><path d="M4 8h16M4 12h16M4 16h8"/></svg>
            </div>
            <span class="footer-tech-name">PostgreSQL</span>
            <span class="footer-tech-tag">Database</span>
          </div>
          <div class="footer-tech-card" style="--tech-accent: #10b981;">
            <div class="footer-tech-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M12 2v4M12 18v4M4.93 4.93l2.83 2.83M16.24 16.24l2.83 2.83M2 12h4M18 12h4M4.93 19.07l2.83-2.83M16.24 7.76l2.83-2.83"/><path d="M12 8a4 4 0 1 1 0 8 4 4 0 0 1 0-8z"/></svg>
            </div>
            <span class="footer-tech-name">JWT</span>
            <span class="footer-tech-tag">Auth</span>
          </div>
          <div class="footer-tech-card" style="--tech-accent: #8b5cf6;">
            <div class="footer-tech-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M4 6h16M4 12h16M4 18h8"/><path d="M14 18h6"/></svg>
            </div>
            <span class="footer-tech-name">Tiptap</span>
            <span class="footer-tech-tag">Rich text editor</span>
          </div>
          <div class="footer-tech-card footer-tech-card-ai" style="--tech-accent: #f59e0b;">
            <div class="footer-tech-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M12 2a4 4 0 0 1 4 4v2a4 4 0 0 1-4 4 4 4 0 0 1-4-4V6a4 4 0 0 1 4-4z"/><path d="M4 20v-2a4 4 0 0 1 4-4h8a4 4 0 0 1 4 4v2"/><path d="M9 12l2 2 4-4"/></svg>
            </div>
            <span class="footer-tech-name">AI (Cursor)</span>
            <span class="footer-tech-tag">Code & design hỗ trợ bởi AI</span>
          </div>
        </div>
      </section>

      <div class="footer-bottom">
        <p>© 2026 TNC · Cảm ơn bạn đã ghé thăm.</p>
      </div>
    </div>
  </footer>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import LogoIcon from './LogoIcon.vue';
import TodayWidget from './TodayWidget.vue';
import { API_BASE } from '../api/config';

/** Bật lại thành true khi muốn hiện Điều hướng, Kết nối, Khác. */
const showFooterNav = false;

const dailyQuote = ref({ content: '', author: null });

onMounted(async () => {
  try {
    const res = await fetch(`${API_BASE}/api/quotes/daily`);
    if (res.ok) {
      const data = await res.json();
      dailyQuote.value = { content: data.content || '', author: data.author || null };
    }
  } catch (_) {}
});

function scrollToId(id) {
  const el = document.getElementById(id);
  if (el) {
    el.scrollIntoView({ behavior: 'smooth' });
  }
}
</script>

