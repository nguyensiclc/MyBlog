<template>
  <main class="about-page">
    <section class="about-hero">
      <div class="about-hero-bg" aria-hidden="true"></div>
      <div class="container about-hero-inner">
        <h1 class="about-name">About</h1>
        <p class="about-subtitle">Introduction & CV</p>
      </div>
    </section>

    <section class="about-body">
      <div class="container container-about">
        <div
          v-if="aboutStore.content"
          class="about-content-prose"
          v-html="displayContent"
        />
        <div v-else class="about-empty">
          <p>Nội dung About Me chưa được cập nhật.</p>
          <p class="about-empty-hint">Chỉnh sửa tại <router-link to="/admin">Trang quản trị</router-link> → About Me.</p>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { useAboutStore } from '../stores/about';

const aboutStore = useAboutStore();

/** Giải mã HTML entities (&lt; &gt; &amp; &quot; ...) thành ký tự thật để trình duyệt render HTML, không hiển thị thẻ dạng chữ. */
function decodeHtmlEntities(str) {
  if (!str || typeof str !== 'string') return '';
  return str
    .replace(/&amp;/g, '&')
    .replace(/&lt;/g, '<')
    .replace(/&gt;/g, '>')
    .replace(/&quot;/g, '"')
    .replace(/&#39;/g, "'")
    .replace(/&apos;/g, "'");
}

/** Loại bỏ toàn bộ thuộc tính style trong HTML để chỉ hiển thị nội dung, áp dụng CSS của trang. */
function stripInlineStyles(html) {
  if (!html || typeof html !== 'string') return '';
  const div = document.createElement('div');
  div.innerHTML = html;
  div.querySelectorAll('[style]').forEach((el) => el.removeAttribute('style'));
  return div.innerHTML;
}

const displayContent = computed(() => {
  const raw = aboutStore.content;
  if (!raw) return '';
  const decoded = decodeHtmlEntities(raw);
  return stripInlineStyles(decoded);
});

onMounted(() => {
  aboutStore.init();
});
</script>

<style scoped>
.about-page {
  min-height: 100vh;
  padding-top: 4rem;
  background: #0a0b14;
}

.about-hero {
  position: relative;
  padding: 4rem 1.5rem 4.5rem;
  text-align: center;
  overflow: hidden;
}

.about-hero-bg {
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg, rgba(20, 21, 31, 0.97) 0%, rgba(10, 11, 20, 0) 60%);
  pointer-events: none;
}

.about-hero-inner {
  position: relative;
  max-width: 720px;
  margin: 0 auto;
}

.about-name {
  font-size: clamp(1.75rem, 4vw, 2.5rem);
  font-weight: 700;
  color: #fff;
  margin: 0 0 0.35rem;
  letter-spacing: -0.02em;
  line-height: 1.2;
}

.about-subtitle {
  font-size: 1.125rem;
  color: #9ca3af;
  margin: 0;
}

.about-body {
  padding: 0 1.5rem 4rem;
}

.container-about {
  max-width: 720px;
  margin: 0 auto;
}

.about-content-prose {
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 0.75rem;
  padding: 1.5rem 1.75rem;
  font-size: 0.9375rem;
  color: #d1d5db;
  line-height: 1.75;
}

.about-content-prose :deep(h1) {
  font-size: 1.5rem;
  font-weight: 700;
  color: #fff;
  margin: 1.5rem 0 0.5rem;
  letter-spacing: -0.02em;
}

.about-content-prose :deep(h1:first-child) { margin-top: 0; }

.about-content-prose :deep(h2) {
  font-size: 1.25rem;
  font-weight: 600;
  color: #10b981;
  margin: 1.5rem 0 0.5rem;
  padding-bottom: 0.35rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.about-content-prose :deep(h2:first-child) { margin-top: 0; }

.about-content-prose :deep(h3) {
  font-size: 1.05rem;
  font-weight: 600;
  color: #e5e7eb;
  margin: 1.25rem 0 0.4rem;
}

.about-content-prose :deep(p) { margin: 0 0 0.75em; }

.about-content-prose :deep(ul),
.about-content-prose :deep(ol) {
  padding-left: 1.5rem;
  margin: 0.5em 0;
}

.about-content-prose :deep(ul) { list-style-type: disc; }
.about-content-prose :deep(ol) { list-style-type: decimal; }
.about-content-prose :deep(li) { margin: 0.25em 0; }

.about-content-prose :deep(a) {
  color: #10b981;
  text-decoration: none;
}
.about-content-prose :deep(a:hover) { text-decoration: underline; }

.about-content-prose :deep(em) { font-style: italic; color: #d1d5db; }

.about-content-prose :deep(blockquote) {
  border-left: 4px solid #10b981;
  padding-left: 1rem;
  margin: 1em 0;
  color: #9ca3af;
}

.about-content-prose :deep(pre) {
  background: rgba(0, 0, 0, 0.3);
  padding: 1rem;
  border-radius: 0.5rem;
  overflow-x: auto;
  font-size: 0.875rem;
  margin: 0.75em 0;
}

.about-content-prose :deep(hr) {
  border: none;
  border-top: 1px solid rgba(255, 255, 255, 0.12);
  margin: 1.25em 0;
}

.about-content-prose :deep(code) {
  background: rgba(0, 0, 0, 0.3);
  padding: 0.15em 0.4em;
  border-radius: 4px;
  font-size: 0.9em;
}

.about-content-prose :deep(strong) { color: #e5e7eb; }

.about-content-prose :deep(table) {
  width: 100%;
  border-collapse: collapse;
  margin: 1rem 0;
  font-size: 0.9rem;
}

.about-content-prose :deep(th),
.about-content-prose :deep(td) {
  padding: 0.5rem 0.75rem;
  border: 1px solid rgba(255, 255, 255, 0.12);
  text-align: left;
  color: #d1d5db;
}

.about-content-prose :deep(tr:nth-child(even)) th,
.about-content-prose :deep(tr:nth-child(even)) td {
  background: rgba(255, 255, 255, 0.03);
}

.about-content-prose :deep(th) {
  font-weight: 600;
  color: #e5e7eb;
  width: 30%;
}

.about-empty {
  text-align: center;
  padding: 3rem 1.5rem;
  background: #14151f;
  border: 1px dashed rgba(255, 255, 255, 0.1);
  border-radius: 0.75rem;
  color: #6b7280;
  font-size: 0.9375rem;
}

.about-empty p { margin: 0 0 0.5rem; }
.about-empty-hint { font-size: 0.875rem; margin-top: 0.75rem; }
.about-empty a { color: #10b981; text-decoration: none; }
.about-empty a:hover { text-decoration: underline; }
</style>
