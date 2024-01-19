# Prerequisite
1. run docker compose
```sh
docker-compose up -d
```

2. check that service is work normally
3. Follow the following requirements.
    1. หากมีการต้องส่ง Error ต้องใช้ CommonException
    2. เส้นไหนถ้ามี req และคิดว่าจำเป็นต้องมี Validation ต้องทำ Validation ด้วย
    3. ทำ CRUD ของ entity User โดยตอนที่จะ response กลับไปต้องมีการสร้าง dto response และมีการใช้ mapper
    4. ใช้ Cache สำหรับ findById
    5. อัพเดท cache หากมีการอัพเดทข้อมูล user
    6. ลบ cache หากมีการลบ user
    7. (ใช้ interface Projection) มี method เพิ่มมาอีก 1 ตัว ที่จะ return response โดยมีค่าของ
        1. id ของ user
        2. fullName ซึ่งเป็นการรวมกันระหว่าง field first_name และ field last_name
    8. สร้าง table Orders โดยมี field ดังนี้
        1. id ซึ่งเป็น primary key ของ orders มี type เป็น UUID
        2. name เป็นรายละเอียดว่า order นั้นซื้ออะไร
        3. price เป็นรายละเอียดว่า order นั้นมีราคาเท่าไร
        4. address เป็นรายละเอียดว่า order นั้นมีที่อยู่จัดส่งเป็นที่ไหน
        5. orderDate เป็นรายละเอียดว่า order นั้นถูกสั่งซื้อวันที่เท่าไร เวลาอะไร
        6. orderStatus เป็นรายละเอียดว่า order นั้นเป็นสถานะอะไร โดยที่มีค่าเป็น PENDING, PROCESSING, COMPLETE และ CANCELLED
        7. user_id เป็นการเก็บว่า order นั้นเป็นของ user คนไหน
    9. ทำ CRUD ของ entity Order
    10. ใช้ cache สำหรับ findById
    11. อัพเดท cache หากมีการอัพเดท order
    12. ลบ cache หากมีการลบ order
    13. มี method ที่เช็คว่า user คนนี้มี order อะไรบ้าง
    14. ทำเทสทั้ง controller และ service ให้ครบ 100%