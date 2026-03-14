import { defineStore } from 'pinia';

export const useBlogStore = defineStore('blog', {
  state: () => ({
    posts: [
      {
        id: 1,
        title: 'Xây blog cá nhân với Vue 3, Vite và thiết kế kiểu Framer',
        date: '12 Mar 2026',
        readTime: 8,
        excerpt:
          'Một walkthrough chi tiết cách mình dựng blog cá nhân bằng Vue 3, kết hợp layout giống các template đẹp trên Framer.',
        tags: ['Vue 3', 'Vite', 'UI/UX']
      },
      {
        id: 2,
        title: 'Ghi chú nhỏ về performance khi làm SPA',
        date: '02 Mar 2026',
        readTime: 6,
        excerpt:
          'Những tối ưu đơn giản nhưng hiệu quả để giữ cho SPA chạy mượt, từ code-splitting tới lazy loading và caching.',
        tags: ['Performance', 'SPA']
      },
      {
        id: 3,
        title: 'Từ landing page crypto sang blog cá nhân',
        date: '20 Feb 2026',
        readTime: 5,
        excerpt:
          'Cách mình tái sử dụng layout của một landing page crypto và biến nó thành trang blog, vẫn giữ vibe hiện đại.',
        tags: ['Crypto', 'Frontend', 'Refactor']
      },
      {
        id: 4,
        title: 'Pinia trong dự án thực tế: đơn giản nhưng đủ dùng',
        date: '05 Feb 2026',
        readTime: 7,
        excerpt:
          'Trải nghiệm dùng Pinia để quản lý state cho app nhỏ đến trung bình, và những pattern mình thấy hữu ích.',
        tags: ['Pinia', 'State Management']
      },
      {
        id: 5,
        title: 'Một vài pattern UI mình thích ở Framer',
        date: '22 Jan 2026',
        readTime: 9,
        excerpt:
          'Nhìn lại các pattern animation, layout và micro interaction hay gặp trên Framer và cách mình port sang code.',
        tags: ['Framer', 'Animation', 'Design']
      },
      {
        id: 6,
        title: 'Làm việc từ xa và giữ năng suất như dev frontend',
        date: '03 Jan 2026',
        readTime: 10,
        excerpt:
          'Một vài tip cá nhân để giữ năng lượng, tập trung và không bị burnout khi làm remote dài hạn.',
        tags: ['Lifestyle', 'Remote Work']
      }
    ]
  }),
  getters: {
    featuredPosts: (state) => state.posts.slice(0, 6)
  }
});

