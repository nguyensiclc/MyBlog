<template>
  <div class="app-root">
    <SiteNavbar v-if="!isAdminRoute" />
    <AdminNavbar v-else />
    <RouterView />
    <SiteFooter v-if="!isAdminRoute" />
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useRoute, RouterView } from 'vue-router';
import SiteNavbar from './components/SiteNavbar.vue';
import AdminNavbar from './components/AdminNavbar.vue';
import SiteFooter from './components/SiteFooter.vue';

const route = useRoute();
const isAdminRoute = computed(() => route.name === 'admin');
</script>

