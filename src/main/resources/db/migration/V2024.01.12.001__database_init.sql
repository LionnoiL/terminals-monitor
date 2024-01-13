create TABLE terminals (
    id                          INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    arm_id                      INTEGER NOT NULL,
    shop_name                   VARCHAR (150) NULL,
    cash_register_name          VARCHAR (150) NULL,
    last_update                 VARCHAR (150) NULL,
    ip_address                  VARCHAR (150) NULL
);

CREATE TABLE users (
	id                      bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username                VARCHAR(255),
	password                VARCHAR(255),
	role                    VARCHAR(36) NOT NULL,
	enabled                 BOOLEAN DEFAULT false
);

create unique index ix_users_user_name   on users (username);

insert into users
    (username, password, role, enabled)
values
    ('admin', '$2a$12$h5zjEEFmeObLcRur8EIrreLsdeyW8dLniPEVy2OdNSNdU3HKdoNN6', 'ROLE_ADMIN', true);
