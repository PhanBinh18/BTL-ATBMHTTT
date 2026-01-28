# Hệ thống E-commerce Security 
Dự án nghiên cứu về SQL injection
## Yêu cầu hệ thống 
* Docker Desktop
## Cơ sở dữ liệu 
Datahase bao gồm các bảng:
* User
* Role
* User_Role
* Products

## Hướng dẫn sử dụng 
1. Khởi động dịch vụ
Chạy lệnh sau để khởi động MySQL và Springboot
```docker compose up -d```
2. Kiểm tra trạng thái
Xem trạng thái các containers:
```docker compose ps```
Xem logs:
```docker compose logs -f```
3. Kết nối database
Thông tin kết nối:
* Host: localhost
* Port: 3307
* Database: ecommerce_db
* Username: dev_user
* Password: 123456
Kết nối qua terminal:
```docker compose exec db mysql -u dev_user -p ecommerce_db```
Nhập password: 123456
* 