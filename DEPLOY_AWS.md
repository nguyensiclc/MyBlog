# Hướng dẫn deploy MyBlog lên AWS (dùng CDK)

Project **Code_CDK** (nằm trong repo, đã được ignore trong `.gitignore`) cung cấp infrastructure AWS: VPC, RDS PostgreSQL, EC2 chạy Spring Boot, S3 chứa JAR.

## Yêu cầu

- AWS CLI đã cấu hình (`aws configure`), CDK CLI (`npm i -g aws-cdk`).
- Key pair EC2 tên **mywebsite** (tạo trong EC2 → Key Pairs).

## Các bước triển khai

### 1. Deploy CDK stack

```bash
cd Code_CDK
mvn compile
cdk bootstrap   # lần đầu nếu chưa bootstrap
cdk deploy
```

Sau khi deploy, ghi lại:
- **WebServerPublicIp** (hoặc Instance ID để lấy Public IP trong EC2 console).
- **AppBucketName** (tên bucket S3 để upload JAR).

### 2. Build backend và upload JAR

```bash
cd backend
mvn -DskipTests package
aws s3 cp target/myblog-0.0.1-SNAPSHOT.jar s3://<AppBucketName>/myblog.jar
```

### 3. (Tùy chọn) Khởi động lại app trên EC2

EC2 user data sẽ tự tải JAR từ S3 và chạy khi khởi động. Nếu bạn upload JAR sau khi EC2 đã chạy, cần SSH vào EC2 và chạy lại:

```bash
# SSH (dùng key mywebsite.pem)
ssh -i mywebsite.pem ec2-user@<WebServerPublicIp>

# Tải JAR mới và chạy lại
REGION=$(curl -s http://169.254.169.254/latest/meta-data/placement/region)
BUCKET=$(aws ssm get-parameter --name /myblog/app-bucket --query Parameter.Value --output text --region $REGION)
aws s3 cp s3://$BUCKET/myblog.jar /opt/myblog.jar
sudo pkill -f "myblog.jar" || true
cd /opt && nohup java -jar myblog.jar --spring.config.location=file:/opt/application.properties >> /var/log/myblog.log 2>&1 &
```

### 4. Frontend cho production

- **Cách 1 – Cùng origin với backend:** Build frontend với API trỏ đúng URL backend, rồi copy `frontend/dist` lên EC2 và cấu hình Spring Boot serve static từ thư mục đó (hoặc dùng Nginx).

- **Cách 2 – Build với API URL:** Khi build frontend, set biến môi trường `VITE_API_URL` trỏ tới backend:

```bash
cd frontend
VITE_API_URL=http://<WebServerPublicIp>:8080 npm run build
```

Sau đó có thể host `dist/` trên S3 + CloudFront hoặc serve từ chính EC2 (cùng port 8080 nếu cấu hình Spring phục vụ static).

### 5. Truy cập web

Mở trình duyệt: `http://<WebServerPublicIp>:8080`

- Nếu frontend được serve từ cùng EC2:8080 thì CORS đã được cấu hình theo public IP.
- Nếu frontend host ở domain/port khác, cần thêm origin đó vào `app.cors.allowed-origins` trong file cấu hình trên EC2 (`/opt/application.properties`).

## Lưu ý

- **JWT secret:** Trên production nên đặt `jwt.secret` mạnh trong `/opt/application.properties` trên EC2 (hoặc qua SSM/Secrets Manager).
- **Key pair:** Đảm bảo đã tạo key **mywebsite** trong AWS EC2 và có file `.pem` để SSH.
- **Database:** RDS dùng database name `myblog`, user/password lấy từ Secrets Manager (CDK tự tạo secret).
