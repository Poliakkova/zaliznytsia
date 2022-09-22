insert into user (id, first_name, second_name, email, password, active)
    values (1, 'Admin', 'Admin', 'admin@admin.com', '1', true);

insert into user_role (user_id, roles)
    values (1, 'USER'), (1, 'ADMIN');