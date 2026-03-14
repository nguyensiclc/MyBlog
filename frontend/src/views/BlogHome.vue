<template>
  <main id="top">
    <section class="hero">
      <div class="hero-content">
        <h1>Ghi lại hành trình Dev & Crypto của tôi</h1>
        <p>
          Một góc nhỏ để chia sẻ về frontend, Vue, Web3 và những trải nghiệm cá nhân trong quá trình xây dựng sản phẩm.
        </p>
        <div class="hero-actions">
          <router-link to="/about" class="hero-button">
            <span class="hero-button-icon" aria-hidden="true">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                <circle cx="12" cy="7" r="4"/>
              </svg>
            </span>
            About me
          </router-link>
        </div>
      </div>
    </section>

    <section v-if="homeSections.showFeaturedPosts" id="posts" class="section-alt">
      <div class="container">
        <div class="section-header animate-on-scroll" ref="postsHeaderRef">
          <h2 class="section-title">Bài viết nổi bật</h2>
          <p class="section-subtitle">Những chia sẻ gần đây nhất trên blog</p>
        </div>
        <div class="grid grid-3">
          <article
            v-for="post in posts"
            :key="post.id"
            class="blog-card animate-on-scroll"
            :ref="setPostRefs"
          >
            <div class="blog-meta">
              {{ post.date }} · {{ post.readTime }} phút đọc
            </div>
            <h3 class="blog-title">
              {{ post.title }}
            </h3>
            <p class="blog-excerpt">
              {{ post.excerpt }}
            </p>
            <div class="blog-tags">
              <span v-for="tag in post.tags" :key="tag" class="blog-tag">
                {{ tag }}
              </span>
            </div>
          </article>
        </div>
      </div>
    </section>

    <section v-if="homeSections.showBlogIntro" id="about">
      <div class="container">
        <div class="section-header animate-on-scroll" ref="aboutHeaderRef">
          <h2 class="section-title">Blog này có gì?</h2>
          <p class="section-subtitle">Một chút về cách mình viết và chia sẻ</p>
        </div>
        <div class="grid grid-4">
          <div class="step-card animate-on-scroll" ref="setAboutStepRefs">
            <div class="step-icon">
              <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#10b981" stroke-width="2">
                <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                <circle cx="8.5" cy="7" r="4"></circle>
              </svg>
            </div>
            <h3>Câu chuyện cá nhân</h3>
            <p>Mình ghi lại những bài học, sai lầm và cả niềm vui khi làm dev.</p>
          </div>

          <div class="step-card animate-on-scroll" ref="setAboutStepRefs">
            <div class="step-icon">
              <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#10b981" stroke-width="2">
                <rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect>
                <line x1="1" y1="10" x2="23" y2="10"></line>
              </svg>
            </div>
            <h3>Ghi chú kỹ thuật</h3>
            <p>Các snippet, pattern Vue/FE mà mình hay dùng trong dự án thực tế.</p>
          </div>

          <div class="step-card animate-on-scroll" ref="setAboutStepRefs">
            <div class="step-icon">
              <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#10b981" stroke-width="2">
                <polyline points="17 1 21 5 17 9"></polyline>
                <path d="M3 11V9a4 4 0 0 1 4-4h14"></path>
                <polyline points="7 23 3 19 7 15"></polyline>
                <path d="M21 13v2a4 4 0 0 1-4 4H3"></path>
              </svg>
            </div>
            <h3>Web3 & Crypto</h3>
            <p>Những quan sát cá nhân khi xây dApp, ví điện tử, trải nghiệm sản phẩm.</p>
          </div>

          <div class="step-card animate-on-scroll" ref="setAboutStepRefs">
            <div class="step-icon">
              <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#10b981" stroke-width="2">
                <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
              </svg>
            </div>
            <h3>Kiến thức được chọn lọc</h3>
            <p>Mỗi bài viết đều xuất phát từ vấn đề thật trong dự án mình từng làm.</p>
          </div>
        </div>
      </div>
    </section>

    <section v-if="homeSections.showFaq" id="faq" class="section-alt">
      <div class="container">
        <div class="section-header animate-on-scroll" ref="faqHeaderRef">
          <h2 class="section-title">Câu hỏi thường gặp</h2>
          <p class="section-subtitle">Một vài điều bạn có thể thắc mắc về blog này</p>
        </div>
        <div class="faq-container">
          <div
            v-for="(item, index) in faqs"
            :key="item.question"
            class="faq-item animate-on-scroll"
            :ref="setFaqRefs"
          >
            <button
              class="faq-question"
              :class="{ active: openFaqIndex === index }"
              @click="toggleFaq(index)"
            >
              <span>{{ item.question }}</span>
              <svg class="chevron" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <polyline points="6 9 12 15 18 9"></polyline>
              </svg>
            </button>
            <div class="faq-answer" :class="{ open: openFaqIndex === index }">
              <div class="faq-answer-content">
                {{ item.answer }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { onMounted, onBeforeUnmount, reactive, ref } from 'vue';
import { useBlogStore } from '../stores/blog';
import { useHomeSectionsStore } from '../stores/homeSections';

const blogStore = useBlogStore();
const homeSections = useHomeSectionsStore();
const posts = blogStore.featuredPosts;

const openFaqIndex = ref(null);

const faqs = reactive([
  {
    question: 'Bạn viết blog bao lâu một lần?',
    answer: 'Mình cố gắng viết ít nhất 2 bài mỗi tháng, tập trung vào những gì mình đang làm hoặc học.'
  },
  {
    question: 'Mình có thể lấy lại code demo không?',
    answer: 'Hầu hết các bài kỹ thuật mình sẽ đính kèm repo GitHub hoặc snippet để bạn tham khảo.'
  },
  {
    question: 'Blog này dành cho ai?',
    answer: 'Cho các bạn dev frontend, đặc biệt là những người thích Vue, thiết kế đẹp và hứng thú với Web3.'
  },
  {
    question: 'Có newsletter không?',
    answer: 'Hiện tại mình chưa làm newsletter, nhưng nếu nhiều người quan tâm mình sẽ thêm sau.'
  }
]);

const postsHeaderRef = ref(null);
const aboutHeaderRef = ref(null);
const faqHeaderRef = ref(null);
const postRefs = ref([]);
const faqRefs = ref([]);
const aboutStepRefs = ref([]);

function setPostRefs(el) {
  if (el) postRefs.value.push(el);
}

function setFaqRefs(el) {
  if (el) faqRefs.value.push(el);
}

function setAboutStepRefs(el) {
  if (el) aboutStepRefs.value.push(el);
}

function toggleFaq(index) {
  openFaqIndex.value = openFaqIndex.value === index ? null : index;
}

function scrollToId(id) {
  const el = document.getElementById(id);
  if (el) {
    el.scrollIntoView({ behavior: 'smooth' });
  }
}

function handleScrollAnimations() {
  const elements = [
    postsHeaderRef.value,
    aboutHeaderRef.value,
    faqHeaderRef.value,
    ...postRefs.value,
    ...faqRefs.value,
    ...aboutStepRefs.value
  ].filter(Boolean);

  const viewportHeight = window.innerHeight || document.documentElement.clientHeight;

  elements.forEach((el) => {
    const rect = el.getBoundingClientRect();
    if (rect.top >= 0 && rect.bottom <= viewportHeight + 100) {
      el.classList.add('visible');
    }
  });
}

onMounted(() => {
  window.addEventListener('scroll', handleScrollAnimations);
  window.addEventListener('load', handleScrollAnimations);
  handleScrollAnimations();
});

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScrollAnimations);
  window.removeEventListener('load', handleScrollAnimations);
});
</script>

