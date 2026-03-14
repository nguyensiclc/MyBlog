# Đẩy MyBlog lên GitHub

Máy bạn chưa cài **GitHub CLI** (`gh`), nên làm theo các bước sau.

## 1. Tạo repository mới trên GitHub

1. Vào **https://github.com/new**
2. **Repository name:** `MyBlog`
3. **Description** (dán nguyên đoạn sau):

```
Fullstack blog cá nhân — Vue 3 + Spring Boot, JWT, quản lý bài viết & chủ đề, trang giới thiệu, trích dẫn. Giao diện tối màu, rich text editor, chống spam đăng nhập.
```

4. Chọn **Public**
5. **Không** tick "Add a README" (repo đã có README từ project)
6. Bấm **Create repository**

## 2. Kết nối và push từ máy bạn

Trong terminal, đứng tại thư mục `MyBlog`, chạy (thay `YOUR_USERNAME` bằng username GitHub của bạn):

```powershell
cd "d:\Code_Carrer\MyBlog"
git remote add origin https://github.com/YOUR_USERNAME/MyBlog.git
git branch -M main
git push -u origin main
```

Nếu GitHub yêu cầu đăng nhập, dùng **Personal Access Token** thay cho mật khẩu (Settings → Developer settings → Personal access tokens).

---

Sau khi push xong, bạn có thể xóa file này: `GITHUB_SETUP.md`.
