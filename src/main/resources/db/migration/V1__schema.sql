CREATE TABLE `users` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `full_name` varchar(50) NOT NULL,
    `username` varchar(30) NOT NULL,
    `password` varchar(100) NOT NULL,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_users_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `roles` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(60) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_roles_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_roles` (
    `user_id` bigint(20) NOT NULL,
    `role_id` bigint(20) NOT NULL,
    PRIMARY KEY (`user_id`,`role_id`),
    KEY `fk_user_roles_role_id` (`role_id`),
    CONSTRAINT `fk_user_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
    CONSTRAINT `fk_user_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `income_types` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(60) NOT NULL,
    `description` TEXT,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uK_income_type_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `bill_types` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(60) NOT NULL,
    `description` TEXT,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uK_bill_type_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `incomes` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `income_type_id` bigint(20) NOT NULL,
    `amount` double NOT NULL,
    `month` int NOT NULL,
    `year` int NOT NULL,
    `info` TEXT,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `fk_income_type_id` (`income_type_id`),
    CONSTRAINT `fk_income_type_id` FOREIGN KEY (`income_type_id`) REFERENCES `income_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `bills` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `bill_type_id` bigint(20) NOT NULL,
    `amount` double NOT NULL,
    `month` int NOT NULL,
    `year` int NOT NULL,
    `info` TEXT,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `fk_bill_type_id` (`bill_type_id`),
    CONSTRAINT `fk_bill_type_id` FOREIGN KEY (`bill_type_id`) REFERENCES `bill_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
