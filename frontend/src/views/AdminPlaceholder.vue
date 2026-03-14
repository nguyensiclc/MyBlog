<template>
  <main class="admin-placeholder">
    <section class="admin-hero">
      <h1>Trang quản trị</h1>
      <p>Nội dung trang quản trị sẽ được bổ sung sau.</p>
      <button class="admin-back" @click="$router.push({ name: 'home' })">← Quay lại trang chủ</button>
    </section>
  </main>
</template>

<style scoped>
.admin-placeholder { min-height: 100vh; padding-top: 5rem; }
.admin-hero {
  max-width: 32rem;
  margin: 0 auto;
  padding: 3rem 1.5rem;
  text-align: center;
}
.admin-hero h1 { font-size: 1.75rem; color: #fff; margin: 0 0 0.5rem; }
.admin-hero p { color: #9ca3af; margin: 0 0 1.5rem; }
.admin-back {
  padding: 0.5rem 1rem;
  color: #10b981;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 0.9375rem;
}
.admin-back:hover { text-decoration: underline; }
</style>
