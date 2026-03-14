<template>
  <svg
    xmlns="http://www.w3.org/2000/svg"
    :width="size"
    :height="size"
    viewBox="0 0 24 24"
    fill="currentColor"
    aria-hidden="true"
  >
    <!-- TNC monogram cách điệu -->
    <!-- T: thanh ngang + thân dọc -->
    <path d="M2 4h4v1.3H3.3v13.4H2.1V5.3H2V4z" />
    <!-- N: hai nét dọc + chéo (trái trên xuống phải dưới) -->
    <path d="M7 4v16h1V4l6.5 16h1V4h-1v16L8 4H7z" />
    <!-- C: thân trái + cung trên và dưới (C mở bên phải) -->
    <path d="M14 4L14 20Q20 20 20 14Q20 4 14 4z" />
  </svg>
</template>

<script setup>
defineProps({
  size: { type: [Number, String], default: 24 }
});
</script>
