CREATE TABLE IF NOT EXISTS `dictionary`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `word`        varchar(50)   NOT NULL,
    `description` text          NOT NULL,
    `img_url`     varchar(2083) NULL
);

CREATE TABLE IF NOT EXISTS `car`
(
    `id`      bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name_ko` varchar(100) NOT NULL,
    `name_en` varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS `trim`
(
    `id`                         bigint        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`                       varchar(50)   NOT NULL,
    `img_url`                    varchar(2083) NOT NULL,
    `background_img_url_web`     varchar(2083) NOT NULL,
    `background_img_url_android` varchar(2083) NOT NULL,
    `hashtag`                    varchar(50)   NOT NULL,
    `price`                      int           NOT NULL,
    `description`                varchar(50)   NULL,
    `car_id`                     bigint        NOT NULL,
    FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
);

CREATE TABLE IF NOT EXISTS `category`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS `options`
(
    `id`                   bigint              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`                 varchar(100) UNIQUE NOT NULL,
    `price`                int                 NOT NULL,
    `feedback_title`       varchar(255)        NULL,
    `feedback_description` varchar(255)        NULL,
    `category_id`          bigint              NOT NULL,
    FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
);

CREATE TABLE IF NOT EXISTS `sell`
(
    `id`                bigint    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `trim_id`           bigint    NOT NULL,
    `engine_id`         bigint    NOT NULL,
    `body_type_id`      bigint    NOT NULL,
    `driving_system_id` bigint    NOT NULL,
    `exterior_color_id` bigint    NOT NULL,
    `interior_color_id` bigint    NOT NULL,
    `wheel_id`          bigint    NOT NULL,
    `age`               tinyint   NOT NULL,
    `gender`            tinyint   NOT NULL,
    `create_date`       timestamp NOT NULL,
    FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`),
    FOREIGN KEY (`engine_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`body_type_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`driving_system_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`exterior_color_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`interior_color_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`wheel_id`) REFERENCES `options` (`id`)
);

CREATE TABLE IF NOT EXISTS `sell_selective_options`
(
    `id`         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sell_id`    bigint NOT NULL,
    `options_id` bigint NOT NULL,
    FOREIGN KEY (`sell_id`) REFERENCES `sell` (`id`),
    FOREIGN KEY (`options_id`) REFERENCES `options` (`id`)
);

CREATE TABLE IF NOT EXISTS `keyword`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS `options_keyword`
(
    `id`         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `options_id` bigint NOT NULL,
    `keyword_id` bigint NOT NULL,
    FOREIGN KEY (`options_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`keyword_id`) REFERENCES `keyword` (`id`)
);

CREATE TABLE IF NOT EXISTS `estimate`
(
    `id`                bigint    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `trim_id`           bigint    NOT NULL,
    `engine_id`         bigint    NOT NULL,
    `body_type_id`      bigint    NOT NULL,
    `driving_system_id` bigint    NOT NULL,
    `exterior_color_id` bigint    NOT NULL,
    `interior_color_id` bigint    NOT NULL,
    `wheel_id`          bigint    NOT NULL,
    `keyword1_id`       bigint    NOT NULL,
    `keyword2_id`       bigint    NOT NULL,
    `keyword3_id`       bigint    NOT NULL,
    `age_range`         tinyint   NOT NULL,
    `gender`            tinyint   NOT NULL,
    `create_date`       timestamp NOT NULL,
    FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`),
    FOREIGN KEY (`engine_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`body_type_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`driving_system_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`exterior_color_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`interior_color_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`wheel_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`keyword1_id`) REFERENCES `keyword` (`id`),
    FOREIGN KEY (`keyword2_id`) REFERENCES `keyword` (`id`),
    FOREIGN KEY (`keyword3_id`) REFERENCES `keyword` (`id`)
);

CREATE TABLE IF NOT EXISTS `estimate_selective_options`
(
    `id`          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `estimate_id` bigint NOT NULL,
    `options_id`  bigint NOT NULL,
    FOREIGN KEY (`estimate_id`) REFERENCES `estimate` (`id`),
    FOREIGN KEY (`options_id`) REFERENCES `options` (`id`)
);

CREATE TABLE IF NOT EXISTS `options_image`
(
    `id`         bigint        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `img_url`    varchar(2083) NOT NULL,
    `img_type`   tinyint       NOT NULL,
    `options_id` bigint        NOT NULL,
    FOREIGN KEY (`options_id`) REFERENCES `options` (`id`)
);

CREATE TABLE IF NOT EXISTS `options_detail`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`        varchar(100)  NULL,
    `description` text          NOT NULL,
    `img_url`     varchar(2083) NULL,
    `options_id`  bigint        NOT NULL,
    FOREIGN KEY (`options_id`) REFERENCES `options` (`id`)
);

CREATE TABLE IF NOT EXISTS `spec`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`              varchar(50)  NOT NULL,
    `description`       varchar(100) NOT NULL,
    `options_detail_id` bigint       NOT NULL,
    FOREIGN KEY (`options_detail_id`) REFERENCES `options` (`id`)
);

CREATE TABLE IF NOT EXISTS `trim_options`
(
    `id`         bigint  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `type`       tinyint NOT NULL,
    `trim_id`    bigint  NOT NULL,
    `options_id` bigint  NOT NULL,
    FOREIGN KEY (`trim_id`) REFERENCES `trim` (`id`),
    FOREIGN KEY (`options_id`) REFERENCES `options` (`id`)
);

CREATE TABLE IF NOT EXISTS `options_relation`
(
    `id`        bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_id` bigint NOT NULL,
    `child_id`  bigint NOT NULL,
    FOREIGN KEY (`parent_id`) REFERENCES `options` (`id`),
    FOREIGN KEY (`child_id`) REFERENCES `options` (`id`)
);