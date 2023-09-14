insert
into picture
(created_date_time, modified_date_time, extension, file_size, full_path, name, origin_file_name, save_file_name,
 picture_type, room_id)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jpg', 1024, '/uploads/abc123.jpg', '숙소 사진',
        '숙소사진.jpg', 'abc123.jpg', 'ACCOMMODATION', NULL);
insert
into accommodation
(first_location, full_address, second_location, created_date_time, description, modified_date_time, name, phone_number,
 picture_id, rating, status, type)
values ('서울', '서울시 강남구 역삼동 123-456', '강남', CURRENT_TIMESTAMP, 'accommodation description!!!',
        CURRENT_TIMESTAMP, 'data.sql 호텔', '010-1234-5678', 1, 0.0, 'RESERVATION_AVAILABLE', 'HOTEL_RESORT');
insert
into room
(accommodation_id, check_in_time, check_out_time, created_date_time, max_occupancy, modified_date_time, name, price,
 stock_batch_date_time)
values (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4,
        CURRENT_TIMESTAMP, '룸1', 10000, NULL);
insert
into picture
(created_date_time, modified_date_time, extension, file_size, full_path, name, origin_file_name, save_file_name,
 picture_type, room_id)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jpg', 2048, '/uploads/def456.jpg', '룸1 사진1',
        '룸1사진1.jpg', 'def456.jpg', 'ROOM', 1);
insert
into picture
(created_date_time, modified_date_time, extension, file_size, full_path, name, origin_file_name, save_file_name,
 picture_type, room_id)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jpg', 3072, '/uploads/ghi789.jpg', '룸1 사진2',
        '룸1사진2.jpg', 'ghi789.jpg', 'ROOM', 1);
insert
into room
(accommodation_id, check_in_time, check_out_time, created_date_time, max_occupancy, modified_date_time, name, price,
 stock_batch_date_time)
values (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4,
        CURRENT_TIMESTAMP, '룸2', 20000, NULL);
insert
into picture
(created_date_time, modified_date_time, extension, file_size, full_path, name, origin_file_name, save_file_name,
 picture_type, room_id)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jpg', 4096, '/uploads/jkl012.jpg', '룸2 사진1',
        '룸2사진1.jpg', 'jkl012.jpg', 'ROOM', 2);
insert
into picture
(created_date_time, modified_date_time, extension, file_size, full_path, name, origin_file_name, save_file_name,
 picture_type, room_id)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jpg', 5120, '/uploads/mno345.jpg', '룸2 사진2',
        '룸2사진2.jpg', 'mno345.jpg', 'ROOM', 2);
INSERT INTO STOCK(created_date_time, modified_date_time, date, quantity, room_id)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 100, 1);
INSERT INTO STOCK(created_date_time, modified_date_time, date, quantity, room_id)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 100, 1);
INSERT INTO MEMBER(created_date_time, modified_date_time, deleted, email, grade, name, password, phone, role)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, 'test@test.com', 'NORMAL', 'park', 'password', '010-1234-5678',
        'USER');
