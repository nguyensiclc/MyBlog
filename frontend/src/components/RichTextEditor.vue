<template>
  <div class="rich-text-editor">
    <div v-if="editor" class="rich-text-editor-toolbar">
      <span class="rte-group">
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('bold') }" @click="editor.chain().focus().toggleBold().run()" title="In đậm"><strong>B</strong></button>
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('italic') }" @click="editor.chain().focus().toggleItalic().run()" title="In nghiêng"><em>I</em></button>
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('strike') }" @click="editor.chain().focus().toggleStrike().run()" title="Gạch ngang"><s>S</s></button>
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('code') }" @click="editor.chain().focus().toggleCode().run()" title="Mã nội tuyến"><code>&lt;&gt;</code></button>
      </span>
      <span class="rte-sep" aria-hidden="true"></span>
      <span class="rte-group">
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }" @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" title="Tiêu đề 1">H1</button>
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }" @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" title="Tiêu đề 2">H2</button>
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }" @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" title="Tiêu đề 3">H3</button>
      </span>
      <span class="rte-sep" aria-hidden="true"></span>
      <span class="rte-group">
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('bulletList') }" @click="editor.chain().focus().toggleBulletList().run()" title="Danh sách dấu đầu dòng">•</button>
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('orderedList') }" @click="editor.chain().focus().toggleOrderedList().run()" title="Danh sách đánh số">1.</button>
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('blockquote') }" @click="editor.chain().focus().toggleBlockquote().run()" title="Trích dẫn">"</button>
        <button type="button" class="rte-btn" :class="{ 'is-active': editor.isActive('codeBlock') }" @click="editor.chain().focus().toggleCodeBlock().run()" title="Đoạn code">&lt;/&gt;</button>
      </span>
      <span class="rte-sep" aria-hidden="true"></span>
      <span class="rte-group">
        <button type="button" class="rte-btn" @click="editor.chain().focus().setHorizontalRule().run()" title="Đường kẻ ngang">―</button>
      </span>
      <span class="rte-sep" aria-hidden="true"></span>
      <span class="rte-group">
        <button type="button" class="rte-btn" @click="editor.chain().focus().undo().run()" :disabled="!editor.can().undo()" title="Hoàn tác">↶</button>
        <button type="button" class="rte-btn" @click="editor.chain().focus().redo().run()" :disabled="!editor.can().redo()" title="Làm lại">↷</button>
      </span>
    </div>
    <EditorContent :editor="editor" class="rich-text-editor-content" />
  </div>
</template>

<script setup>
import { useEditor, EditorContent } from '@tiptap/vue-3';
import StarterKit from '@tiptap/starter-kit';
import { watch } from 'vue';

const props = defineProps({
  modelValue: { type: String, default: '' }
});

const emit = defineEmits(['update:modelValue']);

const editor = useEditor({
  content: props.modelValue || '',
  extensions: [StarterKit],
  editorProps: {
    attributes: {
      class: 'rte-prose',
      'data-placeholder': 'Nhập nội dung giới thiệu / CV tại đây...'
    }
  },
  onUpdate: ({ editor: e }) => {
    emit('update:modelValue', e.getHTML());
  }
});

watch(() => props.modelValue, (val) => {
  if (!editor.value) return;
  const current = editor.value.getHTML();
  if (val !== current) {
    editor.value.commands.setContent(val || '', false);
  }
});
</script>

<style scoped>
.rich-text-editor {
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 0.5rem;
  overflow: hidden;
  background: #0a0b14;
}
.rich-text-editor-toolbar {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 0.25rem;
  padding: 0.5rem 0.75rem;
  background: #14151f;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}
.rte-group {
  display: inline-flex;
  align-items: center;
  gap: 2px;
}
.rte-sep {
  width: 1px;
  height: 20px;
  background: rgba(255, 255, 255, 0.15);
  margin: 0 0.15rem;
  flex-shrink: 0;
}
.rte-btn {
  min-width: 32px;
  height: 32px;
  padding: 0 8px;
  font-size: 0.8125rem;
  color: #9ca3af;
  background: transparent;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: color 0.15s, background 0.15s;
}
.rte-btn:hover:not(:disabled) {
  color: #fff;
  background: rgba(255, 255, 255, 0.08);
}
.rte-btn.is-active {
  color: #10b981;
  background: rgba(16, 185, 129, 0.2);
}
.rte-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}
.rte-btn code { font-size: 0.75rem; }
.rich-text-editor-content {
  min-height: 280px;
}
.rich-text-editor-content :deep(.ProseMirror) {
  min-height: 280px;
  padding: 1rem 1.25rem;
  outline: none;
  color: #e5e7eb;
  line-height: 1.65;
}
.rich-text-editor-content :deep(.ProseMirror p.is-editor-empty:first-child::before) {
  content: attr(data-placeholder);
  color: #6b7280;
  float: left;
  height: 0;
  pointer-events: none;
}
.rich-text-editor-content :deep(.ProseMirror h2) { font-size: 1.25rem; margin: 1.25em 0 0.5em; color: #fff; }
.rich-text-editor-content :deep(.ProseMirror h3) { font-size: 1.1rem; margin: 1em 0 0.4em; color: #e5e7eb; }
.rich-text-editor-content :deep(.ProseMirror ul) { padding-left: 1.5rem; margin: 0.5em 0; }
.rich-text-editor-content :deep(.ProseMirror ol) { padding-left: 1.5rem; margin: 0.5em 0; }
.rich-text-editor-content :deep(.ProseMirror blockquote) {
  border-left: 4px solid #10b981;
  padding-left: 1rem;
  margin: 0.75em 0;
  color: #9ca3af;
}
.rich-text-editor-content :deep(.ProseMirror pre) {
  background: #14151f;
  padding: 1rem;
  border-radius: 0.5rem;
  overflow-x: auto;
  font-size: 0.875rem;
  margin: 0.75em 0;
}
.rich-text-editor-content :deep(.ProseMirror hr) {
  border: none;
  border-top: 1px solid rgba(255, 255, 255, 0.15);
  margin: 1.25em 0;
}
</style>
