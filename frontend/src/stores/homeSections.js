import { defineStore } from 'pinia';
import { ref } from 'vue';

const STORAGE_KEY = 'home_sections';

const DEFAULTS = {
  showFeaturedPosts: false,
  showBlogIntro: false,
  showFaq: false
};

function loadFromStorage() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return { ...DEFAULTS };
    const data = JSON.parse(raw);
    return {
      showFeaturedPosts: !!data.showFeaturedPosts,
      showBlogIntro: !!data.showBlogIntro,
      showFaq: !!data.showFaq
    };
  } catch {
    return { ...DEFAULTS };
  }
}

function saveToStorage(data) {
  try {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
  } catch (_) {}
}

export const useHomeSectionsStore = defineStore('homeSections', () => {
  const showFeaturedPosts = ref(DEFAULTS.showFeaturedPosts);
  const showBlogIntro = ref(DEFAULTS.showBlogIntro);
  const showFaq = ref(DEFAULTS.showFaq);

  function init() {
    const data = loadFromStorage();
    showFeaturedPosts.value = data.showFeaturedPosts;
    showBlogIntro.value = data.showBlogIntro;
    showFaq.value = data.showFaq;
  }

  function setFeaturedPosts(value) {
    showFeaturedPosts.value = !!value;
    saveToStorage({
      showFeaturedPosts: showFeaturedPosts.value,
      showBlogIntro: showBlogIntro.value,
      showFaq: showFaq.value
    });
  }

  function setBlogIntro(value) {
    showBlogIntro.value = !!value;
    saveToStorage({
      showFeaturedPosts: showFeaturedPosts.value,
      showBlogIntro: showBlogIntro.value,
      showFaq: showFaq.value
    });
  }

  function setFaq(value) {
    showFaq.value = !!value;
    saveToStorage({
      showFeaturedPosts: showFeaturedPosts.value,
      showBlogIntro: showBlogIntro.value,
      showFaq: showFaq.value
    });
  }

  init();

  return {
    showFeaturedPosts,
    showBlogIntro,
    showFaq,
    init,
    setFeaturedPosts,
    setBlogIntro,
    setFaq
  };
});
