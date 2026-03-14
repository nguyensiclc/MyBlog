<template>
  <Teleport to="body">
    <Transition name="crop-modal">
      <div v-if="show" class="crop-modal-overlay" @click.self="onCancel">
        <div class="crop-modal">
          <div class="crop-modal-header">
            <div class="crop-modal-header-text">
              <h3 class="crop-modal-title">Cắt ảnh đại diện</h3>
              <p class="crop-modal-subtitle">Kéo và chỉnh khung để chọn vùng dùng làm avatar</p>
            </div>
            <button
              type="button"
              class="crop-modal-close"
              aria-label="Đóng"
              @click="onCancel"
            >
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="18" y1="6" x2="6" y2="18"></line>
                <line x1="6" y1="6" x2="18" y2="18"></line>
              </svg>
            </button>
          </div>

          <div class="crop-modal-body">
            <div
              ref="imageWrapRef"
              class="crop-image-wrap"
              @mousemove="onMouseMove"
              @mouseup="onMouseUp"
              @mouseleave="onMouseUp"
            >
              <img
                ref="imageRef"
                :src="imageUrl"
                alt=""
                class="crop-image"
                draggable="false"
                @load="onImageLoad"
              />
              <div
                class="crop-box"
                :style="cropBoxStyle"
                @mousedown="startDrag"
              >
                <div class="crop-box-border" />
                <div
                  class="crop-resize-handle"
                  @mousedown.stop="startResize"
                />
              </div>
            </div>
          </div>

          <div class="crop-modal-footer">
            <button type="button" class="crop-btn crop-btn-cancel" @click="onCancel">
              Hủy
            </button>
            <button type="button" class="crop-btn crop-btn-confirm" :disabled="uploading" @click="onConfirm">
              {{ uploading ? 'Đang tải...' : 'Xác nhận' }}
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import { ref, computed, watch } from 'vue';

const props = defineProps({
  show: Boolean,
  imageUrl: { type: String, default: '' },
  imageFile: { type: File, default: null },
  uploading: Boolean
});

const emit = defineEmits(['cancel', 'confirm']);

const imageRef = ref(null);
const imageWrapRef = ref(null);

const crop = ref({ x: 10, y: 10, size: 80 });
const imageDisplaySize = ref({ w: 1, h: 1 });
const imageNaturalSize = ref({ w: 1, h: 1 });

let dragging = false;
let resizing = false;
let startX = 0;
let startY = 0;
let startCrop = { x: 0, y: 0, size: 0 };

const cropBoxStyle = computed(() => ({
  left: crop.value.x + '%',
  top: crop.value.y + '%',
  width: crop.value.size + '%',
  height: crop.value.size + '%',
  bottom: (100 - crop.value.y - crop.value.size) + '%',
  right: (100 - crop.value.x - crop.value.size) + '%'
}));

function clampCrop() {
  let { x, y, size } = crop.value;
  size = Math.max(15, Math.min(100, size));
  x = Math.max(0, Math.min(100 - size, x));
  y = Math.max(0, Math.min(100 - size, y));
  crop.value = { x, y, size };
}

function onImageLoad() {
  const img = imageRef.value;
  if (!img) return;
  const wrap = imageWrapRef.value;
  if (!wrap) return;
  imageNaturalSize.value = { w: img.naturalWidth, h: img.naturalHeight };
  const rect = img.getBoundingClientRect();
  imageDisplaySize.value = { w: rect.width, h: rect.height };
  crop.value = { x: 15, y: 15, size: 70 };
  clampCrop();
}

function startDrag(e) {
  if (resizing) return;
  dragging = true;
  startX = e.clientX;
  startY = e.clientY;
  startCrop = { ...crop.value };
}

function startResize(e) {
  e.stopPropagation();
  resizing = true;
  dragging = false;
  startX = e.clientX;
  startY = e.clientY;
  startCrop = { ...crop.value };
}

function onMouseMove(e) {
  if (dragging) {
    const img = imageRef.value;
    const wrap = imageWrapRef.value;
    if (!img || !wrap) return;
    const rect = wrap.getBoundingClientRect();
    const dx = ((e.clientX - startX) / rect.width) * 100;
    const dy = ((e.clientY - startY) / rect.height) * 100;
    crop.value = {
      x: startCrop.x + dx,
      y: startCrop.y + dy,
      size: startCrop.size
    };
    clampCrop();
  } else if (resizing) {
    const img = imageRef.value;
    const wrap = imageWrapRef.value;
    if (!img || !wrap) return;
    const rect = wrap.getBoundingClientRect();
    const diag = (e.clientX - startX + e.clientY - startY) / 2;
    const delta = (diag / Math.min(rect.width, rect.height)) * 100;
    crop.value = {
      x: startCrop.x,
      y: startCrop.y,
      size: startCrop.size + delta
    };
    clampCrop();
  }
}

function onMouseUp() {
  dragging = false;
  resizing = false;
}

function onCancel() {
  emit('cancel');
}

function getCroppedBlob() {
  const img = imageRef.value;
  if (!img || !imageNaturalSize.value.w) return null;
  const { x, y, size } = crop.value;
  const nat = imageNaturalSize.value;
  const scaleX = nat.w / 100;
  const scaleY = nat.h / 100;
  const sx = x * scaleX;
  const sy = y * scaleY;
  const sw = size * scaleX;
  const sh = size * scaleY;
  const canvas = document.createElement('canvas');
  canvas.width = sw;
  canvas.height = sh;
  const ctx = canvas.getContext('2d');
  ctx.drawImage(img, sx, sy, sw, sh, 0, 0, sw, sh);
  return new Promise((resolve) => {
    canvas.toBlob((blob) => resolve(blob), 'image/jpeg', 0.92);
  });
}

async function onConfirm() {
  const blob = await getCroppedBlob();
  if (blob) emit('confirm', blob);
}

watch(() => props.show, (v) => {
  if (v) crop.value = { x: 15, y: 15, size: 70 };
});
</script>

<style scoped>
.crop-modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.75);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 1rem;
}
.crop-modal {
  background: #14151f;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 1rem;
  max-width: 520px;
  width: 100%;
  max-height: 90vh;
  display: flex;
  flex-direction: column;
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.5);
}
.crop-modal-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  padding: 1.25rem 1.5rem 0;
}
.crop-modal-header-text { flex: 1; min-width: 0; }
.crop-modal-title {
  margin: 0;
  font-size: 1.25rem;
  color: #fff;
}
.crop-modal-subtitle {
  margin: 0.35rem 0 0;
  font-size: 0.875rem;
  color: #9ca3af;
}
.crop-modal-close {
  flex-shrink: 0;
  width: 2.25rem;
  height: 2.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 0.5rem;
  color: #9ca3af;
  cursor: pointer;
  transition: color 0.2s, background 0.2s;
}
.crop-modal-close:hover {
  color: #fff;
  background: rgba(255, 255, 255, 0.1);
}
.crop-modal-body {
  padding: 1.25rem 1.5rem;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}
.crop-image-wrap {
  position: relative;
  max-height: 60vh;
  max-width: 100%;
  display: inline-block;
  user-select: none;
}
.crop-image {
  display: block;
  max-width: 100%;
  max-height: 60vh;
  width: auto;
  height: auto;
  vertical-align: middle;
}
.crop-box {
  position: absolute;
  cursor: move;
  box-sizing: border-box;
}
.crop-box-border {
  position: absolute;
  inset: 0;
  border: 2px solid #10b981;
  border-radius: 50%;
  box-shadow: 0 0 0 9999px rgba(0, 0, 0, 0.5);
  pointer-events: none;
}
.crop-resize-handle {
  position: absolute;
  right: 4px;
  bottom: 4px;
  width: 20px;
  height: 20px;
  cursor: nwse-resize;
  background: linear-gradient(135deg, transparent 45%, #10b981 45%, #10b981 55%, transparent 55%);
  border-radius: 2px;
}
.crop-modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  padding: 1rem 1.5rem 1.25rem;
  border-top: 1px solid rgba(255, 255, 255, 0.06);
}
.crop-btn {
  padding: 0.6rem 1.25rem;
  font-size: 0.9375rem;
  font-weight: 600;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background 0.2s;
}
.crop-btn-cancel {
  background: rgba(255, 255, 255, 0.1);
  color: #d1d5db;
}
.crop-btn-cancel:hover {
  background: rgba(255, 255, 255, 0.15);
}
.crop-btn-confirm {
  background: #10b981;
  color: #fff;
}
.crop-btn-confirm:hover:not(:disabled) {
  background: #059669;
}
.crop-btn-confirm:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.crop-modal-enter-active,
.crop-modal-leave-active {
  transition: opacity 0.2s;
}
.crop-modal-enter-from,
.crop-modal-leave-to {
  opacity: 0;
}
.crop-modal-enter-active .crop-modal,
.crop-modal-leave-active .crop-modal {
  transition: transform 0.2s;
}
.crop-modal-enter-from .crop-modal,
.crop-modal-leave-to .crop-modal {
  transform: scale(0.95);
}
</style>
