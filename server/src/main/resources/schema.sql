CREATE TABLE IF NOT EXISTS `dictionary`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `word`        varchar(50)  NOT NULL,
    `description` varchar(255) NOT NULL,
    `img_url`     varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS `car`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS `category`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS `option`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`        varchar(100) NOT NULL,
    `price`       int          NOT NULL,
    `feedback`    varchar(255) NULL,
    `category_id` bigint       NOT NULL
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
    `create_date`       timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS `sell_selective_options`
(
    `id`        bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sell_id`   bigint NOT NULL,
    `option_id` bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS `sell_h_genuine_accessories`
(
    `id`        bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `option_id` bigint NOT NULL,
    `sell_id`   bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS `keyword`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS `option_keyword`
(
    `id`         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `option_id`  bigint NOT NULL,
    `keyword_id` bigint NOT NULL
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
    `age`               tinyint   NOT NULL,
    `gender`            tinyint   NOT NULL,
    `create_date`       timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS `estimate_h_genuine_accessories`
(
    `id`          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `estimate_id` bigint NOT NULL,
    `option_id`   bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS `estimate_selective_options`
(
    `id`          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `estimate_id` bigint NOT NULL,
    `option_id`   bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS `option_image`
(
    `id`        bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `img_url`   varchar(255) NOT NULL,
    `img_type`  tinyint      NOT NULL,
    `option_id` bigint       NOT NULL
);

CREATE TABLE IF NOT EXISTS `detail`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`        varchar(100) NOT NULL,
    `description` varchar(255) NOT NULL,
    `img_url`     varchar(255) NULL,
    `option_id`   bigint       NOT NULL
);

CREATE TABLE IF NOT EXISTS `trim`
(
    `id`      bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`    varchar(50)  NOT NULL,
    `img_url` varchar(255) NOT NULL,
    `car_id`  bigint       NOT NULL
);

CREATE TABLE IF NOT EXISTS `trim_option`
(
    `id`         bigint  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `type`       tinyint NOT NULL,
    `trim_id`    bigint  NOT NULL,
    `options_id` bigint  NOT NULL
);

CREATE TABLE IF NOT EXISTS `option_relation`
(
    `id`        bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_id` bigint NOT NULL,
    `child_id`  bigint NOT NULL
);

ALTER TABLE `option`
    ADD CONSTRAINT `FK_category_TO_option_1` FOREIGN KEY (
                                                          `category_id`
        )
        REFERENCES `category` (
                               `id`
            );

ALTER TABLE `sell`
    ADD CONSTRAINT `FK_trim_TO_sell_1` FOREIGN KEY (
                                                    `trim_id`
        )
        REFERENCES `trim` (
                           `id`
            );

ALTER TABLE `sell`
    ADD CONSTRAINT `FK_option_TO_sell_1` FOREIGN KEY (
                                                      `engine_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `sell`
    ADD CONSTRAINT `FK_option_TO_sell_2` FOREIGN KEY (
                                                      `body_type_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `sell`
    ADD CONSTRAINT `FK_option_TO_sell_3` FOREIGN KEY (
                                                      `driving_system_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `sell`
    ADD CONSTRAINT `FK_option_TO_sell_4` FOREIGN KEY (
                                                      `exterior_color_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `sell`
    ADD CONSTRAINT `FK_option_TO_sell_5` FOREIGN KEY (
                                                      `interior_color_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `sell`
    ADD CONSTRAINT `FK_option_TO_sell_6` FOREIGN KEY (
                                                      `wheel_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `sell_selective_options`
    ADD CONSTRAINT `FK_sell_TO_sell_selective_options_1` FOREIGN KEY (
                                                                      `sell_id`
        )
        REFERENCES `sell` (
                           `id`
            );

ALTER TABLE `sell_selective_options`
    ADD CONSTRAINT `FK_option_TO_sell_selective_options_1` FOREIGN KEY (
                                                                        `option_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `sell_h_genuine_accessories`
    ADD CONSTRAINT `FK_option_TO_sell_h_genuine_accessories_1` FOREIGN KEY (
                                                                            `option_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `sell_h_genuine_accessories`
    ADD CONSTRAINT `FK_sell_TO_sell_h_genuine_accessories_1` FOREIGN KEY (
                                                                          `sell_id`
        )
        REFERENCES `sell` (
                           `id`
            );

ALTER TABLE `option_keyword`
    ADD CONSTRAINT `FK_option_TO_option_keyword_1` FOREIGN KEY (
                                                                `option_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `option_keyword`
    ADD CONSTRAINT `FK_keyword_TO_option_keyword_1` FOREIGN KEY (
                                                                 `keyword_id`
        )
        REFERENCES `keyword` (
                              `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_trim_TO_estimate_1` FOREIGN KEY (
                                                        `trim_id`
        )
        REFERENCES `trim` (
                           `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_option_TO_estimate_1` FOREIGN KEY (
                                                          `engine_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_option_TO_estimate_2` FOREIGN KEY (
                                                          `body_type_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_option_TO_estimate_3` FOREIGN KEY (
                                                          `driving_system_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_option_TO_estimate_4` FOREIGN KEY (
                                                          `exterior_color_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_option_TO_estimate_5` FOREIGN KEY (
                                                          `interior_color_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_option_TO_estimate_6` FOREIGN KEY (
                                                          `wheel_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_keyword_TO_estimate_1` FOREIGN KEY (
                                                           `keyword1_id`
        )
        REFERENCES `keyword` (
                              `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_keyword_TO_estimate_2` FOREIGN KEY (
                                                           `keyword2_id`
        )
        REFERENCES `keyword` (
                              `id`
            );

ALTER TABLE `estimate`
    ADD CONSTRAINT `FK_keyword_TO_estimate_3` FOREIGN KEY (
                                                           `keyword3_id`
        )
        REFERENCES `keyword` (
                              `id`
            );

ALTER TABLE `estimate_h_genuine_accessories`
    ADD CONSTRAINT `FK_estimate_TO_estimate_h_genuine_accessories_1` FOREIGN KEY (
                                                                                  `estimate_id`
        )
        REFERENCES `estimate` (
                               `id`
            );

ALTER TABLE `estimate_h_genuine_accessories`
    ADD CONSTRAINT `FK_option_TO_estimate_h_genuine_accessories_1` FOREIGN KEY (
                                                                                `option_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `estimate_selective_options`
    ADD CONSTRAINT `FK_estimate_TO_estimate_selective_options_1` FOREIGN KEY (
                                                                              `estimate_id`
        )
        REFERENCES `estimate` (
                               `id`
            );

ALTER TABLE `estimate_selective_options`
    ADD CONSTRAINT `FK_option_TO_estimate_selective_options_1` FOREIGN KEY (
                                                                            `option_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `option_image`
    ADD CONSTRAINT `FK_option_TO_option_image_1` FOREIGN KEY (
                                                              `option_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `detail`
    ADD CONSTRAINT `FK_option_TO_detail_1` FOREIGN KEY (
                                                        `option_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `trim`
    ADD CONSTRAINT `FK_car_TO_trim_1` FOREIGN KEY (
                                                   `car_id`
        )
        REFERENCES `car` (
                          `id`
            );

ALTER TABLE `trim_option`
    ADD CONSTRAINT `FK_trim_TO_trim_option_1` FOREIGN KEY (
                                                           `trim_id`
        )
        REFERENCES `trim` (
                           `id`
            );

ALTER TABLE `trim_option`
    ADD CONSTRAINT `FK_option_TO_trim_option_1` FOREIGN KEY (
                                                             `options_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `option_relation`
    ADD CONSTRAINT `FK_option_TO_option_relation_1` FOREIGN KEY (
                                                                 `parent_id`
        )
        REFERENCES `option` (
                             `id`
            );

ALTER TABLE `option_relation`
    ADD CONSTRAINT `FK_option_TO_option_relation_2` FOREIGN KEY (
                                                                 `child_id`
        )
        REFERENCES `option` (
                             `id`
            );


