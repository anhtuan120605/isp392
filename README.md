# Hoc FullStack Voi Nhau

...existing code...

## Backend API — Yêu cầu kỹ thuật (API spec)

Mục tiêu: mọi người có thể triển khai API backend theo cùng một chuẩn để frontend gọi được ngay.

1) Base URL
- Production ví dụ: https://api.example.com
- Dev: http://localhost:8080

2) Authentication
- Endpoint đăng nhập:
  - POST /auth/login
  - Request JSON: { "email": "user@example.com", "password": "secret" }
  - Response 200 OK: { "token": "<jwt>", "user": { "id": 1, "email": "user@example.com", ... } }
- Cách bảo mật endpoint khác:
  - Mọi endpoint bảo mật yêu cầu header: Authorization: Bearer <token>

3) Các endpoint cần thiết (ví dụ, chỉnh theo model thực tế của dự án)
- POST /auth/login
  - Đăng nhập, trả { token, user }
- POST /users
  - Tạo user mới — 201 Created trả user
- GET /users/me
  - Lấy thông tin user hiện tại — 200 OK (auth required)
- GET /items
  - Lấy danh sách — 200 OK trả array JSON
- POST /items
  - Tạo item mới — 201 Created trả item
- GET /items/:id
  - Lấy chi tiết — 200 OK hoặc 404 Not Found
- PUT /items/:id hoặc PATCH /items/:id
  - Cập nhật — 200 OK trả item
- DELETE /items/:id
  - Xoá — 204 No Content

4) Response format & status codes
- Trả JSON cho mọi response body (trừ 204).
- Sử dụng status codes chuẩn:
  - 200 OK — thành công đọc/cập nhật
  - 201 Created — tài nguyên mới đã tạo
  - 204 No Content — xoá thành công, không có body
  - 400 Bad Request — lỗi validate input
  - 401 Unauthorized — token không hợp lệ/không cung cấp
  - 404 Not Found — không tìm thấy tài nguyên
  - 422 Unprocessable Entity — lỗi nghiệp vụ/validate chi tiết
  - 500 Internal Server Error — lỗi server

5) Ví dụ curl
- Login:
  curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"email":"a@b.com","password":"pass"}'
- Gọi endpoint bảo mật:
  curl -H "Authorization: Bearer <token>" http://localhost:8080/users/me

6) CORS (cho frontend dev)
- Phải bật CORS để frontend tại http://localhost:3000 gọi được API dev.
- Ví dụ Node/Express:
  ```js
  // enable-cors.js (snippet)
  const cors = require('cors');
  app.use(cors({
    origin: 'http://localhost:3000',
    methods: ['GET','POST','PUT','PATCH','DELETE','OPTIONS'],
    allowedHeaders: ['Content-Type','Authorization']
  }));
  ```
- Nếu dùng framework khác, cấu hình tương tự cho phép origin dev.

7) Ghi chú vận hành
- Dùng .env.example để lưu biến như JWT_SECRET, DATABASE_URL, PORT.
- Viết README riêng trong /backend chi tiết cách chạy (npm install, migration, lệnh seed, port mặc định).
- Đề nghị: mỗi endpoint có ít nhất 1 test (integration hoặc e2e) để đảm bảo contract với frontend.

---
## Quy trình làm việc: Tạo nhánh cá nhân và đẩy phần backend

**Mục tiêu:** mỗi người tạo nhánh có tên mình, đẩy folder backend lên để tui có thể clone về và kết nối API.

