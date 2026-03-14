# MyBlog

> **Fullstack blog cá nhân** — Vue 3 + Spring Boot, JWT auth.

[![Vue 3](https://img.shields.io/badge/Vue-3.5-4FC08D?logo=vue.js)](https://vuejs.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?logo=spring)](https://spring.io/projects/spring-boot)
[![Vite](https://img.shields.io/badge/Vite-6-646CFF?logo=vite)](https://vitejs.dev/)

---

## ✨ Tính năng

---

## 🛠 Tech stack

| Phần        | Công nghệ |
|------------|-----------|
| Frontend   | Vue 3, Vue Router, Pinia, Vite |
| Backend    | Spring Boot 3, Spring Security, JWT |
| Database   | H2 (dev) / có thể đổi PostgreSQL |
| Editor     | TipTap (rich text) |

---

## 🚀 Chạy dự án

### Yêu cầu

- **Node.js** 18+ (frontend)
- **Java 17+**, **Maven** (backend)

### Backend

```bash
cd backend
mvn spring-boot:run
```

API chạy tại: `http://localhost:8080`

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Giao diện: `http://localhost:5173`

---

## 📁 Cấu trúc

```
MyBlog/
├── backend/          # Spring Boot REST API
│   ├── src/main/java/com/example/myblog/
│   │   ├── auth/     # Login, register, JWT
│   │   ├── post/     # Bài viết
│   │   ├── topic/    # Chủ đề
│   │   ├── about/   # Trang giới thiệu
│   │   ├── quote/   # Trích dẫn
│   │   ├── user/    # User, profile
│   │   └── security/
│   └── src/main/resources/
└── frontend/         # Vue 3 SPA
    └── src/
        ├── views/   # Trang (BlogHome, Admin, Login, About, Profile)
        ├── components/
        ├── stores/  # Pinia
        └── router/
```

---

## 📄 License

MIT
