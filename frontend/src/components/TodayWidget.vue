<template>
  <div class="today-widget">
    <div class="today-widget-item today-widget-clock">
      <span class="today-widget-icon" aria-hidden="true">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/>
        </svg>
      </span>
      <div class="today-widget-body">
        <span class="today-widget-time">{{ currentTime }}</span>
        <span class="today-widget-date">{{ dateLabel }}</span>
      </div>
    </div>
    <div class="today-widget-item today-widget-lunar">
      <span class="today-widget-icon" aria-hidden="true">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"/>
        </svg>
      </span>
      <div class="today-widget-body">
        <span class="today-widget-lunar-label">Âm lịch</span>
        <span class="today-widget-lunar-value">{{ lunarLabel }}</span>
        <span class="today-widget-lunar-canchi" v-if="lunarCanChi">{{ lunarCanChi }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { getLunarDate, getDayCanChi, getYearCanChi, getMonthCanChi } from '@dqcai/vn-lunar';

const currentTime = ref('00:00:00');
const dateLabel = ref('');
const lunarLabel = ref('—');
const lunarCanChi = ref('');

function updateClock() {
  const now = new Date();
  currentTime.value = now.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false });
  dateLabel.value = now.toLocaleDateString('vi-VN', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' });
}

function updateLunar() {
  try {
    const now = new Date();
    const lunar = getLunarDate(now.getDate(), now.getMonth() + 1, now.getFullYear());
    const monthNames = ['Giêng', 'Hai', 'Ba', 'Tư', 'Năm', 'Sáu', 'Bảy', 'Tám', 'Chín', 'Mười', 'Mười một', 'Chạp'];
    const dayStr = lunar.day <= 10 ? `Mùng ${lunar.day}` : `Ngày ${lunar.day}`;
    lunarLabel.value = `${dayStr} tháng ${monthNames[lunar.month - 1]} năm ${lunar.year}${lunar.leap ? ' (nhuận)' : ''}`;
    const dayChi = getDayCanChi(lunar.jd);
    const monthChi = getMonthCanChi(lunar.month, lunar.year);
    const yearChi = getYearCanChi(lunar.year);
    lunarCanChi.value = `${dayChi} · ${monthChi} · ${yearChi}`;
  } catch {
    lunarLabel.value = '—';
    lunarCanChi.value = '';
  }
}

let clockInterval;

onMounted(() => {
  updateClock();
  updateLunar();
  clockInterval = setInterval(() => {
    updateClock();
    updateLunar();
  }, 1000);
});

onBeforeUnmount(() => {
  if (clockInterval) clearInterval(clockInterval);
});
</script>

<style scoped>
.today-widget {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem 1.5rem;
  align-items: stretch;
}

.today-widget-item {
  display: flex;
  align-items: flex-start;
  gap: 0.75rem;
  padding: 0.875rem 1rem;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 0.5rem;
  min-width: 0;
  flex: 1 1 200px;
}

.today-widget-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  flex-shrink: 0;
  border-radius: 0.5rem;
  background: rgba(16, 185, 129, 0.12);
  color: #10b981;
}

.today-widget-icon svg {
  width: 18px;
  height: 18px;
}

.today-widget-lunar .today-widget-icon {
  background: rgba(139, 92, 246, 0.15);
  color: #a78bfa;
}

.today-widget-body {
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
  min-width: 0;
}

.today-widget-time {
  font-size: 1.25rem;
  font-weight: 700;
  color: #10b981;
  font-variant-numeric: tabular-nums;
  letter-spacing: 0.02em;
  line-height: 1.2;
}

.today-widget-date {
  font-size: 0.8125rem;
  color: #d1d5db;
  line-height: 1.35;
}

.today-widget-lunar-label {
  font-size: 0.6875rem;
  text-transform: uppercase;
  letter-spacing: 0.06em;
  color: #9ca3af;
}

.today-widget-lunar-value {
  font-size: 0.875rem;
  color: #e5e7eb;
  line-height: 1.35;
}

.today-widget-lunar-canchi {
  font-size: 0.75rem;
  color: #a78bfa;
  line-height: 1.3;
}

@media (max-width: 640px) {
  .today-widget { flex-direction: column; }
  .today-widget-item { flex: 1 1 auto; }
}
</style>
