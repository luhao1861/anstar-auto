# CarGo LA 租车管理系统

基于 Spring Boot 的租车管理系统

## 技术栈

- Java 17
- Spring Boot 3.2
- MyBatis Plus
- MySQL
- Lombok
- Swagger API 文档

## 项目结构

```
src/main/java/com/cargola/
├── CarRentalApplication.java    # 启动类
├── config/                      # 配置类
├── controller/                  # 控制器
│   └── VehicleController.java  # 车辆接口
├── dto/                        # 数据传输对象
├── entity/                     # 实体类
│   ├── Store.java
│   ├── Vehicle.java
│   ├── Customer.java
│   ├── Rental.java
│   └── Maintenance.java
├── mapper/                     # MyBatis Mapper
│   ├── StoreMapper.java
│   ├── VehicleMapper.java
│   ├── CustomerMapper.java
│   ├── RentalMapper.java
│   └── MaintenanceMapper.java
└── service/                    # 业务逻辑
```

## 快速启动

1. 确保 MySQL 已启动
2. 创建数据库: `CREATE DATABASE cargo_la;`
3. 运行: `mvn spring-boot:run`

## API 文档

启动后访问: http://localhost:8080/swagger-ui.html

## 待完成

- [ ] Customer 控制器
- [ ] Rental 控制器
- [ ] Maintenance 控制器
- [ ] 报表统计功能
- [ ] 前端页面
