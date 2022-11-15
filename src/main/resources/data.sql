INSERT INTO BUILDING(id, name) VALUES(-19, 'Building 1');
INSERT INTO BUILDING(id, name) VALUES(-18, 'Building 2');

INSERT INTO ROOM(id, name, floor, current_temperature, target_temperature,building_id) VALUES(-10, 'Room1', 1, 22.3, 20.0,-19);
INSERT INTO ROOM(id, name, floor,building_id) VALUES(-9, 'Room2',1, -19);
INSERT INTO ROOM(id, name, floor,building_id) VALUES(-8, 'Room3',1, -18);

INSERT INTO HEATER(id, heater_status, name, power, room_id) VALUES(-10, 'ON', 'Heater1', 2000, -10);
INSERT INTO HEATER(id, heater_status, name, power, room_id) VALUES(-9, 'ON', 'Heater2', null, -10);

INSERT INTO RWINDOW(id, window_status, name, room_id) VALUES(-10, 'CLOSED', 'Window 1', -10);
INSERT INTO RWINDOW(id, window_status, name, room_id) VALUES(-9, 'CLOSED', 'Window 2', -10);
INSERT INTO RWINDOW(id, window_status, name, room_id) VALUES(-8, 'OPEN', 'Window 1', -9);
INSERT INTO RWINDOW(id, window_status, name, room_id) VALUES(-7, 'CLOSED', 'Window 2', -9);

-- INSERT INTO USER(id, name, role) VALUES(-19, 'Building 1', 'MANAGER');
-- INSERT INTO USER(id, name, role) VALUES(-19, 'Building 1', 'ADMIN');